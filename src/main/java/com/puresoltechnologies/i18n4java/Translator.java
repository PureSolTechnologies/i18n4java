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

package com.puresoltechnologies.i18n4java;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

import com.puresoltechnologies.i18n4java.data.SingleLanguageTranslations;
import com.puresoltechnologies.i18n4java.data.TRFile;
import com.puresoltechnologies.i18n4java.utils.I18N4Java;

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

	/**
	 * This variable contains the current default locale for the translator.
	 */
	private static Locale defaultLocale = Locale.getDefault();

	/**
	 * This set contains the locales which are to be used as additional
	 * translations.
	 */
	private static final Set<Locale> additionalLocales = new LinkedHashSet<Locale>();

	/**
	 * This variable keeps the references to the system wide unique context
	 * sensitive instances.
	 */
	private static final Map<String, Translator> instances = new HashMap<String, Translator>();

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
	private static Translator createInstance(Class<?> clazz) {
		String context = clazz.getName();
		synchronized (instances) {
			Translator translator = instances.get(context);
			if (translator == null) {
				translator = new Translator(clazz);
				instances.put(context, translator);
			}
			return translator;
		}
	}

	/**
	 * This method returns the static instance to Translator which is unique to
	 * the whole system. This is the static method to build the Singleton
	 * pattern.
	 * 
	 * @param clazz
	 *            is the {@link Class} for which the a translator is to be
	 *            returned.
	 * @return A reference to the static held Translator object is returned.
	 */
	public static Translator getTranslator(Class<?> clazz) {
		Translator translator = instances.get(clazz.getName());
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
	 *            is the locale to use be used as default for translation.
	 */
	public static synchronized void setDefault(Locale locale) {
		defaultLocale = locale;
		Locale.setDefault(locale);
		JComponent.setDefaultLocale(locale);
		resetAllInstances();
	}

	/**
	 * This method returns the currently set default locale which is used for
	 * primary translation.
	 * 
	 * @return The set default locale is returned.
	 */
	public static Locale getDefault() {
		return defaultLocale;
	}

	public static void setSingleLanguageMode() {
		synchronized (additionalLocales) {
			additionalLocales.clear();
			resetAllInstances();
		}
	}

	public static void addAdditionalLocale(Locale locale) {
		synchronized (additionalLocales) {
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
			for (String context : instances.keySet()) {
				instances.get(context).reset();
			}
		}
	}

	private final transient List<WeakReference<LanguageChangeListener>> listeners = new ArrayList<WeakReference<LanguageChangeListener>>();

	/**
	 * This is the hash table for the context translations. Everything is kept
	 * in there. The context is the name of the class including the package
	 * name.
	 */
	private final Map<Locale, SingleLanguageTranslations> translations = new HashMap<Locale, SingleLanguageTranslations>();

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

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		listeners.clear();
	}

	/**
	 * This method clears the translations map. The next request for translation
	 * will result in a re-read of language translations files.
	 */
	private void reset() {
		translations.clear();
		translationChanged();
	}

	/**
	 * This method adds a new translation to the translations map.
	 * 
	 * @param source
	 * @param locale
	 * @param translation
	 */
	synchronized void setTranslation(String source, Locale locale,
			String translation) {
		setDefaultTranslations(locale);
		translations.get(locale).add(source, translation);
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
	 * This method reads the context translation for the locale.
	 * 
	 * @param locale
	 *            is the locale to be read.
	 */
	private void readContextTranslation(Locale locale) {
		if (locale.equals(I18N4Java.getImplementationLocale())) {
			setDefaultTranslations(locale);
			return;
		}
		String resource = TRFile.getResourceName(context, locale);
		readContextTranslationFromResource(locale, resource);
	}

	/**
	 * This method reads the actual translations from the translation file.
	 * 
	 * @param locale
	 *            is the locale for which the translations are to be read.
	 * @param resource
	 *            is the resource name, where the translations for the locale
	 *            can be found.
	 */
	private void readContextTranslationFromResource(Locale locale,
			String resource) {
		try {
			InputStream is = getClass().getResourceAsStream(resource);
			if (is == null) {
				throw new IOException("No context translation file found for '"
						+ resource + "'");
			}
			try {
				synchronized (translations) {
					if (translations.get(locale) == null) {
						translations.put(locale, TRFile.read(is));
					}
				}
			} finally {
				is.close();
			}
		} catch (IOException e) {
			setDefaultTranslations(locale);
		}
	}

	private void setDefaultTranslations(Locale locale) {
		synchronized (translations) {
			if (!translations.containsKey(locale)) {
				translations.put(locale, new SingleLanguageTranslations());
			}
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
	 * @param locale
	 *            are the parameters for the MessageFormat.
	 * @return The translated and localized string is returned.
	 */
	String translate(String text, Locale locale) {
		synchronized (translations) {
			if (translations.size() == 0) {
				readContextTranslation();
			}
		}
		SingleLanguageTranslations singleLanguageTranslations = translations
				.get(locale);
		if (singleLanguageTranslations == null) {
			return text;
		}
		return singleLanguageTranslations.get(text);
	}

	/**
	 * This method translates the given string into the localized form. The
	 * original string and the translation can be a MessageFormat string. Used
	 * is the current set locale.
	 * 
	 * @param text
	 *            is the text to be translated.
	 * @param params
	 *            are the parameters for the MessageFormat.
	 * @return The translated and localized string is returned.
	 */
	public String i18n(String text, Object... params) {
		StringBuilder translation = new StringBuilder(new MessageFormat(
				translate(text, getDefault()), getDefault()).format(params));
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
					.append(new MessageFormat(translate(text, locale), locale)
							.format(params)).append(")");
		}
		return translation.toString();
	}

	/**
	 * This method translates the given string into the localized form. The
	 * original string and the translation can be a MessageFormat string. Used
	 * is the current set locale.
	 * 
	 * @param text
	 *            is the text to be translated.
	 * @param locale
	 *            is the locale to be used for translation.
	 * @param params
	 *            are the parameters for the MessageFormat.
	 * @return The translated and localized string is returned.
	 */
	public String i18n(String text, Locale locale, Object... params) {
		return new MessageFormat(translate(text, locale), locale)
				.format(params);
	}

	public void addLanguageChangeListener(LanguageChangeListener listener) {
		boolean found = false;
		for (Iterator<WeakReference<LanguageChangeListener>> iterator = listeners
				.iterator(); iterator.hasNext();) {
			WeakReference<LanguageChangeListener> ref = iterator.next();
			LanguageChangeListener referencedListener = ref.get();
			if (referencedListener == null) {
				iterator.remove();
			} else if (referencedListener.equals(listener)) {
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
			LanguageChangeListener referencedListener = ref.get();
			if (referencedListener == null) {
				iterator.remove();
			} else if (referencedListener.equals(listener)) {
				iterator.remove();
			}
		}
	}

	private void translationChanged() {
		for (Iterator<WeakReference<LanguageChangeListener>> iterator = listeners
				.iterator(); iterator.hasNext();) {
			WeakReference<LanguageChangeListener> ref = iterator.next();
			LanguageChangeListener referencesListener = ref.get();
			if (referencesListener == null) {
				iterator.remove();
			} else {
				referencesListener.translationChanged(this);
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
