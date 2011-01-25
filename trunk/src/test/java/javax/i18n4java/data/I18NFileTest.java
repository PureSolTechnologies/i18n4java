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

import java.io.File;
import java.util.Locale;

import javax.i18n4java.data.I18NFile;
import javax.i18n4java.data.TRFile;

import org.junit.Test;

public class I18NFileTest {

	@Test
	public void testGetResourceLocale() {
		assertEquals("/javax/i18n4java/TestClass.de_DE.tr", TRFile.getResource(
				"javax.i18n4java.TestClass", new Locale("de", "DE")));

		assertEquals("/javax/i18n4java/TestClass.de.tr",
				TRFile.getResource("javax.i18n4java.TestClass", "de"));
	}

	@Test
	public void testGetResourceString() {
		assertEquals("/javax/i18n4java/TestClass.de.tr",
				TRFile.getResource("javax.i18n4java.TestClass", "de"));
	}

	@Test
	public void testGetResourceFile() {
		File file = new File("src/main/java/javax/i18n4java/data/I18NFile.java");
		assertTrue(file.exists());
		assertEquals(new File("javax/i18n4java/data/I18NFile.i18n"),
				I18NFile.getResource(file));
	}
}
