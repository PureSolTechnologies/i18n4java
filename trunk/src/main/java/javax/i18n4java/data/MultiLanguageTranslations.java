/****************************************************************************
 *
 *   MultiLanguageTranslations.java
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
 
package javax.i18n4java.data;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This hash contains the translations for I18N. Its purpose is to hold
 * translations for multiple translations to support the multi language support
 * for training sessions.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
@XmlRootElement(name = "internationalization")
@XmlAccessorType(XmlAccessType.FIELD)
public class MultiLanguageTranslations implements Cloneable {

	private Hashtable<String, LanguageSet> translations = null;

	/**
	 * Creates an initial translations hash with starting data provided.
	 * 
	 * @param source
	 * @param file
	 * @param line
	 * @return
	 */
	static public MultiLanguageTranslations from(String source, String file,
			int line) {
		MultiLanguageTranslations hash = new MultiLanguageTranslations();
		hash.set(source, file, line);
		return hash;
	}

	public MultiLanguageTranslations() {
		translations = new Hashtable<String, LanguageSet>();
	}

	@SuppressWarnings("unchecked")
	public void setTranslations(Hashtable<String, LanguageSet> translations) {
		if (translations == null) {
			throw new IllegalArgumentException("translations must no be null!");
		}
		this.translations = (Hashtable<String, LanguageSet>) translations
				.clone();
	}

	@SuppressWarnings("unchecked")
	public Hashtable<String, LanguageSet> getTranslations() {
		return (Hashtable<String, LanguageSet>) translations.clone();
	}

	public LanguageSet getTranslations(String source) {
		LanguageSet languageSet = translations.get(source);
		if (languageSet == null) {
			return null;
		}
		return (LanguageSet) languageSet.clone();
	}

	public LanguageSet get(String source) {
		if (!translations.containsKey(source)) {
			return null;
		}
		return translations.get(source);
	}

	public String get(String source, Locale locale) {
		LanguageSet set = get(source);
		if (set == null) {
			return source;
		}
		if (!set.containsLanguage(locale)) {
			return source;
		}
		return translations.get(source).get(locale);
	}

	public void set(String source) {
		if (!translations.containsKey(source)) {
			translations.put(source, new LanguageSet(source));
		}
	}

	public void set(String source, Locale locale, String translation) {
		set(source);
		translations.get(source).set(locale, translation);
	}

	public void set(String source, LanguageSet translation) {
		translations.put(source, translation);
	}

	public void set(String source, String file, int line) {
		set(source);
		addLocation(source, new SourceLocation(file, line));
	}

	public void add(MultiLanguageTranslations translations) {
		if (translations == null) {
			return;
		}
		Set<String> sources = translations.getSources();
		for (String source : sources) {
			add(source, translations.get(source));
		}
	}

	public void add(String source, LanguageSet translation) {
		if (!translations.containsKey(source)) {
			translations.put(source, translation);
		} else {
			translations.get(source).add(translation);
		}
	}

	public void addLocation(String source, SourceLocation location) {
		translations.get(source).addLocation(location);
	}

	public void addLocations(String source, List<SourceLocation> locations) {
		for (int index = 0; index < locations.size(); index++) {
			addLocation(source, locations.get(index));
		}
	}

	public List<SourceLocation> getLocations(String source) {
		return translations.get(source).getLocations();
	}

	public void removeLocations() {
		for (String source : translations.keySet()) {
			translations.get(source).clearLocations();
		}
	}

	public boolean containsSource(String original) {
		return translations.containsKey(original);
	}

	public Set<String> getSources() {
		return translations.keySet();
	}

	public Set<Locale> getAvailableLanguages() {
		Set<Locale> availableLanguages = new HashSet<Locale>();
		Set<String> sources = translations.keySet();
		for (String source : sources) {
			for (Locale language : getAvailableLanguages(source)) {
				if (!availableLanguages.contains(language)) {
					availableLanguages.add(language);
				}
			}
		}
		return availableLanguages;
	}

	public Set<Locale> getAvailableLanguages(String source) {
		return translations.get(source).getAvailableLanguages();
	}

	public boolean hasTranslations() {
		return (translations.size() > 0);
	}

	public void removeWithoutLocation() {
		ArrayList<String> toRemove = new ArrayList<String>();
		for (String source : translations.keySet()) {
			LanguageSet languageSet = translations.get(source);
			if (languageSet.getLocations().size() == 0) {
				toRemove.add(source);
			}
		}
		for (String source : toRemove) {
			translations.remove(source);
		}
	}

	public void print() {
		Set<String> sources = getSources();
		for (String source : sources) {
			System.out.println("Source: " + source);
			printLocations(source);
			printTranslations(source);
		}
	}

	protected void printLocations(String source) {
		System.out.println("\tlocations:");
		List<SourceLocation> locations = translations.get(source)
				.getLocations();
		if (locations == null) {
			return;
		}
		for (int index = 0; index < locations.size(); index++) {
			SourceLocation location = locations.get(index);
			if (location == null) {
				continue;
			}
			System.out.println("\t\t" + location.toString());
		}
	}

	protected void printTranslations(String source) {
		for (Locale language : getAvailableLanguages(source)) {
			System.out.println("\t" + language + "="
					+ translations.get(source).get(language));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + translations.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultiLanguageTranslations other = (MultiLanguageTranslations) obj;
		if (!translations.equals(other.translations))
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		try {
			MultiLanguageTranslations cloned = (MultiLanguageTranslations) super
					.clone();
			cloned.translations = (Hashtable<String, LanguageSet>) this.translations
					.clone();
			return cloned;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public void removeLineBreaks() {
		Enumeration<String> sources = translations.keys();
		while (sources.hasMoreElements()) {
			String source = sources.nextElement();
			LanguageSet languageSet = translations.get(source);
			translations.remove(source);
			source = languageSet.getSource();
			source = source.replaceAll("\\n", "\\\\n");
			translations.put(source, languageSet);
			languageSet.setSource(source);
			for (Locale language : languageSet.getAvailableLanguages()) {
				String translation = languageSet.get(language);
				if (translation != null) {
					translation = translation.replaceAll("\\n", "\\\\n");
					languageSet.set(language, translation);
				}
			}
		}
	}

	public void addLineBreaks() {
		Enumeration<String> sources = translations.keys();
		while (sources.hasMoreElements()) {
			String source = sources.nextElement();
			LanguageSet languageSet = translations.get(source);
			translations.remove(source);
			source = languageSet.getSource();
			source = source.replaceAll("\\\\n", "\n");
			translations.put(source, languageSet);
			languageSet.setSource(source);
			for (Locale language : languageSet.getAvailableLanguages()) {
				String translation = languageSet.get(language);
				if (translation != null) {
					translation = translation.replaceAll("\\\\n", "\n");
					languageSet.set(language, translation);
				}
			}
		}
	}

	public boolean isTranslationFinished() {
		Set<Locale> languages = getAvailableLanguages();
		if (languages.size() == 0) {
			return false;
		}
		for (Locale language : languages) {
			if (!isTranslationFinished(language)) {
				return false;
			}
		}
		return true;
	}

	public boolean isTranslationFinished(Locale language) {
		Set<String> sources = getSources();
		for (String source : sources) {
			LanguageSet languageSet = getTranslations(source);
			if (!languageSet.containsLanguage(language)) {
				return false;
			}
		}
		return true;
	}
}
