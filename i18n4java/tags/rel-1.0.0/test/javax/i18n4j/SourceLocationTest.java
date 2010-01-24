package javax.i18n4j;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SourceLocationTest extends TestCase {

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
		SourceLocation sourceLocation = new SourceLocation("File", 1, 1);
		sourceLocation.setFile("File.java");
		sourceLocation.setLine(2);
		sourceLocation.setLineCount(3);

		Assert.assertEquals("File.java", sourceLocation.getFile());
		Assert.assertEquals(2, sourceLocation.getLine());
		Assert.assertEquals(3, sourceLocation.getLineCount());
	}

	@Test
	public void testHashCode() {
		SourceLocation sourceLocation = new SourceLocation("File", 1, 2);
		System.out.println(sourceLocation.hashCode());
		Assert.assertTrue(sourceLocation.hashCode() > 0);
		sourceLocation.setLine(1);
		System.out.println(sourceLocation.hashCode());
		Assert.assertTrue(sourceLocation.hashCode() > 0);
		sourceLocation.setLineCount(2);
		System.out.println(sourceLocation.hashCode());
		Assert.assertTrue(sourceLocation.hashCode() > 0);
	}

	@Test
	public void testEquals() {
		SourceLocation sl1 = new SourceLocation("File", 1, 2);
		Assert.assertTrue(sl1.equals(sl1));
		Assert.assertFalse(sl1.equals(null));
		Assert.assertFalse(sl1.equals("Test"));

		SourceLocation sl2 = new SourceLocation("File", 1, 2);
		Assert.assertTrue(sl1.equals(sl2));
		Assert.assertTrue(sl2.equals(sl1));
		sl1.setFile("File.java");
		Assert.assertFalse(sl1.equals(sl2));
		Assert.assertFalse(sl2.equals(sl1));
		sl2.setFile("File.java");
		Assert.assertTrue(sl1.equals(sl2));
		Assert.assertTrue(sl2.equals(sl1));
		sl1.setLine(2);
		Assert.assertFalse(sl1.equals(sl2));
		Assert.assertFalse(sl2.equals(sl1));
		sl2.setLine(2);
		Assert.assertTrue(sl1.equals(sl2));
		Assert.assertTrue(sl2.equals(sl1));
		sl1.setLineCount(3);
		Assert.assertFalse(sl1.equals(sl2));
		Assert.assertFalse(sl2.equals(sl1));
		sl2.setLineCount(3);
		Assert.assertTrue(sl1.equals(sl2));
		Assert.assertTrue(sl2.equals(sl1));
	}

	@Test
	public void testClone() {
		SourceLocation origin = new SourceLocation("File.java", 1, 2);
		SourceLocation cloned = (SourceLocation) origin.clone();
		Assert.assertTrue(origin.equals(cloned));
		Assert.assertEquals(0, origin.compareTo(cloned));
		Assert.assertEquals(0, cloned.compareTo(origin));

		cloned.setFile("NewFile.java");
		Assert.assertFalse(origin.equals(cloned));

		cloned.setFile("File.java");
		Assert.assertTrue(origin.equals(cloned));

		cloned.setLine(2);
		Assert.assertFalse(origin.equals(cloned));

		cloned.setLine(1);
		Assert.assertTrue(origin.equals(cloned));

		cloned.setLineCount(3);
		Assert.assertFalse(origin.equals(cloned));
	}

	@Test
	public void testExceptions() {
		SourceLocation sourceLocation = new SourceLocation("File", 1, 2);
		try {
			sourceLocation.setFile(null);
			Assert
					.fail("An IllegalArguementException was expected due to a null file!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			sourceLocation.setFile("");
			Assert
					.fail("An IllegalArguementException was expected due to an empty file!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			sourceLocation.setLine(0);
			Assert
					.fail("An IllegalArguementException was expected due to 0 line number!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			sourceLocation.setLine(-2);
			Assert
					.fail("An IllegalArguementException was expected due to negative line number!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			sourceLocation.setLineCount(0);
			Assert
					.fail("An IllegalArguementException was expected due to 0 line count!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			sourceLocation.setLineCount(-3);
			Assert
					.fail("An IllegalArguementException was expected due to negative line count!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
	}

	@Test
	public void testCompareTo() {
		SourceLocation default1 = new SourceLocation("File", 1, 2);
		SourceLocation default2 = new SourceLocation("File", 1, 2);
		Assert.assertEquals(0, default1.compareTo(default2));
		Assert.assertEquals(0, default2.compareTo(default1));
		Assert.assertTrue(default1.equals(default2));
		Assert.assertTrue(default2.equals(default1));
		Assert.assertEquals(0, default1.compareTo(default1));
		Assert.assertEquals(-1, default1.compareTo(null));

		default1.setFile("File1.java");
		Assert.assertEquals(1, default1.compareTo(default2));
		Assert.assertEquals(-1, default2.compareTo(default1));
		Assert.assertFalse(default1.equals(default2));
		Assert.assertFalse(default2.equals(default1));

		default2.setFile("File2.java");
		Assert.assertEquals(-1, default1.compareTo(default2));
		Assert.assertEquals(1, default2.compareTo(default1));
		Assert.assertFalse(default1.equals(default2));
		Assert.assertFalse(default2.equals(default1));

		default1.setFile("File.java");
		default2.setFile("File.java");
		default1.setLine(1);
		default2.setLine(2);
		Assert.assertEquals(-1, default1.compareTo(default2));
		Assert.assertEquals(1, default2.compareTo(default1));
		Assert.assertFalse(default1.equals(default2));
		Assert.assertFalse(default2.equals(default1));

		default2.setLine(1);
		default1.setLineCount(2);
		default2.setLineCount(3);
		Assert.assertEquals(-1, default1.compareTo(default2));
		Assert.assertEquals(1, default2.compareTo(default1));
		Assert.assertFalse(default1.equals(default2));
		Assert.assertFalse(default2.equals(default1));
	}

	@Test
	public void testToString() {
		Assert.assertEquals("File.java:1", new SourceLocation("File.java", 1)
				.toString());
		Assert.assertEquals("File.java:1-2", new SourceLocation("File.java", 1,
				2).toString());
	}
}
