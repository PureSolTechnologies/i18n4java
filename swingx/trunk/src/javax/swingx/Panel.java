package javax.swingx;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;

/**
 * This panel is a inheritance of JPanel. The Panel was enhanced with connection
 * handling and translation facilities.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class Panel extends JPanel implements ConnectionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	/**
	 * This is a standard constructor.
	 * 
	 */
	public Panel() {
		super();
	}

	/**
	 * This method sets a titled border on a very easy way. A text can be
	 * specified, but everything else is standard.
	 * 
	 * @param title
	 *            is the title of the border.
	 */
	public void setTitledBorder(String title) {
		this.setBorder(new TitledBorder(title));
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
