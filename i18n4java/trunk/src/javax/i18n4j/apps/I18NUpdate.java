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

package javax.i18n4j.apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
 */
public class I18NUpdate {

	private static final Logger logger = Logger.getLogger(I18NUpdate.class);
	private static final Translator translator = Translator
			.getTranslator(I18NUpdate.class);

	private final File i18nDirectory;
	private final File sourceDirectory;
	private final List<File> inputFiles = new ArrayList<File>();

	static public boolean isCorrectProjectDirectory(File directory) {
		if (!new File(directory + "/src").exists()) {
			return false;
		}
		if (!new File(directory + "/res").exists()) {
			return false;
		}
		return true;
	}

	public I18NUpdate(File projectDirectory) {
		this(new File(projectDirectory, "src"), new File(projectDirectory,
				"res"));
	}

	public I18NUpdate(File sourceDirectory, File i18nDirectory) {
		super();
		this.sourceDirectory = sourceDirectory;
		this.i18nDirectory = i18nDirectory;
	}

	public I18NUpdate(String args[]) {
		if ((args.length == 0) || (args.length > 2)) {
			sourceDirectory = null;
			i18nDirectory = null;
			showUsage();
			return;
		}
		if (args.length == 1) {
			sourceDirectory = new File(args[0], "src");
			i18nDirectory = new File(args[0], "res");
		} else {
			sourceDirectory = new File(args[0]);
			i18nDirectory = new File(args[1]);
		}
	}

	private void showUsage() {
		System.out.println("==========");
		System.out.println("I18NUpdate");
		System.out.println("==========");
		System.out.println();
		System.out.println(translator
				.i18n("usage: I18NUpdate <project directory>"));
		System.out.println(translator
				.i18n("   or  I18NUpdate <source directory> <i18n directory>"));
		System.out.println();
		System.out
				.println(translator
						.i18n("This application reads a source directory (<project directory>/src)\n"
								+ "and looks for all Java files (<source directory>/**/*.java).\n"
								+ "These files are scanned for i18n strings and the i18n files are\n"
								+ "stored classwise in a i18n directory  (<project directory>/res).\n"));
	}

	public void update() {
		findAllInputFiles();
		processFiles();
	}

	private void findAllInputFiles() {
		inputFiles.addAll(FileSearch.find(sourceDirectory, "*.java"));
	}

	private void processFiles() {
		for (File file : inputFiles) {
			processFile(new File(sourceDirectory, file.toString()));
		}
	}

	private void processFile(File file) {
		try {
			logger.info("Process file " + file.getPath() + "...");
			MultiLanguageTranslations i18nSources = collectI18NSource(file);
			if (i18nSources.hasTranslations()) {
				File i18nFile = new File(i18nDirectory + "/"
						+ I18NFile.getResource(file).getPath());
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
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				logger.warn("Could not create directory '"
						+ directory.getPath() + "'");
				return;
			}
		}
		writeTranslations(file, translations);
	}

	private MultiLanguageTranslations readTranslations(File file) {
		try {
			logger.info("Read " + file.getPath() + " for translations...");
			MultiLanguageTranslations hash = I18NFile.read(file);
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
		I18NFile.write(file, translations);
	}

	static public void main(String args[]) {
		new I18NUpdate(args).update();
	}
}
