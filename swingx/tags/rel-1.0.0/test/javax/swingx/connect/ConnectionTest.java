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
