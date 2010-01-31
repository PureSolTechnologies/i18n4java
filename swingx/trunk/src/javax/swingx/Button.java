package javax.swingx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class Button extends JButton implements Widget, ActionListener {

    private static final long serialVersionUID = 1L;

    private ConnectionManager connectionManager =
	    ConnectionManager.createFor(this);

    public Button(String text) {
	super(text);
	addActionListener(this);
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
    public void release(String signal, Object receiver, String slot,
	    Class<?>... types) {
	connectionManager.release(signal, receiver, slot, types);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isConnected(String signal, Object receiver,
	    String slot, Class<?>... types) {
	return connectionManager
		.isConnected(signal, receiver, slot, types);
    }

    @Signal
    public void start() {
	connectionManager.emitSignal("start");
	changed(this);
    }

    public void actionPerformed(ActionEvent actionEvent) {
	start();
    }

    public void addMediator(Mediator mediator) {
	connectionManager.connect("changed", mediator, "widgetChanged",
		Widget.class);
    }

    @Signal
    public void changed(Widget widget) {
	connectionManager.emitSignal("changed", widget);
    }
}
