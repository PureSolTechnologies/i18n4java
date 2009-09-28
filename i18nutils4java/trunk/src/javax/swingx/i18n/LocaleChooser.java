package javax.swingx.i18n;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.swingx.ComboBox;

public class LocaleChooser extends ComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Locale[] locales;
	private Vector<String> localeNames = null;
	private Hashtable<String, Locale> names2Locales = null;

	public LocaleChooser() {
		super();
		readLocales();
		insertLocales();
	}

	private void readLocales() {
		locales = Locale.getAvailableLocales();
		localeNames = new Vector<String>();
		names2Locales = new Hashtable<String, Locale>();
		for (int index = 0; index < locales.length; index++) {
			Locale locale = locales[index];
			String localeName = locale.toString();
			localeNames.add(localeName);
			names2Locales.put(localeName, locale);
		}
		Collections.sort(localeNames);
	}

	private void insertLocales() {
		for (int index = 0; index < localeNames.size(); index++) {
			String localeName = localeNames.get(index);
			Locale locale = names2Locales.get(localeName);
			addItem(localeName + " / " + locale.getDisplayName());
		}
	}

	public Locale getSelectedLocale() {
		return names2Locales.get(localeNames.get(getSelectedIndex()));
	}

	public void setSelectedLocale(Locale locale) {
		for (int index = 0; index < locales.length; index++) {
			if (names2Locales.get(localeNames.get(index)).equals(locale)) {
				setSelectedIndex(index);
				break;
			}
		}
	}
}
