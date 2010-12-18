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

package javax.swingx.i18n;

import java.util.List;
import java.util.Locale;

import javax.i18n4java.I18N4Java;
import javax.swingx.ComboBox;

/**
 * This class provides a combobox with all available locales for choosing.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class LocaleChooser extends ComboBox {

	private static final long serialVersionUID = -5751261750747502182L;

	private final List<String> availableLocaleNames = I18N4Java
			.getAvailableLocaleNames();

	public LocaleChooser() {
		super();
		insertLocales();
	}

	private void insertLocales() {
		for (String localeName : availableLocaleNames) {
			addItem(localeName + " / "
					+ new Locale(localeName).getDisplayName());
		}
	}

	public Locale getSelectedLocale() {
		return new Locale(availableLocaleNames.get(getSelectedIndex()));
	}

	public void setSelectedLocale(Locale locale) {
		for (int index = 0; index < availableLocaleNames.size(); index++) {
			if (new Locale(availableLocaleNames.get(index)).equals(locale)) {
				setSelectedIndex(index);
				break;
			}
		}
	}
}
