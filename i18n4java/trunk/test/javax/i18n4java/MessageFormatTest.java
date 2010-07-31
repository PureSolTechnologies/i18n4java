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

import java.text.MessageFormat;
import java.util.Locale;

import javax.i18n4java.Translator;

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
