/****************************************************************************
 *
 *   KeyStrokeUpdaterTest.java
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
 
package javax.i18n4java;

import static org.junit.Assert.*;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.junit.Test;

public class KeyStrokeUpdaterTest {

	private static final Translator translator = Translator
			.getTranslator(KeyStrokeUpdaterTest.class);

	@Test
	public void testInstance() {
		assertNotNull(new KeyStrokeUpdater());
	}

	@Test
	public void testWeakReferencesAbstractButton() {
		KeyStrokeUpdater o = new KeyStrokeUpdater();
		JMenuItem item = new JMenuItem("Open");
		o.i18n("ctrl O", translator, item);
		item.getAccelerator();
		assertEquals(KeyStroke.getKeyStroke("ctrl O"), item.getAccelerator());
		assertEquals(1, o.getListeners().keySet().size());
		item = null;
		System.gc();
		assertEquals(0, o.getListeners().keySet().size());
	}
}
