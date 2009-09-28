package javax.swingx;

import javax.swing.JMenuBar;
import javax.swingx.connect.ConnectionManager;

public class MenuBar extends JMenuBar implements Widget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConnectionManager connectionManager = ConnectionManager
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
	public boolean isConnected(String signal, Object receiver, String slot) {
		return connectionManager.isConnected(signal, receiver, slot);
	}

	/**
	 * {@inheritDoc}
	 */
	public void release(String signal, Object receiver, String slot) {
		connectionManager.release(signal, receiver, slot);
	}

	public void addMediator(Mediator mediator) {
		connectionManager.connect("changed", mediator, "widgetChanged");
	}

	public void changed(Widget widget) {
		connectionManager.emitSignal("changed", widget);
	}
}
