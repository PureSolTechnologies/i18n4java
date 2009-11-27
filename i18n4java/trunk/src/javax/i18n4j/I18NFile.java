/***************************************************************************
 *
 *   I18NFile.java
 *   -------------------
 *   copyright            : (c) 2009 by Rick-Rainer Ludwig
 *   author               : Rick-Rainer Ludwig
 *   email                : rl719236@sourceforge.net
 *
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package javax.i18n4j;

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

	static private Logger logger = Logger.getLogger(I18NFile.class);

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
