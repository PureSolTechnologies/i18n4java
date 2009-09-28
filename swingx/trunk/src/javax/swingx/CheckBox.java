package javax.swingx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class CheckBox extends JCheckBox implements Widget, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public CheckBox() {
		super();
		addActionListener(this);
	}

	public CheckBox(String text) {
		super(text);
		addActionListener(this);
	}

	public CheckBox(String text, boolean checked) {
		super(text, checked);
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
	public void release(String signal, Object receiver, String slot) {
		connectionManager.release(signal, receiver, slot);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isConnected(String signal, Object receiver, String slot) {
		return connectionManager.isConnected(signal, receiver, slot);
	}

	@Signal
	public void start() {
		connectionManager.emitSignal("start");
		changed(this);
	}

	@Signal
	public void actionPerformed(ActionEvent actionEvent) {
		connectionManager.emitSignal("actionPerformed", actionEvent);
		start();
	}

	public void addMediator(Mediator mediator) {
		connectionManager.connect("changed", mediator, "widgetChanged");
	}

	public void changed(Widget widget) {
		connectionManager.emitSignal("changed", widget);
	}
}
