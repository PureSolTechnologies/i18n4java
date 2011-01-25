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
