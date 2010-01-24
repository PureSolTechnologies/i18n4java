package javax.swingx;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;

public class Frame extends JFrame implements ConnectionHandler {

    private static final long serialVersionUID = -7797445282861924849L;

    private final ConnectionManager connectionManager =
	    ConnectionManager.createFor(this);

    public Frame() throws HeadlessException {
	super();
    }

    public Frame(GraphicsConfiguration gc) {
	super(gc);
    }

    public Frame(String title, GraphicsConfiguration gc) {
	super(title, gc);
    }

    public Frame(String title) throws HeadlessException {
	super(title);
    }

    @Override
    public void connect(String signal, Object receiver, String slot,
	    Class<?>... types) {
	connectionManager.connect(signal, receiver, slot, types);
    }

    @Override
    public boolean isConnected(String signal, Object receiver,
	    String slot, Class<?>... types) {
	return connectionManager
		.isConnected(signal, receiver, slot, types);
    }

    @Override
    public void release(String signal, Object receiver, String slot,
	    Class<?>... types) {
	connectionManager.release(signal, receiver, slot, types);
    }

    protected void emitSignal(String signal, Object... params) {
	connectionManager.emitSignal(signal, params);
    }
}
