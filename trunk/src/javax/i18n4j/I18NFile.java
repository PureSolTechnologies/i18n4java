package javax.i18n4j;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 * This class is for reading and writing a simple XML based translations file
 * with support for a lot of different languages.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class I18NFile {

	static private Logger logger = Logger.getLogger(I18NFile.class);

	static public File getI18NFile(String context, Locale locale) {
		return new File("/" + context.replaceAll("\\.", "/") + "."
				+ locale.getLanguage() + ".tr");

	}

	static public File getI18NFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("package")) {
					line = line.replaceAll("^.*package\\s*", "");
					line = line.replaceAll("\\s*;\\s*$", "");
					line = line.replaceAll("\\.", "/");
					line += "/" + file.getName();
					line = line.replaceAll("\\.[^\\.]*$", ".tr");
					return new File(line);
				}
			}
			return new File(file.getName());
		} catch (IOException e) {
			return null;
		}
	}

	static public boolean writeWithJAXB(File file, MultiLanguageTranslations translations) {
		try {
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

	static public MultiLanguageTranslations readWithJAXB(File file)
			throws FileNotFoundException {
		try {
			if (!file.exists()) {
				throw new FileNotFoundException("File " + file.getPath()
						+ " could not be found!");
			}
			JAXBContext context = JAXBContext
					.newInstance(MultiLanguageTranslations.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (MultiLanguageTranslations) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	static public MultiLanguageTranslations readWithJAXB(InputStream inputStream)
			throws IOException {
		try {
			if (inputStream == null) {
				throw new IOException("Input stream was not available!");
			}
			JAXBContext context = JAXBContext
					.newInstance(MultiLanguageTranslations.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (MultiLanguageTranslations) unmarshaller.unmarshal(inputStream);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	static public void main(String[] args) {
		try {
			System.out.println(I18NFile.getI18NFile(
					new File("src/com/qsys/apps/I18NUpdate.java")).getPath());
			logger.addAppender(new ConsoleAppender(new SimpleLayout()));
			MultiLanguageTranslations translations;
			translations = I18NFile.readWithJAXB(new File(
					"translations/translations.tr", "de"));
			translations.print();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
