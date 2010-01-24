package javax.swingx.config;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ClassRegistryElementTypeTest extends TestCase {

    @Test
    public void testFrom() {
	Assert.assertEquals(ClassRegistryElementType.CLONED,
		ClassRegistryElementType.from("cloned"));
	Assert.assertEquals(ClassRegistryElementType.CLONED,
		ClassRegistryElementType.from("CLONED"));

	Assert.assertEquals(ClassRegistryElementType.FACTORY,
		ClassRegistryElementType.from("factory"));
	Assert.assertEquals(ClassRegistryElementType.FACTORY,
		ClassRegistryElementType.from("FACTORY"));

	Assert.assertEquals(ClassRegistryElementType.SINGLETON,
		ClassRegistryElementType.from("singleton"));
	Assert.assertEquals(ClassRegistryElementType.SINGLETON,
		ClassRegistryElementType.from("SINGLETON"));
    }

    @Test
    public void testInvalidName() {
	try {
	    ClassRegistryElementType.from("INVALID");
	    Assert.fail("IllegalArgumentException was expected!");
	} catch (IllegalArgumentException e) {
	    // nothing to catch, because it was expected...
	}
    }

}
