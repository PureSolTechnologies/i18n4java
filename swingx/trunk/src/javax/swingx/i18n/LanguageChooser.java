package javax.swingx.i18n;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.swingx.ComboBox;

public class LanguageChooser extends ComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Locale[] locales;
	private Vector<String> languageNames = null;
	private Hashtable<String, Locale> names2Locales = null;

	public LanguageChooser() {
		super();
		readLocales();
		insertLocales();
	}

	private void readLocales() {
		locales = Locale.getAvailableLocales();
		languageNames = new Vector<String>();
		names2Locales = new Hashtable<String, Locale>();
		for (int index = 0; index < locales.length; index++) {
			Locale locale = locales[index];
			String languageName = locale.getLanguage();
			languageNames.add(languageName);
			names2Locales.put(languageName, locale);
		}
		Collections.sort(languageNames);
	}

	private void insertLocales() {
		for (int index = 0; index < languageNames.size(); index++) {
			String languageName = languageNames.get(index);
			Locale locale = names2Locales.get(languageName);
			addItem(languageName + " / " + locale.getDisplayName());
		}
	}

	public Locale getSelectedLocale() {
		return names2Locales.get(languageNames.get(getSelectedIndex()));
	}

	public void setSelectedLocale(Locale locale) {
		for (int index = 0; index < locales.length; index++) {
			if (names2Locales.get(languageNames.get(index)).equals(locale)) {
				setSelectedIndex(index);
				break;
			}
		}
	}
}
