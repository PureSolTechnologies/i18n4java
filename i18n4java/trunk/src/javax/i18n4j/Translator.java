/***************************************************************************
 *
 *   Translator.java
 *   -------------------
 *   copyright            : (c) 2009 by Rick-Rainer Ludwig
 *   author               : Rick-Rainer Ludwig
 *   email                : rl719236@sourceforge.net
 *
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package javax.i18n4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * This small object was inspired by QT's approach to internationalization which
 * is much more flexible and much easier to use. This object was to be created
 * because the String object is final and a tt() function could not be
 * implemented in that way.
 * 
 * This class is the central translator for all translations. It's instanciated
 * at best as
 * <tt>private static final Translator translator = Translator.getTranslator(Class)</tt>
 * 
 * @author Rick-Rainer Ludwig
 */
public class Translator implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(Translator.class);

	private static Locale defaultLocale = Locale.getDefault();
	private static Locale[] additionalLocales = null;
	/**
	 * This variable keeps the references to the system wide unique context
	 * sensitive instances.
	 */
	private static Hashtable<String, Translator> instances = new Hashtable<String, Translator>();

	/**
	 * This method creates a new instance of Translator. This method should only
	 * be used, if it is really necessary. getInstance() should be used instead.
	 * 
	 * @see #getTranslator()
	 * 
	 * @return A reference to a newly created Translator object is returned.
	 */
	public static Translator newInstance(Class<?> clazz) {
		logger.debug("Creating instance for class '" + clazz.getName() + "'");
		return new Translator(clazz);
	}

	/**
	 * This method creates a the unique instance of Translator for Singleton
	 * pattern. This method is synchronized to assure only one instance. This
	 * method is exclusively called by getInstance to create the unique instance
	 * in case it was not created before.
	 * 
	 * @see #getTranslator()
	 * 
	 * @return A reference to a newly created Translator object is returned.
	 */
	private static synchronized Translator createInstance(Class<?> clazz) {
		String context = clazz.getName();
		if (!instances.containsKey(context)) {
			instances.put(context, newInstance(clazz));
		}
		return instances.get(context);
	}

	/**
	 * This method returns the static instance to Translator which is unique to
	 * the whole system. This is the static method to build the Singleton
	 * pattern.
	 * 
	 * @return A reference to the static held Translator object is returned.
	 */
	public static Translator getTranslator(Class<?> clazz) {
		String context = clazz.getName();
		Translator translator = (Translator) instances.get(context);
		if (translator == null) {
			translator = createInstance(clazz);
		}
		return translator;
	}

	static public void setDefault(Locale locale) {
		logger.info("Set default locale to '" + locale.toString() + "'");
		defaultLocale = locale;
		resetAllInstances();
	}

	static public Locale getDefault() {
		return defaultLocale;
	}

	static public String getDefaultLanguage() {
		return getDefault().getLanguage();
	}

	static public String getDefaultCountry() {
		return getDefault().getCountry();
	}

	static public void setSingleLanguageMode() {
		logger.info("Set to single language mode");
		additionalLocales = null;
		resetAllInstances();
	}

	static public void setAdditionalLocales(Locale... additionalLocales) {
		if (logger.isInfoEnabled()) {
			String s = "Set additional locales: ";
			boolean first = true;
			for (Locale locale : additionalLocales) {
				if (!first) {
					s += ", ";
				}
				s += locale.toString();
			}
			logger.info(s);
		}
		Translator.additionalLocales = additionalLocales;
		resetAllInstances();
	}

	static public Locale[] getAdditionalLocales() {
		return additionalLocales;
	}

	static private void resetAllInstances() {
		logger.info("Reset all instances");
		for (String context : instances.keySet()) {
			instances.get(context).reset();
		}
	}

	/**
	 * This is the Hashtable for the context translations. Everything is kept in
	 * there. The context is the name of the class including the package name.
	 */
	private Hashtable<String, SingleLanguageTranslations> translations = null;

	/**
	 * This variable keeps the name of the class which is used as a context.
	 */
	private String context = "";

	/**
	 * This is the standard constructor which performs some default
	 * initializations if needed. This constructor is private to assure the
	 * Singleton pattern. It would not be wise to have a lot of Translator
	 * instances. The memory could be filled heavily if we would hold a lot of
	 * Translator objects and the time for reading the translation files
	 * multiple times is also not to be forgotten.
	 * 
	 * @param context
	 *            is the objects context for what the translator is to be
	 *            created.
	 */
	private Translator(Class<?> clazz) {
		context = clazz.getName();
	}

	private void reset() {
		logger.info("reset '" + context + "'");
		translations = null;
	}

	protected void setTranslation(String source, String language,
			String translation) {
		if (translations == null) {
			translations = new Hashtable<String, SingleLanguageTranslations>();
		}
		if (!translations.containsKey(language)) {
			translations.put(language, new SingleLanguageTranslations());
		}
		translations.get(language).set(source, translation);
	}

	private void readContextTranslation() {
		if (translations == null) {
			translations = new Hashtable<String, SingleLanguageTranslations>();
		}
		readContextTranslation(getDefaultLanguage());
		if (getAdditionalLocales() != null) {
			for (Locale addLocale : getAdditionalLocales()) {
				readContextTranslation(addLocale.getLanguage());
			}
		}
	}

	private void readContextTranslation(String language) {
		logger.debug("read context translation for context '" + context
				+ "' and language '" + language + "'");
		if (language.equals("en")) {
			translations.put("en", new SingleLanguageTranslations());
			return;
		}
		File file = I18NFile.getI18NFile(context, language);
		logger.info("Read context language file '" + file.getPath() + "'");
		InputStream is = getClass().getResourceAsStream(file.getPath());
		SingleLanguageTranslations translations = readFromStream(is);
		this.translations.put(language, translations);
	}

	private SingleLanguageTranslations readFromStream(InputStream is) {
		try {
			SingleLanguageTranslations translations = I18NFile
					.readSingleLanguageFile(is);
			return translations;
		} catch (IOException e) {
			return new SingleLanguageTranslations();
		}
	}

	public String i18n(String text) {
		if (translations == null) {
			readContextTranslation();
		}
		String translation = translations.get(getDefaultLanguage()).get(text);
		boolean useLineBreak = false;
		if (translation.contains("\n")) {
			useLineBreak = true;
		}
		if (additionalLocales != null) {
			for (Locale locale : getAdditionalLocales()) {
				if (useLineBreak) {
					translation += "\n";
				} else {
					translation += " ";
				}
				translation += "("
						+ translations.get(locale.getLanguage()).get(text)
						+ ")";
			}
		}
		return translation;
	}

	/**
	 * {@inheritDoc}
	 */
	public String i18n(String text, Object... params) {
		if (translations == null) {
			readContextTranslation();
		}
		String translation = new MessageFormat(translations.get(
				getDefaultLanguage()).get(text), getDefault()).format(params);
		boolean useLineBreak = false;
		if (translation.contains("\n")) {
			useLineBreak = true;
		}
		if (additionalLocales != null) {
			for (Locale locale : getAdditionalLocales()) {
				if (useLineBreak) {
					translation += "\n";
				} else {
					translation += " ";
				}
				translation += "("
						+ new MessageFormat(translations.get(
								locale.getLanguage()).get(text), locale)
								.format(params) + ")";
			}
		}
		return translation;
	}
}
