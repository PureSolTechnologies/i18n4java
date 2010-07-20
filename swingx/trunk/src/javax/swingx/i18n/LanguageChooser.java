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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swingx.ComboBox;

/**
 * This class provides a combobox with all available languages for choosing.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class LanguageChooser extends ComboBox {

	private static final long serialVersionUID = -2438620845333994142L;

	private final List<String> languageCodes = new ArrayList<String>();
	private final Map<String, Locale> names2Locales = new Hashtable<String, Locale>();

	public LanguageChooser() {
		super();
		readLocales();
		insertLocales();
	}

	private void readLocales() {
		String[] languages = Locale.getISOLanguages();
		for (String language : languages) {
			Locale locale = new Locale(language);
			languageCodes.add(language);
			names2Locales.put(language, locale);
		}
		Collections.sort(languageCodes);
	}

	private void insertLocales() {
		for (String languageCode : languageCodes) {
			Locale locale = names2Locales.get(languageCode);
			addItem(languageCode + " / " + locale.getDisplayName());
		}
	}

	public Locale getSelectedLocale() {
		return names2Locales.get(languageCodes.get(getSelectedIndex()));
	}

	public void setSelectedLocale(Locale locale) {
		for (int index = 0; index < languageCodes.size(); index++) {
			if (names2Locales.get(languageCodes.get(index)).equals(locale)) {
				setSelectedIndex(index);
				break;
			}
		}
	}
}
