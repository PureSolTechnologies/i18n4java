package javax.swingx.i18n;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.swingx.ComboBox;

public class LanguageChooser extends ComboBox {

	private static final long serialVersionUID = -2438620845333994142L;

	private String[] languages;
	private Vector<String> languageCodes = null;
	private Hashtable<String, Locale> names2Locales = null;

	public LanguageChooser() {
		super();
		readLocales();
		insertLocales();
	}

	private void readLocales() {
		languages = Locale.getISOLanguages();
		languageCodes = new Vector<String>();
		names2Locales = new Hashtable<String, Locale>();
		for (String language : languages) {
			Locale locale = new Locale(language);
			languageCodes.add(language);
			names2Locales.put(language, locale);
		}
		Collections.sort(languageCodes);
	}

	private void insertLocales() {
		for (String languageCode : languageCodes) {
			Locale locale = names2Locales.get(languageCode);
			addItem(languageCode + " / " + locale.getDisplayName());
		}
	}

	public Locale getSelectedLocale() {
		return names2Locales.get(languageCodes.get(getSelectedIndex()));
	}

	public void setSelectedLocale(Locale locale) {
		for (int index = 0; index < languages.length; index++) {
			if (names2Locales.get(languageCodes.get(index)).equals(locale)) {
				setSelectedIndex(index);
				break;
			}
		}
	}
}
