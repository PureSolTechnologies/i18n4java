package javax.swingx.connect;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * This class handles connections which were established and also emits signals.
 * 
 * For the connection handling a special observer pattern was developed which
 * not only stores object pointers of special interface, but objects pointers
 * and method names to be connected. If a signal function is called all slots
 * (observer methods) are called, too. It's also possible to send data through
 * these connections.
 * 
 * For details have a look into the handbook.
 * 
 * <b>Attention:</b> In Conection as well as in ConnectionManager logging via
 * Logger is not possible, because Logger uses the ConnectionManager as well for
 * tranmitting changes to LogViewer. A logging via itself would lead to an
 * endless loop! <b>Therefore, enhanced and very tight testing is necessary due
 * to the fact that logging and connection management are central facilities for
 * all classes within the API.</b>
 * 
 * @see Slot
 * @see Signal
 * @see SignalEmitter
 * @see SignalReceiver
 * @see ConnectionHandler
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
final public class ConnectionManager implements ConnectionHandler, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(ConnectionManager.class);

	/**
	 * This Vector stores all connections which were established.
	 */
	private Vector<Connection> connections = new Vector<Connection>();

	private ConnectionHandler emitter = null;

	static public ConnectionManager createFor(ConnectionHandler emitter) {
		return new ConnectionManager(emitter);
	}

	private ConnectionManager(ConnectionHandler emitter) {
		this.emitter = emitter;
	}

	protected Object getEmitter() {
		return emitter;
	}

	/**
	 * {@inheritDoc}
	 */
	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		Connection connection = new Connection(emitter, signal, receiver, slot,
				types);
		connections.add(connection);
	}

	/**
	 * {@inheritDoc}
	 */
	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		Enumeration<Connection> enumeration = connections.elements();
		while (enumeration.hasMoreElements()) {
			Connection connection = enumeration.nextElement();
			if (connection.isSignal(emitter, signal, types)
					&& connection.isSlot(receiver, slot, types)) {
				connections.remove(connection);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		try {
			Method signalMethod = getEmitter().getClass().getMethod(signal,
					types);
			Method slotMethod = receiver.getClass().getMethod(slot, types);
			for (Connection connection : connections) {
				if ((connection.getEmitter() == emitter)
						&& (connection.getReceiver() == receiver)
						&& (signalMethod.equals(connection.getSignal()))
						&& (slotMethod.equals(connection.getSlot()))) {
					return true;
				}
			}
			return false;
		} catch (SecurityException e) {
			logger.fatal(e.getMessage(), e);
			throw new RuntimeException();
		} catch (NoSuchMethodException e) {
			logger.fatal(e.getMessage(), e);
			throw new RuntimeException();
		}
	}

	/**
	 * This method is used to emit a signal to all its receivers.
	 * 
	 * @param emitter
	 *            is a pointer to the signal class which emits the signal.
	 * @param signal
	 *            is the method's name which is the method which emit the
	 *            signal.
	 * @param params
	 *            are the parameters which are used to send to all slots.
	 * @return A boolean is returned to represent the success of the emission
	 *         process. True is returned if all slots were called correctly.
	 *         False is returned otherwise.
	 */
	public boolean emitSignal(String signal, Object... params) {
		boolean retVal = true;
		for (Connection connection : connections) {
			if (connection.isSignal(emitter, signal, params)) {
				if (!connection.emit(params)) {
					retVal = false;
				}
			}
		}
		return retVal;
	}
}
