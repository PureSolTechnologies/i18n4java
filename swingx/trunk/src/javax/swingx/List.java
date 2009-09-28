package javax.swingx;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class List extends JList implements ListSelectionListener,
		ConnectionHandler {

	private static final long serialVersionUID = 1L;

	private ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public List() {
		super();
		this.addListSelectionListener(this);
	}

	public List(ListModel listModel) {
		super(listModel);
		this.addListSelectionListener(this);
	}

	@Signal
	public void valueChanged(Object value) {
		connectionManager.emitSignal("valueChanged", value);
	}

	@Signal
	public void valuesChanged(Object[] values) {
		connectionManager.emitSignal("valuesChanged", values);
	}

	@Signal
	public void indexChanged(int index) {
		connectionManager.emitSignal("indexChanged", index);
	}

	@Signal
	public void indicesChanged(int[] indices) {
		connectionManager.emitSignal("indicesChanged", indices);
	}

	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.connect(signal, receiver, slot, types);
	}

	public boolean isConnected(String signal, Object receiver, String slot) {
		return connectionManager.isConnected(signal, receiver, slot);
	}

	public void release(String signal, Object receiver, String slot) {
		connectionManager.release(signal, receiver, slot);
	}

	public void valueChanged(ListSelectionEvent event) {
		if (!event.getValueIsAdjusting()) {
			valueChanged(getSelectedValue());
			valuesChanged(getSelectedValues());
			indexChanged(getSelectedIndex());
			indicesChanged(getSelectedIndices());
		}
	}
}
