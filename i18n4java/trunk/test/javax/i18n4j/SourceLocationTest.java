package javax.i18n4j;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SourceLocationTest extends TestCase {

	@Test
	public void testDefaultConstructor() {
		SourceLocation sourceLocation = new SourceLocation();
		Assert.assertEquals("", sourceLocation.getFile());
		Assert.assertEquals(0, sourceLocation.getLine());
		Assert.assertEquals(0, sourceLocation.getLineCount());
	}

	@Test
	public void testConstructors() {
		SourceLocation sourceLocation = new SourceLocation("File1.java", 100);
		Assert.assertEquals("File1.java", sourceLocation.getFile());
		Assert.assertEquals(100, sourceLocation.getLine());
		Assert.assertEquals(1, sourceLocation.getLineCount());

		sourceLocation = new SourceLocation("File2.java", 200, 2);
		Assert.assertEquals("File2.java", sourceLocation.getFile());
		Assert.assertEquals(200, sourceLocation.getLine());
		Assert.assertEquals(2, sourceLocation.getLineCount());
	}

	@Test
	public void testSettersAndGetters() {
		SourceLocation sourceLocation = new SourceLocation();
		sourceLocation.setFile("File.java");
		sourceLocation.setLine(1);
		sourceLocation.setLineCount(2);

		Assert.assertEquals("File.java", sourceLocation.getFile());
		Assert.assertEquals(1, sourceLocation.getLine());
		Assert.assertEquals(2, sourceLocation.getLineCount());
	}
}
