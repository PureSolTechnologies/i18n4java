package javax.i18n4j;

import java.io.File;
import java.util.Locale;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class I18NFileTest extends TestCase {

	@Test
	public void testGetI18NFile() {
		Assert.assertEquals("/javax/i18n4j/TestClass.de.tr",
				I18NFile.getTrResource("javax.i18n4j.TestClass", new Locale(
						"de", "DE")));

		Assert.assertEquals("/javax/i18n4j/TestClass.de.tr", I18NFile
				.getTrResource("javax.i18n4j.TestClass", "de"));

		File file = new File("src/javax/i18n4j/I18NFile.java");
		Assert.assertTrue(file.exists());
		Assert.assertEquals("javax/i18n4j/I18NFile.i18n", I18NFile
				.getI18NResource(file).getPath());
	}
}
