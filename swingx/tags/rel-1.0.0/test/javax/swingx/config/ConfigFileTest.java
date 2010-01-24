package javax.swingx.config;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ConfigFileTest extends TestCase {

	@Test
	public void testGetPotentialConfigFiles() {
		ArrayList<String> files = ConfigFile
				.getPotentialConfigFiles("test/config");
		Assert.assertTrue(files.contains("/etc/test/config"));
		Assert.assertTrue(files.contains(System.getProperty("user.home")
				+ System.getProperty("file.separator") + "test/config"));
		Assert.assertTrue(files.contains(System.getProperty("user.home")
				+ System.getProperty("file.separator") + ".test/config"));
		Assert.assertTrue(files.contains(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "test/config"));
		Assert.assertTrue(files.contains(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + ".test/config"));
		for (String file:files) {
			System.out.println(file);
		}
	}
}
