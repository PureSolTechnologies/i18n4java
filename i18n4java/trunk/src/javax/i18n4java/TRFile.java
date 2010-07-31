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

package javax.i18n4java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class TRFile {

	private static final Logger logger = Logger.getLogger(TRFile.class);

	/**
	 * This method calculates the resource path to a context translation file.
	 * The language information from the locale is used to invoke another
	 * getTrResource method to get the name.
	 * 
	 * @see I18NFile#getResource(File)
	 * @param context
	 *            is the context name to use. In normal case its
	 *            class.getName().
	 * @param locale
	 *            is a locale as language specifier to be used.
	 * @return A String is returned with a path to the translation file. String
	 *         is used and not(!) File to due the issue that resources within
	 *         JARs are to be specified with normal '/' slash separators.
	 */
	static public String getResource(String context, Locale locale) {
		return getResource(context, locale.getLanguage());
	}

	/**
	 * This method calculates the resource path to a context translation file.
	 * 
	 * @param context
	 *            is the context name to use. In normal case its
	 *            class.getName().
	 * @param language
	 *            is the language to look for.
	 * @return A String is returned with a path to the translation file. String
	 *         is used and not(!) File to due the issue that resources within
	 *         JARs are to be specified with normal '/' slash separators.
	 */
	static public String getResource(String context, String language) {
		return "/" + context.replaceAll("\\.", "/") + "." + language + ".tr";
	}

	static public boolean write(File file,
			SingleLanguageTranslations translations) {
		try {
			translations = (SingleLanguageTranslations) translations.clone();
			translations.removeLineBreaks();
			JAXBContext context = JAXBContext.newInstance(translations
					.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(translations, file);
			return true;
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	static public SingleLanguageTranslations read(File file)
			throws FileNotFoundException {
		try {
			if (!file.exists()) {
				throw new FileNotFoundException("File " + file.getPath()
						+ " could not be found!");
			}
			JAXBContext context = JAXBContext
					.newInstance(SingleLanguageTranslations.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			SingleLanguageTranslations translations = (SingleLanguageTranslations) unmarshaller
					.unmarshal(file);
			translations.addLineBreaks();
			return translations;
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	static public SingleLanguageTranslations read(InputStream inputStream)
			throws IOException {
		try {
			if (inputStream == null) {
				throw new IOException("Input stream was not available!");
			}
			JAXBContext context = JAXBContext
					.newInstance(SingleLanguageTranslations.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			SingleLanguageTranslations translations = (SingleLanguageTranslations) unmarshaller
					.unmarshal(inputStream);
			translations.addLineBreaks();
			return translations;
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
