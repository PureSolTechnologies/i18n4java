package javax.i18n4j;

import java.util.Vector;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MultiLanguageTranslationsTest extends TestCase {

	@Test
	public void testDefaultConstructor() {
		MultiLanguageTranslations translations = new MultiLanguageTranslations();
		Assert.assertEquals(0, translations.getAvailableLanguages().size());
		Assert.assertEquals(0, translations.getSources().size());
	}

	@Test
	public void testFrom() {
		MultiLanguageTranslations translations = MultiLanguageTranslations
				.from("Source", "File.java", 123);
		Assert.assertNotNull(translations);
		Assert.assertEquals(0, translations.getAvailableLanguages().size());
		Assert.assertEquals(1, translations.getSources().size());
		LanguageSet languageSet = translations.get("Source");
		Assert.assertNotNull(languageSet);
		Assert.assertEquals(0, languageSet.getAvailableLanguages().size());
		Vector<SourceLocation> locations = languageSet.getLocations();
		Assert.assertNotNull(locations);
		Assert.assertEquals(1, locations.size());
	}

	@Test
	public void testSettersAndGetters() {
		MultiLanguageTranslations translations = new MultiLanguageTranslations();
		// TODO proceed here!
	}
}
