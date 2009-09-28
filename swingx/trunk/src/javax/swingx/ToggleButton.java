package javax.swingx;

import javax.swing.JToggleButton;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;

public class ToggleButton extends JToggleButton implements ConnectionHandler {

	private static final long serialVersionUID = 1L;

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public ToggleButton() {
		super();
	}

	public ToggleButton(String text) {
		super(text);
	}

	public ToggleButton(String text, boolean set) {
		super(text, set);
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

}
