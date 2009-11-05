package javax.swingx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class ToggleButton extends JToggleButton implements Widget,
		ActionListener {

	private static final long serialVersionUID = 1L;

	private Mediator mediator = null;

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public ToggleButton() {
		super();
		addActionListener(this);
	}

	public ToggleButton(String text) {
		super(text);
		addActionListener(this);
	}

	public ToggleButton(String text, boolean set) {
		super(text, set);
		addActionListener(this);
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

	@Signal
	public void start() {
		connectionManager.emitSignal("start");
	}

	@Override
	public void addMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	public void changed(Widget widget) {
		if (mediator != null) {
			mediator.widgetChanged(widget);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		start();
		changed(this);
	}
}
