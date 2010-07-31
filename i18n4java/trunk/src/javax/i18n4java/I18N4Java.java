package javax.i18n4java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class I18N4Java {

	public static List<String> getISOLanguages() {
		List<String> isoLanguages = new ArrayList<String>();
		for (String isoLanguage : Locale.getISOLanguages()) {
			isoLanguages.add(isoLanguage);
		}
		Collections.sort(isoLanguages);
		return isoLanguages;
	}

	public static List<Locale> getAvailableLocales() {
		List<Locale> locales = new ArrayList<Locale>();
		for (Locale locale : Locale.getAvailableLocales()) {
			locales.add(locale);
		}
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
