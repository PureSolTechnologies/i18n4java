package javax.i18n4j;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

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
public class MultiLanguageTranslations {

	private static final long serialVersionUID = 1L;

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

	public void setTranslations(Hashtable<String, LanguageSet> translations) {
		this.translations = translations;
	}

	public Hashtable<String, LanguageSet> getTranslations() {
		return translations;
	}

	public void setLocations(String source, Vector<SourceLocation> locations) {
		translations.get(source).addLocations(locations);
	}

	public Vector<SourceLocation> getLlocations(String source) {
		return translations.get(source).getLocations();
	}

	public LanguageSet get(String source) {
		if (!translations.containsKey(source)) {
			return null;
		}
		return translations.get(source);
	}

	public String get(String source, String language) {
		LanguageSet set = get(source);
		if (set == null) {
			return source;
		}
		if (!set.containsLanguage(language)) {
			return source;
		}
		return translations.get(source).get(language);
	}

	public void set(String source) {
		if (!translations.containsKey(source)) {
			translations.put(source, new LanguageSet(source));
		}
	}

	public void set(String source, String language, String translation) {
		set(source);
		translations.get(source).set(language, translation);
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

	public void addLocations(String source, Vector<SourceLocation> locations) {
		for (int index = 0; index < locations.size(); index++) {
			addLocation(source, locations.get(index));
		}
	}

	public Vector<SourceLocation> getLocations(String source) {
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

	public Set<String> getAvailableLanguages() {
		Set<String> availableLanguages = new HashSet<String>();
		Set<String> sources = translations.keySet();
		for (String source : sources) {
			Set<String> languages = getAvailableLanguages(source);
			for (String language : languages) {
				if (!availableLanguages.contains(language)) {
					availableLanguages.add(language);
				}
			}
		}
		return availableLanguages;
	}

	public Set<String> getAvailableLanguages(String source) {
		return translations.get(source).getAvailableLanguages();
	}

	public boolean hasTranslations() {
		return (translations.size() > 0);
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
		Vector<SourceLocation> locations = translations.get(source)
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
		Set<String> languages = getAvailableLanguages(source);
		for (String language : languages) {
			System.out.println("\t" + language + "="
					+ translations.get(source).get(language));
		}
	}
}
