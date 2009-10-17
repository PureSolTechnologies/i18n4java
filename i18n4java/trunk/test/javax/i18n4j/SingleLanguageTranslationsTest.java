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
}
