/***************************************************************************
 *
 *   LocaleTest.java
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

import java.util.Locale;
import java.util.Vector;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class LocaleTest extends TestCase {

	@Test
	public void testAvailableLocales() {
		Locale locales[] = Locale.getAvailableLocales();
		Vector<String> localeShortCuts = new Vector<String>();
		for (int index = 0; index < locales.length; index++) {
			Locale locale = locales[index];
			localeShortCuts.add(locale.toString());
			System.out.println(locale.toString() + "\t"
					+ locale.getDisplayName() + ":\t"
					+ locale.getDisplayLanguage() + "\t"
					+ locale.getDisplayCountry() + "\t"
					+ locale.getDisplayVariant());
		}
		Assert.assertTrue(localeShortCuts.contains("de_DE"));
		Assert.assertTrue(localeShortCuts.contains("en_GB"));
		Assert.assertTrue(localeShortCuts.contains("en_US"));
		Assert.assertTrue(localeShortCuts.contains("vi_VN"));
		Assert.assertTrue(localeShortCuts.contains("zh_TW"));
	}

}
