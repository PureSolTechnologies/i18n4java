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

import static org.junit.Assert.*;

import java.util.Hashtable;
import java.util.Vector;

import javax.i18n4java.data.LanguageSet;
import javax.i18n4java.data.MultiLanguageTranslations;
import javax.i18n4java.data.SourceLocation;

import org.junit.Test;

public class MultiLanguageTranslationsTest {

	@Test
	public void testDefaultConstructor() {
		MultiLanguageTranslations translations = new MultiLanguageTranslations();
		assertEquals(0, translations.getAvailableLanguages().size());
		assertEquals(0, translations.getSources().size());
		assertFalse(translations.hasTranslations());
	}

	@Test
	public void testFrom() {
		MultiLanguageTranslations translations = MultiLanguageTranslations
				.from("Source", "File.java", 123);
		assertNotNull(translations);
		assertEquals(0, translations.getAvailableLanguages().size());
		assertEquals(1, translations.getSources().size());
		assertTrue(translations.hasTranslations());
		LanguageSet languageSet = translations.get("Source");
		assertNotNull(languageSet);
		assertEquals(0, languageSet.getAvailableLanguages().size());
		Vector<SourceLocation> locations = languageSet.getLocations();
		assertNotNull(locations);
		assertEquals(1, locations.size());
	}

	@Test
	public void testSettersAndGetters() {
		MultiLanguageTranslations translations = new MultiLanguageTranslations();
		translations.set("Source1");
		translations.addLocation("Source1", new SourceLocation("File1.java", 1,
				2));
		translations.set("Source2", "File2.java", 2);
		translations.set("Source3", "de", "Quelle3");
		translations.addLocation("Source3", new SourceLocation("File3.java", 3,
				4));
		Vector<SourceLocation> locations = new Vector<SourceLocation>();
		locations.add(new SourceLocation("File3.java", 3, 4));
		locations.add(new SourceLocation("File3.java", 5, 6));
		locations.add(new SourceLocation("File3_1.java", 1, 2));
		translations.addLocations("Source3", locations);

		assertTrue(translations.getTranslations().size() > 0);
		assertTrue(translations.hasTranslations());

		assertTrue(translations.containsSource("Source1"));
		assertEquals(1, translations.getAvailableLanguages().size());
		assertEquals(3, translations.getSources().size());
		assertEquals(0, translations.getAvailableLanguages("Source1").size());
		assertEquals(1, translations.getLocations("Source1").size());
		LanguageSet languageSet = translations.get("Source1");
		assertNotNull(languageSet);
		assertEquals(0, languageSet.getAvailableLanguages().size());
		locations = languageSet.getLocations();
		assertNotNull(locations);
		assertEquals(1, locations.size());

		assertTrue(translations.containsSource("Source2"));
		assertEquals(0, translations.getAvailableLanguages("Source2").size());
		assertEquals(1, translations.getLocations("Source2").size());
		languageSet = translations.get("Source2");
		assertNotNull(languageSet);
		assertEquals(0, languageSet.getAvailableLanguages().size());
		locations = languageSet.getLocations();
		assertNotNull(locations);
		assertEquals(1, locations.size());

		assertTrue(translations.containsSource("Source3"));
		assertEquals(1, translations.getAvailableLanguages("Source3").size());
		assertEquals(3, translations.getLocations("Source3").size());
		languageSet = translations.get("Source3");
		assertNotNull(languageSet);
		assertEquals(1, languageSet.getAvailableLanguages().size());
		locations = languageSet.getLocations();
		assertNotNull(locations);
		assertEquals(3, locations.size());

		translations.removeLocations();
		assertEquals(0, translations.getLocations("Source1").size());
		assertEquals(0, translations.getLocations("Source2").size());
		assertEquals(0, translations.getLocations("Source3").size());

		Hashtable<String, LanguageSet> translation = new Hashtable<String, LanguageSet>();
		translation.put("Test", new LanguageSet());
		translation.get("Test").set("de", "Test");
		translations.setTranslations(translation);
		assertNotSame(translation, translations.getTranslations());
		assertEquals(translation, translations.getTranslations());
	}

	@Test
	public void testEquals() {
		MultiLanguageTranslations translations = new MultiLanguageTranslations();
		assertTrue(translations.equals(translations));
		assertFalse(translations.equals(null));
	}

	@Test
	public void testClone() {
		MultiLanguageTranslations origin = MultiLanguageTranslations.from(
				"Source1", "File.java", 123);
		MultiLanguageTranslations cloned = (MultiLanguageTranslations) origin
				.clone();
		assertNotSame(origin, cloned);
		assertEquals(origin, cloned);
	}

	@Test
	public void testSetAndGetTranslation() {
		MultiLanguageTranslations hash = new MultiLanguageTranslations();
		assertEquals("English", hash.get("English", "de"));

		hash.set("English", "de", "Deutsch");
		hash.set("English", "vi", "Tieng Viet");

		assertEquals("English", hash.get("English", ""));
		assertEquals("Deutsch", hash.get("English", "de"));
		assertEquals("Tieng Viet", hash.get("English", "vi"));
	}
}
