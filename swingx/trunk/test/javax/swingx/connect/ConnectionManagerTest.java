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

	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
	}

	public boolean isConnected(String signal, Object receiver, String slot) {
		return false;
	}

	public void release(String signal, Object receiver, String slot) {
	}

	@Test
	public void testCreation() {
		ConnectionManager manager = ConnectionManager.createFor(this);
		Assert.assertEquals(this, manager.getEmitter());
	}

	@Test
	public void testConnect() {
		ConnectionManager manager = ConnectionManager.createFor(this);
		// manager.connect("signal", this, "slot");
		Assert.assertEquals(this, manager.getEmitter());
	}

}
