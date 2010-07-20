/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************/

package javax.i18n4j;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import junit.framework.TestCase;

public class FileSearchTest extends TestCase {

	@Test
	public void testOSSpecificSeparator() {
		String osName = System.getProperty("os.name");
		assertNotNull(osName);
		assertFalse(osName.isEmpty());
		if (osName.equals("Windows XP")) {
			assertEquals("\\", File.separator);
		} else if (osName.equals("Linux")) {
			assertEquals("/", File.separator);
		} else {
			throw new RuntimeException("Operating system '" + osName
					+ "'not included in test! Add needed.");
		}
	}

	@Test
	public void testPatternBehaviour() {
		assertFalse(Pattern.matches("[^\\\\]", "\\"));
		assertTrue(Pattern.matches("[^\\\\]", "/"));
	}

	@Test
	public void testPatternConversion() {
		// basic elements
		assertEquals("\\.", FileSearch.wildcardsToRegExp("."));
		if (File.separator.equals("\\")) {
			assertEquals("[^\\\\]", FileSearch.wildcardsToRegExp("?"));
			assertEquals("[^\\\\]*", FileSearch.wildcardsToRegExp("*"));
		} else {
			assertEquals("[^/]", FileSearch.wildcardsToRegExp("?"));
			assertEquals("[^/]*", FileSearch.wildcardsToRegExp("*"));
		}
	}

	@Test
	public void testRecursiveFileSearch() {
		List<File> files = FileSearch.find(new File("."), "*.j??*");
		assertNotNull(files);
		assertTrue(files.size() > 0);
		boolean foundSelf = false;
		boolean foundClass = false;
		for (File file : files) {
			// System.out.println(file);
			if (file.equals(new File("/test/javax/i18n4j/FileSearchTest.java"))) {
				foundSelf = true;
			}
			if (file.equals(new File("/src/javax/i18n4j/FileSearch.java"))) {
				foundClass = true;
			}
		}
		assertTrue(foundSelf);
		assertTrue(foundClass);
	}
}
