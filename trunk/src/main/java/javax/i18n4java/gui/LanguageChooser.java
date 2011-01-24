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

package javax.i18n4java.gui;

import java.util.List;
import java.util.Locale;

import javax.i18n4java.Translator;
import javax.i18n4java.utils.I18N4Java;
import javax.swing.JComboBox;

/**
 * This class provides a combobox with all available languages for choosing.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class LanguageChooser extends JComboBox {

	private static final long serialVersionUID = -2438620845333994142L;

	private final List<String> availableISOLanguages = I18N4Java
			.getISOLanguages();

	public LanguageChooser() {
		super();
		insertLocales();
	}

	private void insertLocales() {
		for (String languageCode : availableISOLanguages) {
			addItem(languageCode + " / "
					+ new Locale(languageCode).getDisplayName());
		}
		setSelectedLocale(Translator.getDefault());
	}

	public Locale getSelectedLocale() {
		return new Locale(availableISOLanguages.get(getSelectedIndex()));
	}

	public void setSelectedLocale(Locale locale) {
		for (int index = 0; index < availableISOLanguages.size(); index++) {
			if (availableISOLanguages.get(index).equals(locale.getLanguage())) {
				setSelectedIndex(index);
				break;
			}
		}
	}
}
