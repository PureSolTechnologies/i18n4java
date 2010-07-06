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

import javax.i18n4j.MultiLanguageTranslations;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class TranslationsHashTest extends TestCase {

	@Test
	public void testSetAndGetTranslation() {
		MultiLanguageTranslations hash = new MultiLanguageTranslations();
		Assert.assertEquals("English", hash.get("English", "de"));

		hash.set("English", "de", "Deutsch");
		hash.set("English", "vi", "Tieng Viet");

		Assert.assertEquals("English", hash.get("English", ""));
		Assert.assertEquals("Deutsch", hash.get("English", "de"));
		Assert.assertEquals("Tieng Viet", hash.get("English", "vi"));
	}
}
