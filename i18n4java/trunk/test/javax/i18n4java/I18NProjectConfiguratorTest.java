package javax.i18n4java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.i18n4java.I18NProjectConfiguration;

import org.junit.Test;

import junit.framework.TestCase;

public class I18NProjectConfiguratorTest extends TestCase {

	@Test
	public void testReadI18NProperties() {
		try {
			I18NProjectConfiguration config = new I18NProjectConfiguration(
					new File("."));
			assertEquals(".", config.getRelativeProjectTopDirectory());
			assertEquals("src", config.getRelativeSourceDirectory());
			assertEquals("i18n", config.getRelativeI18nDirectory());
			assertEquals("res", config.getRelativeDestinationDirectory());
			assertTrue(config.getProjectDirectory().exists());
			assertTrue(config.getSourceDirectory().exists());
			assertTrue(config.getI18nDirectory().exists());
			assertTrue(config.getDestinationDirectory().exists());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("No exception was expected!");
		} catch (IOException e) {
			e.printStackTrace();
			fail("No exception was expected!");
		}
	}

}
