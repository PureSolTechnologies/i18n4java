package javax.swingx.config;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class ClassRegistryElementTest extends TestCase {

    @Test
    public void testConstructor() {
	ClassRegistryElement element =
		new ClassRegistryElement(ClassRegistryElementType.FACTORY,
			"Test");
	Assert.assertEquals(ClassRegistryElementType.FACTORY, element
		.getType());
	Assert.assertEquals("Test", element.getClassName());
    }
}
