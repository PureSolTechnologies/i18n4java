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

package javax.i18n4java.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class stores all translations of a single piece of text and all
 * additional information used for translation and statistics. It's collected
 * for example where the original string was found (a list of classes are
 * stored) and how often it was found in total. The original string is not
 * stored, because it's stored outside for example in TranslationsHash.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
@XmlRootElement(name = "translation")
@XmlAccessorType(XmlAccessType.FIELD)
public class LanguageSet implements Cloneable {

	private String source = "";
	private Map<String, String> translated = new Hashtable<String, String>();
	private List<SourceLocation> locations = new ArrayList<SourceLocation>();

	public LanguageSet() {
	}

	public LanguageSet(String source) {
		this();
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void set(Locale locale, String translation) {
		translated.put(locale2String(locale), translation);
	}

	public String get(Locale locale) {
		return translated.get(locale2String(locale));
	}

	public void addLocations(List<SourceLocation> locations) {
		this.locations.addAll(locations);
	}

	public void addLocation(SourceLocation location) {
		if (!locations.contains(location)) {
			locations.add(location);
		}
	}

	public List<SourceLocation> getLocations() {
		return locations;
	}

	public void clearLocations() {
		locations.clear();
	}

	public void add(LanguageSet translated) {
		if (!getSource().equals(translated.getSource())) {
			throw new IllegalArgumentException("Source "
					+ translated.getSource() + " can not be set to "
					+ getSource() + "!");
		}
		for (Locale locale : translated.getAvailableLanguages()) {
			set(locale, translated.get(locale));
		}
		addLocations(translated.getLocations());
	}

	public boolean containsLanguage(Locale locale) {
		return translated.containsKey(locale2String(locale));
	}

	public Set<Locale> getAvailableLanguages() {
		Set<Locale> locales = new HashSet<Locale>();
		for (String lang : translated.keySet()) {
			locales.add(string2Locale(lang));
		}
		return locales;
	}

	public Object clone() {
		try {
			LanguageSet languageSet = (LanguageSet) super.clone();
			languageSet.setSource(this.getSource());
			languageSet.translated = new Hashtable<String, String>();
			languageSet.locations = new Vector<SourceLocation>();
			languageSet.add(this);
			return languageSet;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((locations == null) ? 0 : locations.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result
				+ ((translated == null) ? 0 : translated.hashCode());
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
		LanguageSet other = (LanguageSet) obj;
		if (locations == null) {
			if (other.locations != null)
				return false;
		} else if (!locations.equals(other.locations))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (translated == null) {
			if (other.translated != null)
				return false;
		} else if (!translated.equals(other.translated))
			return false;
		return true;
	}

	String locale2String(Locale locale) {
		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();
		String result = language;
		if (!country.isEmpty()) {
			result += "_" + country;
		}
		if (!variant.isEmpty()) {
			result += "_" + variant;
		}
		return result;
	}

	Locale string2Locale(String language) {
		String splits[] = language.split("_");
		if (splits.length == 1) {
			return new Locale(splits[0]);
		} else if (splits.length == 2) {
			return new Locale(splits[0], splits[1]);
		} else if (splits.length == 3) {
			return new Locale(splits[0], splits[1], splits[2]);
		} else {
			throw new RuntimeException(
					"Illegal form of locale string found in '" + language + "'");
		}
	}
}
