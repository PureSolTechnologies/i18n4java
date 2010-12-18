package javax.i18n4java.proc;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.i18n4java.proc.I18NProjectConfiguration;

import org.junit.Test;

public class I18NProjectConfiguratorTest {

	@Test
	public void testReadI18NProperties() {
		try {
			I18NProjectConfiguration config = new I18NProjectConfiguration(
					new File("."));
			assertEquals("src", config.getRelativeProjectTopDirectory());
			assertEquals("main/java", config.getRelativeSourceDirectory());
			assertEquals("i18n", config.getRelativeI18nDirectory());
			assertEquals("main/resources",
					config.getRelativeDestinationDirectory());
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
