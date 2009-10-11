package javax.i18n4j;

import java.util.Set;
import java.util.Vector;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class LanguageSetTest extends TestCase {

	@Test
	public void testDefaultConstructor() {
		LanguageSet set = new LanguageSet();
		Assert.assertEquals("", set.getSource());
		Assert.assertNotNull(set.getLocations());
		Assert.assertEquals(0, set.getLocations().size());
		Assert.assertEquals(0, set.getAvailableLanguages().size());
	}

	@Test
	public void testConstructors() {
		LanguageSet set = new LanguageSet("Source String");
		Assert.assertEquals("Source String", set.getSource());
		Assert.assertNotNull(set.getLocations());
		Assert.assertEquals(0, set.getLocations().size());
		Assert.assertEquals(0, set.getAvailableLanguages().size());
	}

	@Test
	public void testSettersAndGetters() {
		LanguageSet set = new LanguageSet();
		// settings...
		set.setSource("Source String");
		set.set("de", "Quellzeichenkette");

		LanguageSet translated = new LanguageSet("Source String");
		translated.set("xx", "XXX");
		translated.set("yy", "YYY");
		translated.addLocation(new SourceLocation("translated.java", 1, 1));
		set.add(translated);

		set.addLocation(new SourceLocation("TestFile.java", 1, 2));
		// no double entry(!):
		set.addLocation(new SourceLocation("TestFile.java", 1, 2));

		Vector<SourceLocation> locations = new Vector<SourceLocation>();
		locations.add(new SourceLocation("TestFile2.java", 2, 3));
		locations.add(new SourceLocation("TestFile3.java", 3, 4));
		set.addLocations(locations);

		// check for correct settings...
		Assert.assertEquals("Source String", set.getSource());
		Set<String> langs = set.getAvailableLanguages();
		Assert.assertEquals(3, langs.size());
		Assert.assertTrue(langs.contains("de"));
		Assert.assertTrue(langs.contains("xx"));
		Assert.assertTrue(langs.contains("yy"));
		Assert.assertEquals("Quellzeichenkette", set.get("de"));
		Assert.assertEquals("XXX", set.get("xx"));
		Assert.assertEquals("YYY", set.get("yy"));
		locations = set.getLocations();
		Assert.assertEquals(4, locations.size());
		Assert.assertTrue(locations.contains(new SourceLocation(
				"translated.java", 1, 1)));
		Assert.assertTrue(locations.contains(new SourceLocation(
				"TestFile.java", 1, 2)));
		Assert.assertTrue(locations.contains(new SourceLocation(
				"TestFile2.java", 2, 3)));
		Assert.assertTrue(locations.contains(new SourceLocation(
				"TestFile3.java", 3, 4)));

		// check clearLocations()...
		set.clearLocations();
		Assert.assertNotNull(set.getLocations());
		Assert.assertEquals(0, set.getLocations().size());
	}

	@Test
	public void testException() {
		LanguageSet set = new LanguageSet("Source String1");
		LanguageSet translated = new LanguageSet("Source String 2");
		try {
			set.add(translated);
			Assert
					.fail("Illegal ArgumentException was expected due to two different source strings!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected...
		}
	}

	@Test
	public void testEquals() {
		LanguageSet set1 = new LanguageSet();
		Assert.assertTrue(set1.equals(set1));
		Assert.assertFalse(set1.equals(null));
		Assert.assertFalse(set1.equals(new String("Test")));

		LanguageSet set2 = new LanguageSet();
		Assert.assertTrue(set1.equals(set2));
		Assert.assertTrue(set2.equals(set1));
		set1.setSource("Source");
		Assert.assertFalse(set1.equals(set2));
		Assert.assertFalse(set2.equals(set1));
		set2.setSource("Source");
		Assert.assertTrue(set1.equals(set2));
		Assert.assertTrue(set2.equals(set1));
		set1.addLocation(new SourceLocation("source.java", 1, 2));
		Assert.assertFalse(set1.equals(set2));
		Assert.assertFalse(set2.equals(set1));
		set2.addLocation(new SourceLocation("source.java", 1, 2));
		Assert.assertTrue(set1.equals(set2));
		Assert.assertTrue(set2.equals(set1));

		Assert.assertTrue(set1.equals(set1));
		Assert.assertFalse(set1.equals(null));
		Assert.assertFalse(set1.equals(new String("Test")));
	}

	@Test
	public void testHashCode() {
		LanguageSet set = new LanguageSet();
		Assert.assertTrue(set.hashCode() > 0);
	}

	@Test
	public void testClose() {
		LanguageSet origin = new LanguageSet("Source String");
		origin.set("de", "Quellzeichenkette");
		origin.addLocation(new SourceLocation("File.java", 1, 2));
		LanguageSet cloned = (LanguageSet) origin.clone();
		Assert.assertNotNull(cloned);
		Assert.assertEquals(origin, cloned);
		cloned.setSource("New Source String");
		Assert.assertFalse(origin.equals(cloned));
		cloned.setSource("Source String");
		Assert.assertTrue(origin.equals(cloned));
		cloned.set("de", "Neue Quellzeichenkette");
		Assert.assertFalse(origin.equals(cloned));
		cloned.set("de", "Quellzeichenkette");
		Assert.assertTrue(origin.equals(cloned));
		cloned.addLocation(new SourceLocation("File.java", 10, 3));
		Assert.assertFalse(origin.equals(cloned));
	}
}
