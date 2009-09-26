/***************************************************************************
 *
 *   LanguageSet.java
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

import java.util.Hashtable;
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
@XmlRootElement(name = "translation", namespace = "http://overstock.com/example")
@XmlAccessorType(XmlAccessType.FIELD)
public class LanguageSet {

	private String source = "";
	private Hashtable<String, String> translated = null;
	private Vector<SourceLocation> locations = null;

	public LanguageSet() {
		translated = new Hashtable<String, String>();
		locations = new Vector<SourceLocation>();
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

	public void set(String language, String translation) {
		translated.put(language, translation);
	}

	public String get(String language) {
		return translated.get(language);
	}

	public void addLocations(Vector<SourceLocation> locations) {
		this.locations.addAll(locations);
	}

	public void addLocation(SourceLocation location) {
		if (!locations.contains(location)) {
			locations.add(location);
		}
	}

	public Vector<SourceLocation> getLocations() {
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
		Set<String> languages = translated.getAvailableLanguages();
		for (String language : languages) {
			set(language, translated.get(language));
		}
		addLocations(translated.getLocations());
	}

	public boolean containsLanguage(String language) {
		return translated.containsKey(language);
	}

	public Set<String> getAvailableLanguages() {
		return translated.keySet();
	}
}
