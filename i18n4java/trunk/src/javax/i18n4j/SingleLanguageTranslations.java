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

package javax.i18n4j;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "translations")
@XmlAccessorType(XmlAccessType.FIELD)
public class SingleLanguageTranslations implements Cloneable {

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

	public String toString() {
		StringBuffer result = new StringBuffer();
		for (String source : translations.keySet()) {
			result.append(source).append(" --> ").append(
					translations.get(source)).append("\n");
		}
		return result.toString();
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
		SingleLanguageTranslations other = (SingleLanguageTranslations) obj;
		if (!translations.equals(other.translations))
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		try {
			SingleLanguageTranslations cloned = (SingleLanguageTranslations) super
					.clone();
			cloned.translations = (Hashtable<String, String>) this.translations
					.clone();
			return cloned;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void removeLineBreaks() {
		Enumeration<String> sources = translations.keys();
		while (sources.hasMoreElements()) {
			String source = sources.nextElement();
			String translation = translations.get(source);
			translations.remove(source);
			source = source.replaceAll("\\n", "\\\\n");
			translation = translation.replaceAll("\\n", "\\\\n");
			translations.put(source, translation);
		}
	}

	public void addLineBreaks() {
		Enumeration<String> sources = translations.keys();
		while (sources.hasMoreElements()) {
			String source = sources.nextElement();
			String translation = translations.get(source);
			translations.remove(source);
			source = source.replaceAll("\\\\n", "\n");
			translation = translation.replaceAll("\\\\n", "\n");
			translations.put(source, translation);
		}
	}
}
