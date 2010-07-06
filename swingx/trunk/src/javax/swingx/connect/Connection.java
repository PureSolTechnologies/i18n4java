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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swingx.data.TypeWrapper;

import org.apache.log4j.Logger;

/**
 * This class is used to store the information about a single connection and to
 * perform all checks on the connection.
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
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class Connection {

    private static final Logger logger = Logger.getLogger(Connection.class);

    /**
     * This pointer is used to store the reference to the emitter class.
     */
    private Object emitter = null;

    /**
     * This Method variable stores the information about the signal method.
     */
    private Method signal = null;

    /**
     * This pointer is used to store the reference to the receiver class.
     */
    private Object receiver = null;

    /**
     * This Method variable stores the information about the slot method.
     */
    private Method slot = null;
    private Class<?> types[] = null;

    public Connection(Object emitter, String signal, Object receiver,
	    String slot, Class<?>... types) {
	super();
	connect(emitter, signal, receiver, slot, types);
    }

    /**
     * This method is used to connect a signal method with a slot method. The
     * signal method should be annotated as
     * 
     * Signal and should be a member of a class with an implemented
     * SignalEmitter interface. The slot method has to be treated in a different
     * way. The slot has to be annotated as Slot and has to be member of a class
     * implementing the SignalReceiver interface.
     * 
     * For more details about the connection handling have a look to the
     * handbook.
     * 
     * @see Slot
     * @see Signal
     * @see SignalEmitter
     * @see SignalReceiver
     * 
     * @param emitter
     *            is a pointer to the signal class which emits the signal.
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
    private void connect(Object emitter, String signal, Object receiver,
	    String slot, Class<?>... types) {
	try {
	    Method slotMethod = findMethodAndMakeAccessible(receiver, slot,
		    types);
	    if (!slotMethod.isAccessible()) {
		slotMethod.setAccessible(true);
	    }

	    if (slotMethod.getAnnotation(Slot.class) == null) {
		logger.fatal("Slot '" + receiver.getClass().getName() + "."
			+ slot + "' does not annote @Slot! ");
		throw new RuntimeException();
	    }

	    Method signalMethod = findMethodAndMakeAccessible(emitter, signal,
		    types);

	    if (signalMethod.getAnnotation(Signal.class) == null) {
		logger.fatal("Signal '" + emitter.getClass().getName() + "."
			+ signal + "' does not annote @Signal! ");
		throw new RuntimeException();
	    }

	    if ((!signalMethod.getReturnType().equals(void.class))) {
		logger.fatal("Return value '"
			+ signalMethod.getReturnType().getName()
			+ "'for signal '" + emitter.getClass().getName() + "."
			+ signal + "' does not meet the requirement!");
		throw new RuntimeException();
	    }

	    // check exceptions...
	    Class<?> slotExceptions[] = slotMethod.getExceptionTypes();

	    if (slotExceptions.length != 0) {
		logger
			.fatal("Exceptions for slot '"
				+ receiver.getClass().getName()
				+ "."
				+ slot
				+ "' do not meet the requirement! "
				+ "A must not implement any exceptions due to the missing exception processing!");
		throw new RuntimeException();
	    }

	    this.receiver = receiver;
	    this.slot = slotMethod;
	    this.emitter = emitter;
	    this.signal = signalMethod;
	    this.types = types;

	    // check types for primitive types and convert to wrappers...
	    for (int i = 0; i < this.types.length; i++) {
		if (this.types[i].isPrimitive()) {
		    this.types[i] = TypeWrapper
			    .toPrimitiveWrapper(this.types[i]);
		}
	    }
	} catch (SecurityException e) {
	    logger.fatal(e.getMessage(), e);
	    throw new RuntimeException();
	}
    }

    private Method findMethodAndMakeAccessible(Object receiver, String slot,
	    Class<?>... types) {
	try {
	    Class<?> clazz = receiver.getClass();
	    Method method = null;
	    try {
		method = clazz.getDeclaredMethod(slot, types);
	    } catch (NoSuchMethodException e) {
	    }
	    while (method == null) {
		clazz = clazz.getSuperclass();
		if (clazz == null) {
		    throw new NoSuchMethodException("Could not find slot '"
			    + slot + "' in class '"
			    + receiver.getClass().getName() + "'!");
		}
		try {
		    method = clazz.getDeclaredMethod(slot, types);
		} catch (NoSuchMethodException e) {
		}
	    }
	    method.setAccessible(true);
	    return method;
	} catch (SecurityException e) {
	    logger.fatal(e.getMessage(), e);
	    throw new RuntimeException();
	} catch (NoSuchMethodException e) {
	    logger.fatal(e.getMessage(), e);
	    throw new RuntimeException();
	}
    }

    /**
     * This method is used to emit a signal to its receiver.
     * 
     * @param params
     *            are the parameters which are used to send to the slot.
     * @return A boolean is returned to represent the success of the emission
     *         process. True is returned if the slot were called correctly.
     *         False is returned otherwise.
     */
    public void emit(Object... params) {
	try {
	    getSlot().invoke(getReceiver(), params);
	} catch (IllegalAccessException e) {
	    logger.fatal(e.getMessage(), e);
	    throw new RuntimeException("Could not emit signal '" + toString()
		    + "' correctly! (" + e.getMessage() + ")");
	} catch (InvocationTargetException e) {
	    logger.fatal(e.getMessage(), e);
	    throw new RuntimeException("Could not emit signal '" + toString()
		    + "' correctly! (" + e.getMessage() + ")");
	}
    }

    /**
     * This method checks a object reference and method name pair to be the
     * signal in this connection.
     * 
     * @param emitter
     *            is a pointer to the signal class which emits the signal.
     * @param signal
     *            is the method's name which is the method which emit the
     *            signal.
     * @return True is returned if the object reference and the method name are
     *         the emitting class and the method in this connection.
     */
    public boolean isSignal(ConnectionHandler emitter, String signal,
	    Object... params) {
	Class<?> types[] = new Class<?>[params.length];
	for (int i = 0; i < params.length; i++) {
	    if (params[i] != null) {
		types[i] = params[i].getClass();
	    } else {
		types[i] = Object.class;
	    }
	}
	return isSignal(emitter, signal, types);
    }

    public boolean isSignal(ConnectionHandler emitter, String signal,
	    Class<?>... types) {
	if (getEmitter() != emitter) {
	    return false;
	}
	if (!getSignal().getName().equals(signal)) {
	    return false;
	}
	if (this.types.length != types.length) {
	    return false;
	}
	for (int i = 0; i < types.length; i++) {
	    if ((!types[i].equals(this.types[i]))
		    && (!this.types[i].isAssignableFrom(types[i]))) {
		return false;
	    }
	}
	return true;
    }

    /**
     * This method checks a object reference and method name pair to be the
     * slots in this connection.
     * 
     * @param receiver
     *            is the signal receiving class.
     * @param slot
     *            is the method which is to be called, when the signal was
     *            emitted.
     * @return True is returned if the object reference and the method name are
     *         the receiving class and the method in this connection.
     */
    public boolean isSlot(Object receiver, String slot, Object... params) {
	ArrayList<Class<?>> types = new ArrayList<Class<?>>();
	for (Object param : params) {
	    types.add(param.getClass());
	}
	return isSlot(receiver, slot, (Class<?>[]) types.toArray());
    }

    public boolean isSlot(Object receiver, String slot, Class<?>... types) {
	if (getReceiver() != receiver) {
	    return false;
	}
	if (!getSlot().getName().equals(slot)) {
	    return false;
	}
	if (this.types.length != types.length) {
	    return false;
	}
	for (int i = 0; i < types.length; i++) {
	    if (!this.types[i].equals(types[i])) {
		return false;
	    }
	}
	return true;
    }

    /**
     * This method returns the reference to the emitter.
     * 
     * @return An Object is returned with the reference.
     */
    public Object getEmitter() {
	return emitter;
    }

    /**
     * This method returns the method information about the signal method.
     * 
     * @return A Method object is returned containing the signal method
     *         information.
     */
    public Method getSignal() {
	return signal;
    }

    /**
     * This method returns the reference to the receiver.
     * 
     * @return An Object is returned with the reference.
     */
    public Object getReceiver() {
	return receiver;
    }

    /**
     * This method returns the method information about the slot method.
     * 
     * @return A Method object is returned containing the slot method
     *         information.
     */
    public Method getSlot() {
	return slot;
    }

    public Class<?>[] getParameterTypes() {
	return types;
    }

    /**
     * This method returns a simple text representation of the content of the
     * connection for command line or log output.
     * 
     * @return A String is returned with a text representation of the
     *         connection.
     */
    public String toString() {
	return "signal " + emitter.getClass().getName() + "." + signal
		+ "--> slot " + receiver.getClass().getName() + "." + slot;
    }
}
