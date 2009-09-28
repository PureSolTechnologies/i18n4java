package javax.swingx;

import javax.swing.JToolBar;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;

public class ToolBar extends JToolBar implements ConnectionHandler {

	private static final long serialVersionUID = 1L;

	public ToolBar() {
		super();
	}

	public ToolBar(String name, int direction) {
		super(name, direction);
	}

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

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
