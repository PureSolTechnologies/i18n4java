/****************************************************************************
 *
 *   MessageFormatTest.java
 *   -------------------
 *   copyright            : (c) 2009-2011 by PureSol-Technologies
 *   author               : Rick-Rainer Ludwig
 *   email                : ludwig@puresol-technologies.com
 *
 ****************************************************************************/

/****************************************************************************
 *
 * Copyright 2009-2011 PureSol-Technologies
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
 ****************************************************************************/
 
package com.puresoltechnologies.i18n4java;

import static org.junit.Assert.*;

import java.text.MessageFormat;
import java.util.Locale;

import org.junit.Test;

public class MessageFormatTest {

	public String tr(String text, Object... params) {
		return new MessageFormat(text, new Locale("de", "DE")).format(params);
	}

	@Test
	public void testStringMessageFormat() {
		Object[] test = { "Test", "Test2", 2 };
		assertEquals(
				"Der Benutzer Test Test2 mit der ID 2 existiert bereits!",
				new MessageFormat(
						"Der Benutzer {0} {1} mit der ID {2,number,integer} existiert bereits!",
						new Locale("de", "DE")).format(test));
		assertEquals(
				"Der Benutzer Test Test2 mit der ID 2 existiert bereits!",
				this.tr("Der Benutzer {0} {1} mit der ID {2,number,integer} existiert bereits!",
						"Test", "Test2", 2));
	}
}
