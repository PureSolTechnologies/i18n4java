package javax.swingx;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class Table extends JTable implements ConnectionHandler {

	private static final long serialVersionUID = 1L;

	public Table() {
		super();
		init();
	}

	public Table(int rows, int cols) {
		super(rows, cols);
		init();
	}

	public Table(AbstractTableModel model) {
		super(model);
		init();
	}

	private void init() {
		getSelectionModel().addListSelectionListener(this);
	}

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	@Override
	public void valueChanged(ListSelectionEvent event) {
		super.valueChanged(event);
		selectionChanged();
		selectionChanged(this.getSelectedRow());
	}

	@Signal
	public void selectionChanged() {
		connectionManager.emitSignal("selectionChanged");
	}

	@Signal
	public void selectionChanged(int row) {
		connectionManager.emitSignal("selectionChanged", row);
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
