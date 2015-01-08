package com.puresoltechnologies.i18n4java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Ignore;
import org.junit.Test;

public class LocaleChooserTest {

	@Test
	public void testInstance() {
		assertNotNull(new LocaleChooser());
	}

	@Test
	@Ignore("This test does not run in command line...")
	public void testDefaultValues() {
		LocaleChooser chooser = new LocaleChooser();
		assertEquals(new Locale("ar"), chooser.getSelectedLocale());
	}

	@Test
	public void testSettersAndGetters() {
		LocaleChooser chooser = new LocaleChooser();
		chooser.setSelectedLocale(new Locale("en", "US"));
		assertEquals(new Locale("en", "US"), chooser.getSelectedLocale());
	}

}
