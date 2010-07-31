/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *
 ***************************************************************************/

package javax.i18n4java;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.i18n4java.data.SingleLanguageTranslations;
import javax.i18n4java.data.TRFile;

import org.apache.log4j.Logger;

/**
 * This small object was inspired by QT's approach to internationalization which
 * is much more flexible and much easier to use. This object was to be created
 * because the String object is final and a tt() function could not be
 * implemented in that way.
 * 
 * This class is the central translator for all translations. It's instanciated
 * at best as
 * 
 * <tt>private static final Translator translator = 
 * Translator.getTranslator(Class)</tt>
 * 
 * This class is thread safe.
 * 
 * @author Rick-Rainer Ludwig
 */
public class Translator implements Serializable {

	private static final long serialVersionUID = -2918229155516838530L;

	private static final Logger logger = Logger.getLogger(Translator.class);

	/**
	 * This variable contains the current default language for the translator.
	 */
	private static Locale defaultLocale;
	static {
		defaultLocale = Locale.getDefault();
		logger.info("Setting default locale to '" + defaultLocale.toString()
				+ "'");
	}

	private static final List<Locale> additionalLocales = new ArrayList<Locale>();
	private static final Object ADDITIONAL_LOCALES_LOCK = new Object();

	/**
	 * This variable keeps the references to the system wide unique context
	 * sensitive instances.
	 */
	private static final ConcurrentMap<String, Translator> instances = new ConcurrentHashMap<String, Translator>();
	private static final Object INSTANCES_LOCK = new Object();

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
		synchronized (INSTANCES_LOCK) {
			String context = clazz.getName();
			Translator translator = (Translator) instances.get(context);
			if (translator == null) {
				logger.debug("Creating instance for class '" + context + "'");
				translator = new Translator(clazz);
				instances.putIfAbsent(context, translator);
			}
			return translator;
		}
	}

	/**
	 * This method returns the static instance to Translator which is unique to
	 * the whole system. This is the static method to build the Singleton
	 * pattern.
	 * 
	 * @return A reference to the static held Translator object is returned.
	 */
	public static Translator getTranslator(Class<?> clazz) {
		Translator translator = (Translator) instances.get(clazz.getName());
		if (translator == null) {
			translator = createInstance(clazz);
		}
		return translator;
	}

	/**
	 * This method sets the current defaut locale for all translations to
	 * follow.
	 * 
	 * @param locale
	 */
	public static synchronized void setDefault(Locale locale) {
		logger.info("Set default locale to '" + locale.toString() + "'");
		defaultLocale = locale;
		resetAllInstances();
	}

	public static Locale getDefault() {
		return defaultLocale;
	}

	public static String getDefaultLanguage() {
		return getDefault().getLanguage();
	}

	public static String getDefaultCountry() {
		return getDefault().getCountry();
	}

	public static void setSingleLanguageMode() {
		synchronized (ADDITIONAL_LOCALES_LOCK) {
			logger.info("Set to single language mode");
			additionalLocales.clear();
			resetAllInstances();
		}
	}

	public static void addAdditionalLocale(Locale locale) {
		synchronized (ADDITIONAL_LOCALES_LOCK) {
			logger.info("Set additional locale " + locale.toString());
			if (!additionalLocales.contains(locale)) {
				additionalLocales.add(locale);
			}
			resetAllInstances();
		}
	}

	public static List<Locale> getAdditionalLocales() {
		synchronized (ADDITIONAL_LOCALES_LOCK) {
			return additionalLocales;
		}
	}

	private static void resetAllInstances() {
		synchronized (INSTANCES_LOCK) {
			logger.info("Reset all instances");
			for (String context : instances.keySet()) {
				instances.get(context).reset();
			}
		}
	}

	/**
	 * This is the Hashtable for the context translations. Everything is kept in
	 * there. The context is the name of the class including the package name.
	 */
	private final ConcurrentMap<String, SingleLanguageTranslations> translations = new ConcurrentHashMap<String, SingleLanguageTranslations>();;
	private final Object TRANSLATIONS_LOCK = new Object();

	/**
	 * This variable keeps the name of the class which is used as a context.
	 */
	private final String context;

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
		translations.clear();
	}

	synchronized void setTranslation(String source, String language,
			String translation) {
		if (!translations.containsKey(language)) {
			translations
					.putIfAbsent(language, new SingleLanguageTranslations());
		}
		translations.get(language).add(source, translation);
	}

	private void readContextTranslation() {
		readContextTranslation(getDefaultLanguage());
		for (Locale addLocale : getAdditionalLocales()) {
			readContextTranslation(addLocale.getLanguage());
		}
	}

	private void readContextTranslation(String language) {
		logger.debug("read context translation for context '" + context
				+ "' and language '" + language + "'");
		if (language.equals("en")) {
			translations.putIfAbsent("en", new SingleLanguageTranslations());
			return;
		}
		String resource = TRFile.getResource(context, language);
		logger.info("Read context language file '" + resource + "'");
		InputStream is = getClass().getResourceAsStream(resource);
		if (is == null) {
			logger.warn("No context translation file found for '" + resource
					+ "'");
			return;
		}
		translations.putIfAbsent(language, readFromStream(is));
	}

	private SingleLanguageTranslations readFromStream(InputStream is) {
		try {
			SingleLanguageTranslations translations = TRFile.read(is);
			return translations;
		} catch (IOException e) {
			return new SingleLanguageTranslations();
		}
	}

	/**
	 * This method translates the given string. The original string and the
	 * translation can be a MessageFormat string, but the parameters are not put
	 * into the string. This method is just a hash map lookup.
	 * 
	 * If there is no translated string available, the original string is
	 * returned.
	 * 
	 * @param text
	 *            is the text to be translated.
	 * @param language
	 *            are the parameters for the MessageFormat.
	 * @return The translated and localized string is returned.
	 */
	String translate(String text, String language) {
		synchronized (TRANSLATIONS_LOCK) {
			if (translations.size() == 0) {
				readContextTranslation();
			}
		}
		SingleLanguageTranslations singleLanguageTranslations = translations
				.get(language);
		if (singleLanguageTranslations == null) {
			return text;
		}
		String translation = singleLanguageTranslations.get(text);
		if (translation == null) {
			return text;
		}
		return translation;
	}

	/**
	 * This method translates the given string into the localized form. The
	 * original string and the translation can be a MessageFormat string. Used
	 * is the current set locale and language.
	 * 
	 * @param text
	 *            is the text to be translated.
	 * @param params
	 *            are the parameters for the MessageFormat.
	 * @return The translated and localized string is returned.
	 */
	public String i18n(String text, Object... params) {
		StringBuffer translation = new StringBuffer(
				new MessageFormat(translate(text, getDefaultLanguage()),
						getDefault()).format(params));
		boolean useLineBreak = false;
		if (translation.toString().contains("\n")) {
			useLineBreak = true;
		}
		for (Locale locale : getAdditionalLocales()) {
			if (useLineBreak) {
				translation.append("\n");
			} else {
				translation.append(" ");
			}
			translation
					.append("(")
					.append(new MessageFormat(translate(text,
							locale.getLanguage()), locale).format(params))
					.append(")");
		}
		return translation.toString();
	}

}
