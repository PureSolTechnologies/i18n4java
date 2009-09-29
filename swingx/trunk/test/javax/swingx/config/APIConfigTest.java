package javax.swingx.config;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class APIConfigTest extends TestCase {

	@Test
	public void testGettersAndSetters() {
		APIConfig.setApplicationName("AppName");
		APIConfig.setHelpRequest(1);
		APIConfig.setVersionRequest(2);
		Assert.assertEquals("AppName", APIConfig.getApplicationName());
		Assert.assertEquals(1, APIConfig.getHelpRequest());
		Assert.assertEquals(2, APIConfig.getVersionRequest());
	}
}
