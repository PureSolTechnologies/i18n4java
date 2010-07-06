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

import java.util.Hashtable;
import java.util.Vector;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MultiLanguageTranslationsTest extends TestCase {

	@Test
	public void testDefaultConstructor() {
		MultiLanguageTranslations translations = new MultiLanguageTranslations();
		Assert.assertEquals(0, translations.getAvailableLanguages().size());
		Assert.assertEquals(0, translations.getSources().size());
		Assert.assertFalse(translations.hasTranslations());
	}

	@Test
	public void testFrom() {
		MultiLanguageTranslations translations = MultiLanguageTranslations
				.from("Source", "File.java", 123);
		Assert.assertNotNull(translations);
		Assert.assertEquals(0, translations.getAvailableLanguages().size());
		Assert.assertEquals(1, translations.getSources().size());
		Assert.assertTrue(translations.hasTranslations());
		LanguageSet languageSet = translations.get("Source");
		Assert.assertNotNull(languageSet);
		Assert.assertEquals(0, languageSet.getAvailableLanguages().size());
		Vector<SourceLocation> locations = languageSet.getLocations();
		Assert.assertNotNull(locations);
		Assert.assertEquals(1, locations.size());
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

		Assert.assertTrue(translations.getTranslations().size() > 0);
		Assert.assertTrue(translations.hasTranslations());

		Assert.assertTrue(translations.containsSource("Source1"));
		Assert.assertEquals(1, translations.getAvailableLanguages().size());
		Assert.assertEquals(3, translations.getSources().size());
		Assert.assertEquals(0, translations.getAvailableLanguages("Source1")
				.size());
		Assert.assertEquals(1, translations.getLocations("Source1").size());
		LanguageSet languageSet = translations.get("Source1");
		Assert.assertNotNull(languageSet);
		Assert.assertEquals(0, languageSet.getAvailableLanguages().size());
		locations = languageSet.getLocations();
		Assert.assertNotNull(locations);
		Assert.assertEquals(1, locations.size());

		Assert.assertTrue(translations.containsSource("Source2"));
		Assert.assertEquals(0, translations.getAvailableLanguages("Source2")
				.size());
		Assert.assertEquals(1, translations.getLocations("Source2").size());
		languageSet = translations.get("Source2");
		Assert.assertNotNull(languageSet);
		Assert.assertEquals(0, languageSet.getAvailableLanguages().size());
		locations = languageSet.getLocations();
		Assert.assertNotNull(locations);
		Assert.assertEquals(1, locations.size());

		Assert.assertTrue(translations.containsSource("Source3"));
		Assert.assertEquals(1, translations.getAvailableLanguages("Source3")
				.size());
		Assert.assertEquals(3, translations.getLocations("Source3").size());
		languageSet = translations.get("Source3");
		Assert.assertNotNull(languageSet);
		Assert.assertEquals(1, languageSet.getAvailableLanguages().size());
		locations = languageSet.getLocations();
		Assert.assertNotNull(locations);
		Assert.assertEquals(3, locations.size());

		translations.removeLocations();
		Assert.assertEquals(0, translations.getLocations("Source1").size());
		Assert.assertEquals(0, translations.getLocations("Source2").size());
		Assert.assertEquals(0, translations.getLocations("Source3").size());

		Hashtable<String, LanguageSet> translation = new Hashtable<String, LanguageSet>();
		translation.put("Test", new LanguageSet());
		translation.get("Test").set("de", "Test");
		translations.setTranslations(translation);
		Assert.assertNotSame(translation, translations.getTranslations());
		Assert.assertEquals(translation, translations.getTranslations());
	}

	@Test
	public void testEquals() {
		MultiLanguageTranslations translations = new MultiLanguageTranslations();
		Assert.assertTrue(translations.equals(translations));
		Assert.assertFalse(translations.equals(null));
	}

	@Test
	public void testClone() {
		MultiLanguageTranslations origin = MultiLanguageTranslations.from(
				"Source1", "File.java", 123);
		MultiLanguageTranslations cloned = (MultiLanguageTranslations) origin
				.clone();
		Assert.assertNotSame(origin, cloned);
		Assert.assertEquals(origin, cloned);
	}
}
