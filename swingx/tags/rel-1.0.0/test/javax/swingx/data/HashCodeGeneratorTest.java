package javax.swingx.data;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class HashCodeGeneratorTest extends TestCase {

	@Test
	public void testMD5() {
		String code = HashCodeGenerator.getMD5("TEST!");
		Assert.assertEquals("7c36f14325954cd6cf996f8ee1261d56", code);
	}

	@Test
	public void testSHA() {
		String code = HashCodeGenerator.getSHA("TEST!");
		Assert.assertEquals("c079fbc11f067bfa8115f53341c9d455a56c6", code);
	}
}
