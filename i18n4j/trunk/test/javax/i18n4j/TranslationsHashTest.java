/***************************************************************************
 *
 *   TranslationsHashTest.java
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
