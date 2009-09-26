/***************************************************************************
 *
 *   I18NJavaParserTest.java
 *   -------------------
 *   copyright            : (c) 2009 by Rick-Rainer Ludwig
 *   author               : Rick-Rainer Ludwig
 *   email                : rl719236@sourceforge.net
 *
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package javax.i18n4j;

import java.io.File;
import java.io.FileNotFoundException;

import javax.i18n4j.I18NJavaParser;
import javax.i18n4j.MultiLanguageTranslations;
import javax.i18n4j.Translator;

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
							"test/javax/i18n4j/I18NJavaParserTest.java"));
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
