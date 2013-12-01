package com.puresoltechnologies.i18n4java;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

import com.puresoltechnologies.i18n4java.LocaleChooser;

public class LocaleChooserTest {

	@Test
	public void testInstance() {
		assertNotNull(new LocaleChooser());
	}

	@Test
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
