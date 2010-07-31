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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

/**
 * This class is for reading and writing a simple XML based translations file
 * with support for a lot of different languages.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class I18NFile {

	private static Logger logger = Logger.getLogger(I18NFile.class);

	static public File getResource(File file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("package")) {
					line = line.replaceAll("^.*package\\s*", "");
					line = line.replaceAll("\\s*;\\s*$", "");
					line = line.replaceAll("\\.", "/");
					line += "/" + file.getName();
					line = line.replaceAll("\\.[^\\.]*$", ".i18n");
					reader.close();
					return new File(line);
				}
			}
			reader.close();
			return new File(file.getName());
		} catch (IOException e) {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			return null;
		}
	}

	public static boolean isFinished(File file) {
		try {
			MultiLanguageTranslations translations = read(file);
			return translations.isTranslationFinished();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	static public boolean write(File file,
			MultiLanguageTranslations translations) {
		try {
			translations = (MultiLanguageTranslations) translations.clone();
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

	static public MultiLanguageTranslations read(File file)
			throws FileNotFoundException {
		try {
			if (!file.exists()) {
				throw new FileNotFoundException("File " + file.getPath()
						+ " could not be found!");
			}
			JAXBContext context = JAXBContext
					.newInstance(MultiLanguageTranslations.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			MultiLanguageTranslations translations = (MultiLanguageTranslations) unmarshaller
					.unmarshal(file);
			translations.addLineBreaks();
			return translations;
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	static public MultiLanguageTranslations read(InputStream inputStream)
			throws IOException {
		try {
			if (inputStream == null) {
				throw new IOException("Input stream was not available!");
			}
			JAXBContext context = JAXBContext
					.newInstance(MultiLanguageTranslations.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			MultiLanguageTranslations translations = (MultiLanguageTranslations) unmarshaller
					.unmarshal(inputStream);
			translations.addLineBreaks();
			return translations;
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
