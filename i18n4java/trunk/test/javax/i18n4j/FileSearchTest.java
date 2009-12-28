package javax.i18n4j;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class FileSearchTest extends TestCase {

    @Test
    public void testFileSearch() {
	ArrayList<File> files = FileSearch.find("**/*.java");
	// check for correct file extension...
	boolean foundSelf = false;
	boolean foundClass = false;
	for (File file : files) {
	    Assert.assertTrue(file.getName().endsWith(".java"));
	    if (file.getPath().endsWith(
		    "/test/javax/i18n4j/FileSearchTest.java")) {
		foundSelf = true;
	    }
	    if (file.getPath().endsWith(
		    "/src/javax/i18n4j/FileSearch.java")) {
		foundClass = true;
	    }
	}
	Assert.assertTrue(foundSelf);
	Assert.assertTrue(foundClass);
    }
}
