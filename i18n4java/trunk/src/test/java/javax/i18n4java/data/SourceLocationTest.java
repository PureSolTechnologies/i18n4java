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

package javax.i18n4java.data;

import javax.i18n4java.data.SourceLocation;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SourceLocationTest extends TestCase {

	@Test
	public void testConstructors() {
		SourceLocation sourceLocation = new SourceLocation("File1.java", 100);
		Assert.assertEquals("File1.java", sourceLocation.getFile());
		Assert.assertEquals(100, sourceLocation.getLine());
		Assert.assertEquals(1, sourceLocation.getLineCount());

		sourceLocation = new SourceLocation("File2.java", 200, 2);
		Assert.assertEquals("File2.java", sourceLocation.getFile());
		Assert.assertEquals(200, sourceLocation.getLine());
		Assert.assertEquals(2, sourceLocation.getLineCount());
	}

	@Test
	public void testHashCode() {
		SourceLocation sourceLocation = new SourceLocation("File", 1, 2);
		System.out.println(sourceLocation.hashCode());
		Assert.assertTrue(sourceLocation.hashCode() > 0);
	}

	@Test
	public void testEquals() {
		SourceLocation sl1 = new SourceLocation("File", 1, 2);
		Assert.assertTrue(sl1.equals(sl1));
		Assert.assertFalse(sl1.equals(null));
		Assert.assertFalse(sl1.equals("Test"));

		SourceLocation sl2 = new SourceLocation("File", 1, 2);
		Assert.assertTrue(sl1.equals(sl2));
		Assert.assertTrue(sl2.equals(sl1));
	}

	@Test
	public void testClone() {
		SourceLocation origin = new SourceLocation("File.java", 1, 2);
		SourceLocation cloned = (SourceLocation) origin.clone();
		Assert.assertTrue(origin.equals(cloned));
		Assert.assertEquals(0, origin.compareTo(cloned));
		Assert.assertEquals(0, cloned.compareTo(origin));
	}

	@Test
	public void testExceptions() {
		try {
			new SourceLocation(null, 1, 2);
			Assert.fail("An IllegalArguementException was expected due to a null file!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			new SourceLocation("", 1, 2);
			Assert.fail("An IllegalArguementException was expected due to an empty file!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			new SourceLocation("File.java", 0, 2);
			Assert.fail("An IllegalArguementException was expected due to 0 line number!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			new SourceLocation("File.java", -2, 2);
			Assert.fail("An IllegalArguementException was expected due to negative line number!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			new SourceLocation("File.java", 1, 0);
			Assert.fail("An IllegalArguementException was expected due to 0 line count!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
		try {
			new SourceLocation("File.java", 1, -3);
			Assert.fail("An IllegalArguementException was expected due to negative line count!");
		} catch (IllegalArgumentException e) {
			// nothing to catch, exception was expected!
		}
	}

	@Test
	public void testCompareTo() {
		SourceLocation default1 = new SourceLocation("File", 1, 2);
		SourceLocation default2 = new SourceLocation("File", 1, 2);
		Assert.assertEquals(0, default1.compareTo(default2));
		Assert.assertEquals(0, default2.compareTo(default1));
		Assert.assertTrue(default1.equals(default2));
		Assert.assertTrue(default2.equals(default1));
		Assert.assertEquals(0, default1.compareTo(default1));
		Assert.assertEquals(-1, default1.compareTo(null));
	}

	@Test
	public void testToString() {
		Assert.assertEquals("File.java:1",
				new SourceLocation("File.java", 1).toString());
		Assert.assertEquals("File.java:1-2", new SourceLocation("File.java", 1,
				2).toString());
	}
}
