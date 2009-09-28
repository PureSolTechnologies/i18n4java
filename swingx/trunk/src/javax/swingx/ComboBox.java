package javax.swingx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class ComboBox extends JComboBox implements Widget, ActionListener,
		FocusListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public ComboBox() {
		super();
		initialize();
	}

	private void initialize() {
		addActionListener(this);
		addFocusListener(this);
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
	public void changedSelection(Object o) {
		connectionManager.emitSignal("changedSelection", o);
	}

	@Signal
	public void changedIndex(int index) {
		connectionManager.emitSignal("changedIndex", index);
	}

	public void actionPerformed(ActionEvent e) {
		changedSelection(getSelectedItem());
		changedIndex(getSelectedIndex());
	}

	@Signal
	public void focusGained(FocusEvent focusEvent) {
		connectionManager.emitSignal("focusGained", focusEvent);
	}

	@Signal
	public void focusLost(FocusEvent focusEvent) {
		connectionManager.emitSignal("focusLost", focusEvent);
	}

	public void addMediator(Mediator mediator) {
		connectionManager.connect("changed", mediator, "widgetChanged");
	}

	public void changed(Widget widget) {
		connectionManager.emitSignal("changed", widget);
	}
}
