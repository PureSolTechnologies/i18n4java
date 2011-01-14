package javax.i18n4java;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;

import org.junit.Test;

public class I18N4JavaTest {

	@Test
	public void testGetISOLanguages() {
		List<String> languages = I18N4Java.getISOLanguages();
		assertNotNull(languages);
		assertTrue(languages.size() > 0);
	}

	@Test
	public void testGetAvailableLocales() {
		List<Locale> locales = I18N4Java.getAvailableLocales();
		assertNotNull(locales);
		assertTrue(locales.size() > 0);
	}

	@Test
	public void testGetAvailableLocaleNames() {
		List<String> localeNames = I18N4Java.getAvailableLocaleNames();
		assertNotNull(localeNames);
		assertTrue(localeNames.size() > 0);
	}

	@Test
	public void testGetLanguageLocales() {
		List<Locale> languageLocales = I18N4Java.getLanguageLocales();
		assertNotNull(languageLocales);
		assertTrue(languageLocales.size() > 0);
	}
}
