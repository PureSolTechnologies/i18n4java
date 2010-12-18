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

import javax.i18n4java.data.SingleLanguageTranslations;

import org.junit.Test;

public class SingleLanguageTranslationsTest {

	@Test
	public void testDefaultConstructor() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		assertEquals("Nothing in", translations.get("Nothing in"));
	}

	@Test
	public void testSetterAndGetter() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		translations.add("Source", "Quelle");
		translations.add("Translation", "Uebersetzung");
		assertEquals("Quelle", translations.get("Source"));
		assertEquals("Uebersetzung", translations.get("Translation"));
	}

	@Test
	public void testToString() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		translations.add("Source", "Quelle");
		translations.add("Translation", "Uebersetzung");
		System.out.println(translations.toString());
		assertEquals("Translation --> Uebersetzung\nSource --> Quelle\n",
				translations.toString());
	}

	@Test
	public void testHashCode() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		assertTrue(translations.hashCode() > 0);
	}

	@Test
	public void testEquals() {
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		assertTrue(translations.equals(translations));
		assertFalse(translations.equals(null));
		SingleLanguageTranslations translations2 = new SingleLanguageTranslations();
		assertTrue(translations.equals(translations2));
		assertTrue(translations2.equals(translations));

		translations.add("Source", "Quelle");
		assertFalse(translations.equals(translations2));
		assertFalse(translations2.equals(translations));

		translations2.add("Source", "Quelle");
		assertTrue(translations.equals(translations2));
		assertTrue(translations2.equals(translations));
	}

	@Test
	public void testClone() {
		SingleLanguageTranslations origin = new SingleLanguageTranslations();
		origin.add("Source", "Quelle");
		SingleLanguageTranslations cloned = (SingleLanguageTranslations) origin
				.clone();
		assertNotSame(origin, cloned);
		assertEquals(origin, cloned);
		cloned.set("Source", "Quelle");
		assertEquals(origin, cloned);
		cloned.set("Source2", "Quelle2");
		assertFalse(origin.equals(cloned));
	}
}
