package javax.i18n4java;

import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.AbstractButton;

public class TranslationUpdater {

	private final Map<Object, LanguageChangeListener> listeners = new WeakHashMap<Object, LanguageChangeListener>();

	public void i18n(final String text, final Translator translator,
			final AbstractButton button, final Object... params) {
		LanguageChangeListener listener = new LanguageChangeListener() {
			@Override
			public void translationChanged(Translator translator) {
				String translation = translator.i18n(text, params);
				button.setText(translation);
				if ((translation != null) && (translation.length() > 0)) {
					button.setMnemonic(translation.toCharArray()[0]);
				} else {
					button.setMnemonic((char) 0);
				}
			}
		};
		translator.addLanguageChangeListener(listener);
		listeners.put(button, listener);
		listener.translationChanged(translator);
	}
}
