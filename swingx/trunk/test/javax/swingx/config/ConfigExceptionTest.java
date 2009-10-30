package javax.swingx.config;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ConfigExceptionTest extends TestCase {

	@Test
	public void testConstructor() {
		ConfigException exception = new ConfigException("Test Message");
		Assert.assertEquals("Test Message", exception.getMessage());
	}
}
