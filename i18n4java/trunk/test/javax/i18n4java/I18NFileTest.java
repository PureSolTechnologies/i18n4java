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

package javax.i18n4java;

import java.io.File;
import java.util.Locale;

import javax.i18n4java.I18NFile;
import javax.i18n4java.TRFile;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class I18NFileTest extends TestCase {

	@Test
	public void testGetI18NFile() {
		Assert.assertEquals("/javax/i18n4j/TestClass.de.tr", TRFile
				.getResource("javax.i18n4j.TestClass", new Locale("de", "DE")));

		Assert.assertEquals("/javax/i18n4j/TestClass.de.tr", TRFile
				.getResource("javax.i18n4j.TestClass", "de"));

		File file = new File("src/javax/i18n4j/I18NFile.java");
		Assert.assertTrue(file.exists());
		Assert.assertEquals(new File("javax/i18n4j/I18NFile.i18n"), I18NFile
				.getResource(file));
	}
}
