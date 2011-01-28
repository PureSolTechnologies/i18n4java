package javax.i18n4java.data;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

public class TRFileTest {

	@Test
	public void testGetResourceLocale() {
		assertEquals("/javax/i18n4java/TestClass.de.tr",
				TRFile.getResourceName("javax.i18n4java.TestClass", new Locale(
						"de")));
		assertEquals("/javax/i18n4java/TestClass.de_DE.tr",
				TRFile.getResourceName("javax.i18n4java.TestClass", new Locale(
						"de", "DE")));
	}

}
