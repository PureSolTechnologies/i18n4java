package javax.i18n4j;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SingleLanguageTranslationsTest extends TestCase {

	@Test
	public void testDefaultConstructor() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		Assert.assertEquals("Nothing in", translations.get("Nothing in"));
	}

	@Test
	public void testSetterAndGetter() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		translations.set("Source", "Quelle");
		translations.set("Translation", "Uebersetzung");
		Assert.assertEquals("Quelle", translations.get("Source"));
		Assert.assertEquals("Uebersetzung", translations.get("Translation"));
	}

	@Test
	public void testToString() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		translations.set("Source", "Quelle");
		translations.set("Translation", "Uebersetzung");
		System.out.println(translations.toString());
		Assert.assertEquals(
				"Translation --> Uebersetzung\nSource --> Quelle\n",
				translations.toString());
	}

	@Test
	public void testHashCode() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		Assert.assertTrue(translations.hashCode() > 0);
	}

	@Test
	public void testEquals() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		Assert.assertTrue(translations.equals(translations));
		Assert.assertFalse(translations.equals(null));
		SingleLanguageTranslations translations2 = new SingleLanguageTranslations();
		Assert.assertTrue(translations.equals(translations2));
		Assert.assertTrue(translations2.equals(translations));

		translations.set("Source", "Quelle");
		Assert.assertFalse(translations.equals(translations2));
		Assert.assertFalse(translations2.equals(translations));

		translations2.set("Source", "Quelle");
		Assert.assertTrue(translations.equals(translations2));
		Assert.assertTrue(translations2.equals(translations));
	}

	@Test
	public void testClone() {
		SingleLanguageTranslations origin = new SingleLanguageTranslations();
		origin.set("Source", "Quelle");
		SingleLanguageTranslations cloned = (SingleLanguageTranslations) origin
				.clone();
		Assert.assertNotSame(origin, cloned);
		Assert.assertEquals(origin, cloned);
		cloned.set("Source", "Quelle");
		Assert.assertEquals(origin, cloned);
		cloned.set("Source2", "Quelle2");
		Assert.assertFalse(origin.equals(cloned));
	}
}
