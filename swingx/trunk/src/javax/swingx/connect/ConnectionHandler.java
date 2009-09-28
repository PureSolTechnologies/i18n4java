package javax.swingx.connect;

/**
 * This interface is implemented by all classes which should act as a connection
 * handler. This interface is used rarely. The best interface to be implemented
 * is SignalEmitter.
 * 
 * @see SignalEmitter
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public interface ConnectionHandler {

	/**
	 * This method is used to connect a signal method with a slot method. The
	 * signal method should be annotated as @Signal and should be a member of a
	 * class with an implemented SignalEmitter interface. The slot method has to
	 * be treated in a different way. The slot has to be annotated as @Slot and
	 * has to be member of a class implementing the SignalReceiver interface.
	 * 
	 * For more details about the connection handling have a look to the
	 * handbook.
	 * 
	 * @see Slot
	 * @see Signal
	 * @see SignalEmitter
	 * @see SignalReceiver
	 * 
	 * @param signal
	 *            is the method's name which is the method which emit the
	 *            signal.
	 * @param receiver
	 *            is the signal receiving class.
	 * @param slot
	 *            is the method which is to be called, when the signal was
	 *            emitted. The method should have the same parameters as the
	 *            signal method and a void or boolean return value. The boolean
	 *            return value signals the successful procession of the signal.
	 *            True means, the signal was treated successfully. Otherwise a
	 *            false is the answer.
	 */
	public void connect(String signal, Object receiver, String slot,
			Class<?>... types);

	/**
	 * This method releases a connection. If no connection was connected before,
	 * this method performs no action.
	 * 
	 * @param signal
	 *            is the method's name which is the method which emit the
	 *            signal.
	 * @param receiver
	 *            is the signal receiving class.
	 * @param slot
	 *            is the method which is to be called, when the signal was
	 *            emitted.
	 */
	public void release(String signal, Object receiver, String slot);

	/**
	 * This method checks for an established connection.
	 * 
	 * @param signal
	 *            is the method's name which is the method which emit the
	 *            signal.
	 * @param receiver
	 *            is the signal receiving class.
	 * @param slot
	 *            is the method which is to be called, when the signal was
	 *            emitted.
	 * @return True is returned if a connection was establish before which has
	 *         the same objects and methods involved.
	 */
	public boolean isConnected(String signal, Object receiver, String slot);
}
