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

package javax.i18n4java.proc;

import java.io.File;
import java.io.FileNotFoundException;

import javax.i18n4java.Translator;
import javax.i18n4java.data.MultiLanguageTranslations;
import javax.i18n4java.proc.I18NJavaParser;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * This test case checks the I18N Java parser for functionality.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class I18NJavaParserTest extends TestCase {

	private static final Translator translator = Translator
			.getTranslator(I18NJavaParserTest.class);

	/**
	 * This method just includes different representations of I18N strings.
	 */
	public void translationExamples() {
		System.out.println(translator.i18n("tr(String)"));
		System.out.println(translator.i18n("tr(String, Objects)", 123, "abc"));
		System.out.println(Translator.getTranslator(I18NJavaParserTest.class)
				.i18n("tr(Context,String)"));
		System.out.println(Translator.getTranslator(I18NJavaParserTest.class)
				.i18n("tr(Context,String, Objects)", 123, "abc"));
		System.out
				.println(translator.i18n("This test for processing multi line"
						+ "texts in tr()!"));

		System.out.println(translator.i18n("Test!"));
	}

	@Before
	public void initLogger() {
		Logger logger = Logger.getLogger(I18NJavaParser.class);
		logger.addAppender(new ConsoleAppender(new SimpleLayout()));
		logger.setLevel(Level.TRACE);
		logger.trace("Logger set.");
	}

	@Test
	public void testParser() {
		try {
			MultiLanguageTranslations translations = I18NJavaParser
					.parseFile(new File(
							"src/test/java/javax/i18n4java/proc/I18NJavaParserTest.java"));
			Assert.assertNotNull(translations);
			translations.print();
			Assert.assertNotNull(translations.get("tr(String)"));
			Assert.assertNotNull(translations.get("tr(String)"));
			Assert.assertNotNull(translations.get("tr(String, Objects)"));
			Assert.assertNotNull(translations.get("tr(Context,String)"));
			Assert.assertNotNull(translations
					.get("tr(Context,String, Objects)"));
			Assert.assertNotNull(translations
					.get("This test for processing multi line"
							+ "texts in tr()!"));
			Assert.assertNotNull(translations
					.get("This exception was not expected:"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Assert.fail(translator.i18n("This exception was not expected:"));
		}
	}

	@Test
	public void testFileNotFound() {
		try {
			I18NJavaParser.parseFile(new File("this/file/is/not/existent"));
			Assert.fail("FileNotFoundException was expected!");
		} catch (FileNotFoundException e) {
			// nothing to catch, this exception was expected!
		}
	}
}
