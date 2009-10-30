package javax.swingx.config;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class ClassRegistryElementTest extends TestCase {

	@Test
	public void testConstructor() {
		ClassRegistryElement element = new ClassRegistryElement(1, "Test");
		Assert.assertEquals(1, element.getType());
		Assert.assertEquals("Test", element.getClassName());
	}

	@Test
	public void testIllegalArguments() {
		try {
			new ClassRegistryElement(-1, "Test");
			Assert.fail("IllegalArgumentException was expected due to type<0");
		} catch (IllegalArgumentException e) {
			// exception was exptected
		}
		try {
			new ClassRegistryElement(3, "Test");
			Assert.fail("IllegalArgumentException was expected due to type>2");
		} catch (IllegalArgumentException e) {
			// exception was exptected
		}
	}
}
