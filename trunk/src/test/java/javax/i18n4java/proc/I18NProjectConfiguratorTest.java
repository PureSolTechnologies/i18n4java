/****************************************************************************
 *
 *   I18NProjectConfiguratorTest.java
 *   -------------------
 *   copyright            : (c) 2009-2011 by PureSol-Technologies
 *   author               : Rick-Rainer Ludwig
 *   email                : ludwig@puresol-technologies.com
 *
 ****************************************************************************/

/****************************************************************************
 *
 * Copyright 2009-2011 PureSol-Technologies
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
 ****************************************************************************/
 
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
