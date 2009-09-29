package javax.swingx;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;

public class Table extends JTable implements ConnectionHandler {

	private static final long serialVersionUID = 1L;

	public Table() {
		super();
	}

	public Table(int rows, int cols) {
		super(rows, cols);
	}

	public Table(AbstractTableModel model) {
		super(model);
	}

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

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
