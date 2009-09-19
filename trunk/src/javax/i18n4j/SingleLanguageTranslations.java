package javax.i18n4j;

import java.util.Hashtable;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "translations")
@XmlAccessorType(XmlAccessType.FIELD)
public class SingleLanguageTranslations {

	private Hashtable<String, String> translations = new Hashtable<String, String>();

	public SingleLanguageTranslations() {
	}

	public void set(String source, String translation) {
		translations.put(source, translation);
	}

	public String get(String source) {
		if (translations.containsKey(source)) {
			return translations.get(source);
		}
		return source;
	}

	public void print() {
		Set<String> sources = translations.keySet();
		for (String source : sources) {
			System.out.println(source);
			System.out.println("--> " + translations.get(source));
		}
	}
}
