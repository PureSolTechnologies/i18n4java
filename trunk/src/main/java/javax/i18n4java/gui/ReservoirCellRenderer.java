package javax.i18n4java.gui;

import java.awt.Component;
import java.util.Locale;

import javax.i18n4java.data.LanguageSet;
import javax.i18n4java.data.MultiLanguageTranslations;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ReservoirCellRenderer implements ListCellRenderer {

	private MultiLanguageTranslations translationsHash;
	private Locale language = Locale.getDefault();

	public ReservoirCellRenderer() {
		super();
	}

	public ReservoirCellRenderer(MultiLanguageTranslations translationsHash) {
		super();
		this.translationsHash = translationsHash;
	}

	/**
	 * @return the translationsHash
	 */
	public MultiLanguageTranslations getTranslationsHash() {
		return translationsHash;
	}

	/**
	 * @param translationsHash
	 *            the translationsHash to set
	 */
	public void setTranslationsHash(MultiLanguageTranslations translationsHash) {
		this.translationsHash = translationsHash;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return language;
	}

	public void setLocale(Locale locale) {
		this.language = locale;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		String source = (String) value;

		boolean finished = false;
		LanguageSet languageSet = translationsHash.getTranslations(source);
		if (languageSet == null) {
			return new StatusComponent(source, isSelected, cellHasFocus, false);
		}
		String translation = languageSet.get(language);
		if ((translation != null) && (!translation.isEmpty())) {
			finished = true;
		}
		return new StatusComponent(source, isSelected, cellHasFocus, finished);
	}
}
