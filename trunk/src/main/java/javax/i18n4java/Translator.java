/****************************************************************************
 *
 *   Translator.java
 *   -------------------
 *   copyright            : (c) 2009-2011 by PureSol-Technologies
 *   author               : Rick-Rainer Ludwig
 *   email                : ludwig@puresol-technologies.com
 *
 ****************************************************************************/

/****************************************************************************
 *
 * Copyright 2009-2011 PureSol-Technologies
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
 ****************************************************************************/
 
package javax.i18n4java;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.i18n4java.data.SingleLanguageTranslations;
import javax.i18n4java.data.TRFile;
import javax.i18n4java.utils.I18N4Java;
import javax.swing.JComponent;

import org.apache.log4j.Logger;

/**
 * This small object was inspired by QT's approach to internationalization which
 * is much more flexible and much easier to use. This object was to be created
 * because the String object is final and a tt() function could not be
 * implemented in that way.
 * 
 * This class is the central translator for all translations. It's instantiated
 * as best as
 * 
 * <tt>private static final Translator translator = 
 * Translator.getTranslator(Class)</tt>.
 * 
 * <b>This class is thread safe.</b>
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

	/**
	 * This set contains the locales which are to be used as additional
	 * translations.
	 */
	private static final Set<Locale> additionalLocales = new LinkedHashSet<Locale>();

	/**
	 * This variable keeps the references to the system wide unique context
	 * sensitive instances.
	 */
	private static final ConcurrentMap<String, Translator> instances = new ConcurrentHashMap<String, Translator>();

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
		synchronized (instances) {
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
		Locale.setDefault(locale);
		JComponent.setDefaultLocale(locale);
		resetAllInstances();
	}

	/**
	 * This method returns the currently set default locale which is used for
	 * primary translation.
	 * 
	 * @return
	 */
	public static Locale getDefault() {
		return defaultLocale;
	}

	/**
	 * This method returns the currently set language.
	 * 
	 * @return
	 */
	public static String getDefaultLanguage() {
		return getDefault().getLanguage();
	}

	public static String getDefaultCountry() {
		return getDefault().getCountry();
	}

	public static void setSingleLanguageMode() {
		synchronized (additionalLocales) {
			logger.info("Set to single language mode");
			additionalLocales.clear();
			resetAllInstances();
		}
	}

	public static void addAdditionalLocale(Locale locale) {
		synchronized (additionalLocales) {
			logger.info("Set additional locale " + locale.toString());
			if (!additionalLocales.contains(locale)) {
				additionalLocales.add(locale);
			}
			resetAllInstances();
		}
	}

	public static Set<Locale> getAdditionalLocales() {
		synchronized (additionalLocales) {
			return additionalLocales;
		}
	}

	/**
	 * This method resets all translation maps in all instances. For any new
	 * translation, the translation file is re-read.
	 * 
	 * This method is called in cases of changed locales.
	 */
	private static void resetAllInstances() {
		synchronized (instances) {
			logger.info("Reset all instances");
			for (String context : instances.keySet()) {
				instances.get(context).reset();
			}
		}
	}

	private final List<WeakReference<LanguageChangeListener>> listeners = new ArrayList<WeakReference<LanguageChangeListener>>();

	/**
	 * This is the hash table for the context translations. Everything is kept
	 * in there. The context is the name of the class including the package
	 * name.
	 */
	private final ConcurrentMap<String, SingleLanguageTranslations> translations = new ConcurrentHashMap<String, SingleLanguageTranslations>();

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

	/**
	 * This method clears the translations map. The next request for translation
	 * will result in a re-read of language translations files.
	 */
	private void reset() {
		logger.info("reset '" + context + "'");
		translations.clear();
		translationChanged();
	}

	/**
	 * This method adds a new translation to the translations map.
	 * 
	 * @param source
	 * @param language
	 * @param translation
	 */
	synchronized void setTranslation(String source, String language,
			String translation) {
		if (!translations.containsKey(language)) {
			translations
					.putIfAbsent(language, new SingleLanguageTranslations());
		}
		translations.get(language).add(source, translation);
	}

	/**
	 * This method reads all context translations for all set locales.
	 */
	private void readContextTranslation() {
		readContextTranslation(getDefault());
		for (Locale addLocale : getAdditionalLocales()) {
			readContextTranslation(addLocale);
		}
	}

	/**
	 * This method reads the context translation for the language.
	 * 
	 * @param locale
	 *            is the language to be read.
	 */
	private void readContextTranslation(Locale locale) {
		logger.debug("read context translation for context '" + context
				+ "' and language '" + locale + "'");
		if (locale.equals(I18N4Java.getImplementationLocale())) {
			translations.putIfAbsent(locale.toString(),
					new SingleLanguageTranslations());
			return;
		}
		String resource = TRFile.getResourceName(context, locale);
		readContextTranslationFromResource(locale.toString(), resource);
	}

	/**
	 * This method reads the actual translations from the translation file.
	 * 
	 * @param language
	 *            is the language for which the translations are to be read.
	 * @param resource
	 *            is the resource name, where the translations for the language
	 *            can be found.
	 */
	private void readContextTranslationFromResource(String language,
			String resource) {
		try {
			logger.info("Read context language file '" + resource + "'");
			InputStream is = getClass().getResourceAsStream(resource);
			if (is == null) {
				throw new IOException("No context translation file found for '"
						+ resource + "'");
			}
			try {
				translations.putIfAbsent(language, TRFile.read(is));
			} finally {
				is.close();
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
			translations
					.putIfAbsent(language, new SingleLanguageTranslations());
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
		synchronized (translations) {
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

	public void addLanguageChangeListener(LanguageChangeListener listener) {
		boolean found = false;
		for (Iterator<WeakReference<LanguageChangeListener>> iterator = listeners
				.iterator(); iterator.hasNext();) {
			WeakReference<LanguageChangeListener> ref = iterator.next();
			if (ref.get() == null) {
				iterator.remove();
			} else if (ref.get().equals(listener)) {
				found = true;
			}
		}
		if (!found) {
			listeners.add(new WeakReference<LanguageChangeListener>(listener));
		}
	}

	public void removeLanguageChangeListener(LanguageChangeListener listener) {
		for (Iterator<WeakReference<LanguageChangeListener>> iterator = listeners
				.iterator(); iterator.hasNext();) {
			WeakReference<LanguageChangeListener> ref = iterator.next();
			if (ref.get() == null) {
				iterator.remove();
			} else if (ref.get().equals(listener)) {
				iterator.remove();
			}
		}
	}

	private void translationChanged() {
		for (Iterator<WeakReference<LanguageChangeListener>> iterator = listeners
				.iterator(); iterator.hasNext();) {
			WeakReference<LanguageChangeListener> ref = iterator.next();
			if (ref.get() == null) {
				iterator.remove();
			} else {
				ref.get().translationChanged(this);
			}
		}
	}

	/**
	 * This mehtod is used for testing purposes only.
	 * 
	 * @return
	 */
	List<WeakReference<LanguageChangeListener>> getLanguageChangeListeners() {
		List<WeakReference<LanguageChangeListener>> toBeRemoved = new ArrayList<WeakReference<LanguageChangeListener>>();
		for (WeakReference<LanguageChangeListener> ref : listeners) {
			if (ref.get() == null) {
				toBeRemoved.add(ref);
			}
		}
		for (WeakReference<LanguageChangeListener> ref : toBeRemoved) {
			listeners.remove(ref);
		}
		return listeners;
	}

}
