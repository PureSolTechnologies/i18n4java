/***************************************************************************
 *
 *   MessageFormatTest.java
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

import java.text.MessageFormat;
import java.util.Locale;

import javax.i18n4j.Translator;

import org.junit.Test;

public class MessageFormatTest {

	private static final Translator translator = Translator
			.getTranslator(MessageFormatTest.class);

	@Test
	public void testStringMessageFormat() {
		String formatString = "This is a {0}!";
		System.out.println(formatString);
		MessageFormat message = new MessageFormat(formatString, Locale
				.getDefault());
		Object[] o = { "TEST" };
		String s = message.format(o);
		System.out.println(s);

		System.out.println(translator.i18n("String {0}!", "TEST"));

		Locale.setDefault(new Locale("de", "DE"));
		formatString = "Der Benutzer {0} {1} mit der ID {2,number,integer} existiert bereits!";
		message = new MessageFormat(formatString, Locale.getDefault());
		Object[] oo = { "TEST", "TEST2", 9999 };
		s = message.format(oo);
		System.out.println(s);
	}

	public String tr(String text, Object... params) {
		return new MessageFormat(text, new Locale("de", "DE")).format(params);
	}

	@Test
	public void testStringMessageFormat2() {
		Object[] test = { "Test", "Test2", 2 };
		System.out
				.println(new MessageFormat(
						"Der Benutzer {0} {1} mit der ID {2,number,integer} existiert bereits!",
						new Locale("de", "DE")).format(test));
		System.out
				.println(this
						.tr(
								"Der Benutzer {0} {1} mit der ID {2,number,integer} existiert bereits!",
								"Test", "Test2", 2));
		System.out
				.println(translator
						.i18n(
								"Der Benutzer {0} {1} mit der ID {2,number,integer} existiert bereits!",
								"Test", "Test2", 2));
	}
}
