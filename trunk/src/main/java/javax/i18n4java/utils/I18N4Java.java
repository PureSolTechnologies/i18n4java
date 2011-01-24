package javax.i18n4java.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * This class provides some easy and efficient ways to perform standard
 * operations related to Java's I18n framework.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class I18N4Java {

	public static List<String> getISOLanguages() {
		List<String> isoLanguages = new ArrayList<String>();
		Collections.addAll(isoLanguages, Locale.getISOLanguages());
		Collections.sort(isoLanguages);
		return isoLanguages;
	}

	public static List<Locale> getAvailableLocales() {
		List<Locale> locales = new ArrayList<Locale>();
		Collections.addAll(locales, Locale.getAvailableLocales());
		return locales;
	}

	public static List<String> getAvailableLocaleNames() {
		List<String> localeNames = new ArrayList<String>();
		for (Locale locale : Locale.getAvailableLocales()) {
			localeNames.add(locale.toString());
		}
		Collections.sort(localeNames);
		return localeNames;
	}

	public static List<Locale> getLanguageLocales() {
		List<Locale> isoLanguages = new ArrayList<Locale>();
		for (String isoLanguage : getISOLanguages()) {
			isoLanguages.add(new Locale(isoLanguage));
		}
		return isoLanguages;
	}

}
