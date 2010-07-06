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

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test object for general ConnectionManager testing.
 * 
 * @see com.qsys.api.ui.connection.ConnectionManager
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class ConnectionManagerTest extends TestCase implements
		ConnectionHandler {

	private ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.connect(signal, receiver, slot, types);
	}

	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		return connectionManager.isConnected(signal, receiver, slot, types);
	}

	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.release(signal, receiver, slot, types);
	}

	private int testIntValue = 0;
	private String testStringValue = "";
	private boolean testBooleanValue = false;

	@Signal
	public void testSignal() {
	}

	@Slot
	public void testSlot() {
		testIntValue++;
	}

	@Signal
	public void testSignal(String value) {
	}

	@Slot
	public void testSlot(String value) {
		testStringValue = value;
	}

	@Signal
	public void testSignal(boolean value) {
	}

	@Slot
	public void testSlot(boolean value) {
		testBooleanValue = value;
	}

	@Test
	public void testCreation() {
		ConnectionManager manager = ConnectionManager.createFor(this);
		Assert.assertEquals(this, manager.getEmitter());
	}

	@Test
	public void testConnect() {
		connectionManager.connect("testSignal", this, "testSlot");
		connectionManager.connect("testSignal", this, "testSlot", String.class);
		connectionManager
				.connect("testSignal", this, "testSlot", boolean.class);
		Assert.assertEquals(0, testIntValue);
		Assert.assertEquals("", testStringValue);
		Assert.assertEquals(false, testBooleanValue);

		connectionManager.emitSignal("testSignal");
		Assert.assertEquals(1, testIntValue);
		Assert.assertEquals("", testStringValue);
		Assert.assertEquals(false, testBooleanValue);

		connectionManager.emitSignal("testSignal", "Hallo!");
		Assert.assertEquals(1, testIntValue);
		Assert.assertEquals("Hallo!", testStringValue);
		Assert.assertEquals(false, testBooleanValue);

		connectionManager.emitSignal("testSignal", true);
		Assert.assertEquals(1, testIntValue);
		Assert.assertEquals("Hallo!", testStringValue);
		Assert.assertEquals(true, testBooleanValue);
	}
}
