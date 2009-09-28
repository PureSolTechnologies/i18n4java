package javax.swingx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class CheckBoxMenuItem extends JCheckBoxMenuItem implements Widget,
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public CheckBoxMenuItem() {
		super();
		initialize();
	}

	public CheckBoxMenuItem(String text) {
		super(text);
		initialize();
	}

	public CheckBoxMenuItem(String text, Icon icon) {
		super(text, icon);
		initialize();
	}

	public CheckBoxMenuItem(String text, boolean selected) {
		super(text, selected);
		initialize();
	}

	public CheckBoxMenuItem(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
		initialize();
	}

	private void initialize() {
		addActionListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.connect(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isConnected(String signal, Object receiver, String slot) {
		return connectionManager.isConnected(signal, receiver, slot);
	}

	/**
	 * {@inheritDoc}
	 */
	public void release(String signal, Object receiver, String slot) {
		connectionManager.release(signal, receiver, slot);
	}

	@Signal
	public void start() {
		connectionManager.emitSignal("start");
		changed(isSelected());
	}

	@Signal
	public void changed(boolean state) {
		connectionManager.emitSignal("changed", state);
	}

	@Signal
	public void actionPerformed(ActionEvent actionEvent) {
		start();
		connectionManager.emitSignal("actionPerformed");
	}

	public void addMediator(Mediator mediator) {
		connectionManager.connect("changed", mediator, "widgetChanged");
	}

	public void changed(Widget widget) {
		connectionManager.emitSignal("changed", widget);
	}
}
