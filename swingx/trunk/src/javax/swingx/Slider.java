package javax.swingx;

import javax.swing.JSlider;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;

public class Slider extends JSlider implements ConnectionHandler {

	private static final long serialVersionUID = 1L;

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public Slider() {
		super();
	}

	public Slider(int orientation, int min, int max, int value) {
		super(orientation, min, max, value);
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
