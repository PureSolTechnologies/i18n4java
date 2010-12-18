/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *
 ***************************************************************************/

package javax.swingx.connect;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

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

	/**
	 * This Vector stores all connections which were established.
	 */
	private Set<Connection> connections = new LinkedHashSet<Connection>();

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
		connections.add(new Connection(emitter, signal, receiver, slot, types));
	}

	/**
	 * {@inheritDoc}
	 */
	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		connections.remove(new Connection(emitter, signal, receiver, slot,
				types));
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		return connections.contains(new Connection(emitter, signal, receiver,
				slot, types));
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
	public void emitSignal(String signal, Object... params) {
		for (Connection connection : connections) {
			if (connection.isSignal(emitter, signal, params)) {
				connection.emit(params);
			}
		}
	}
}
