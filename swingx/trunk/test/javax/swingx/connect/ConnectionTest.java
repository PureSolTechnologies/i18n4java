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

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ConnectionTest extends TestCase {

	private int testIntValue = 0;
	private String testStringValue = "";

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

	@Test
	public void testConstructor() {
		Connection connection = new Connection(this, "testSignal", this,
				"testSlot");
		Assert.assertEquals(this, connection.getEmitter());
		Assert.assertEquals(this, connection.getReceiver());
		try {
			Assert.assertEquals(getClass().getMethod("testSignal"), connection
					.getSignal());
			Assert.assertEquals(getClass().getMethod("testSlot"), connection
					.getSlot());
		} catch (SecurityException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testEmit() {
		Connection connection = new Connection(this, "testSignal", this,
				"testSlot");
		Assert.assertEquals(0, testIntValue);
		Assert.assertEquals("", testStringValue);
		connection.emit();
		Assert.assertEquals(1, testIntValue);
		Assert.assertEquals("", testStringValue);
		try {
			connection.emit("Hallo!");
			Assert.fail("IllegalArgumentException had to be thrown!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected
		}

		connection = new Connection(this, "testSignal", this, "testSlot",
				String.class);
		Assert.assertEquals(1, testIntValue);
		Assert.assertEquals("", testStringValue);
		connection.emit("Signal emitted");
		Assert.assertEquals(1, testIntValue);
		Assert.assertEquals("Signal emitted", testStringValue);
		try {
			connection.emit();
			Assert.fail("IllegalArgumentException had to be thrown!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected
		}
	}
}
