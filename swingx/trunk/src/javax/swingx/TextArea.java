package javax.swingx;

import javax.swing.JTextArea;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;

/**
 * TextArea is a multiline text field. The text can be edited if necessary.
 * 
 * @see javax.swing.JTextArea
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class TextArea extends JTextArea implements ConnectionHandler {

	private static final long serialVersionUID = 1L;

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	/**
	 * This is the standard constructor. No text is set.
	 */
	public TextArea() {
		super();
	}

	/**
	 * This constructor is able to set a starting text for this text field.
	 * 
	 * @param text
	 *            is a String with the text to be set.
	 */
	public TextArea(String text) {
		super(text);
	}

	public TextArea(int rows, int columns) {
		super(rows, columns);
	}

	public TextArea(String text, int rows, int columns) {
		super(text, rows, columns);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.connect(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.release(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		return connectionManager.isConnected(signal, receiver, slot, types);
	}

}
