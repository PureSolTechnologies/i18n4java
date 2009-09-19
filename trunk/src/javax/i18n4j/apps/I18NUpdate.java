package javax.i18n4j.apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import javax.i18n4j.FileSearch;
import javax.i18n4j.I18NFile;
import javax.i18n4j.I18NJavaParser;
import javax.i18n4j.MultiLanguageTranslations;
import javax.i18n4j.Translator;

import org.apache.log4j.Logger;

/**
 * This applications reads files and directories given by command line and
 * search the found files for strings to be translated. This tools is similar to
 * Trolltech's lupdate.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class I18NUpdate {

	private static final Logger logger = Logger.getLogger(I18NUpdate.class);
	private static final Translator translator = Translator
			.getTranslator(I18NUpdate.class);

	private String i18nDirectory = "";
	private String sourceDirectory = "";
	private Vector<File> inputFiles = new Vector<File>();

	static public boolean isCorrectProjectDirectory(File directory) {
		if (!new File(directory + "/src").exists()) {
			return false;
		}
		if (!new File(directory + "/res").exists()) {
			return false;
		}
		return true;
	}

	public I18NUpdate(String projectDirectory) {
		this.sourceDirectory = projectDirectory + "/src/**/*.java";
		this.i18nDirectory = projectDirectory + "/res";
	}

	public I18NUpdate(String args[]) {
		if ((args.length == 0) || (args.length > 2)) {
			System.out.println(translator
					.i18n("usage:  I18NUpdate <directory>"));
			System.out
					.println(translator
							.i18n("usage2: I18NUpdate <source directory> <resource directory>"));
			return;
		}
		if (args.length == 1) {
			this.sourceDirectory = args[0] + "/src/**/*.java";
			this.i18nDirectory = args[0] + "/res";
		} else {
			sourceDirectory = args[0];
			i18nDirectory = args[1];
		}
	}

	public void update() {
		findAllInputFiles();
		processFiles();
	}

	private void findAllInputFiles() {
		inputFiles.addAll(FileSearch.find(sourceDirectory));
	}

	private void processFiles() {
		Iterator<File> i = inputFiles.iterator();
		while (i.hasNext()) {
			File file = i.next();
			processFile(file);
		}
	}

	private void processFile(File file) {
		try {
			logger.info("Process file " + file.getPath() + "...");
			MultiLanguageTranslations i18nSources = collectI18NSource(file);
			if (i18nSources.hasTranslations()) {
				File i18nFile = new File(i18nDirectory + "/"
						+ I18NFile.getI18NFile(file).getPath());
				addNewSourcesToExistingFile(i18nFile, i18nSources);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private MultiLanguageTranslations collectI18NSource(File file)
			throws FileNotFoundException {
		logger.info("Scanning " + file.getPath() + "...");
		MultiLanguageTranslations sources = I18NJavaParser.parseFile(file);
		sources.print();
		return sources;
	}

	private void addNewSourcesToExistingFile(File file,
			MultiLanguageTranslations i18nSources) {
		MultiLanguageTranslations translations = readTranslations(file);
		translations.add(i18nSources);
		File directory = new File(file.getPath().replaceAll(file.getName(), ""));
		directory.mkdirs();
		writeTranslations(file, translations);
	}

	private MultiLanguageTranslations readTranslations(File file) {
		try {
			logger.info("Read " + file.getPath() + " for translations...");
			MultiLanguageTranslations hash = I18NFile.readMultiLanguageFile(file);
			hash.removeLocations();
			return hash;
		} catch (FileNotFoundException e) {
			logger.warn(e.getMessage(), e);
			return new MultiLanguageTranslations();
		}
	}

	private void writeTranslations(File file,
			MultiLanguageTranslations translations) {
		logger.info("Write translations to " + file.getPath());
		I18NFile.writeMultiLanguageFile(file, translations);
	}

	static public void main(String args[]) {
		System.out.println("Set translator...");
		Translator.setDefault(new Locale("de", "DE"));
		System.out.println("set.");
		new I18NUpdate(args).update();
	}
}
