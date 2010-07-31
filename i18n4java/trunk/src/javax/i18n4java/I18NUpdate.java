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
import java.util.ArrayList;
import java.util.List;

import javax.i18n4java.FileSearch;
import javax.i18n4java.I18NFile;
import javax.i18n4java.I18NJavaParser;
import javax.i18n4java.I18NProjectConfiguration;
import javax.i18n4java.MultiLanguageTranslations;

import org.apache.log4j.Logger;

/**
 * This applications reads files and directories given by command line and
 * search the found files for strings to be translated. This tools is similar to
 * Trolltech's lupdate.
 * 
 * @author Rick-Rainer Ludwig
 */
public class I18NUpdate extends I18NProgress {

	private static final Logger logger = Logger.getLogger(I18NUpdate.class);

	private final I18NProjectConfiguration configuration;
	private final List<File> inputFiles = new ArrayList<File>();

	public I18NUpdate(File projectDirectory) throws FileNotFoundException,
			IOException {
		configuration = new I18NProjectConfiguration(projectDirectory);
	}

	public void update() {
		findAllInputFiles();
		processFiles();
	}

	private void findAllInputFiles() {
		inputFiles.addAll(FileSearch.find(configuration.getSourceDirectory(),
				"*.java"));
	}

	private void processFiles() {
		for (int i = 0; i < inputFiles.size(); i++) {
			File file = inputFiles.get(i);
			progressUpdate(0, inputFiles.size(), i, file.getPath());
			processFile(file);
		}
	}

	private void processFile(File file) {
		try {
			logger.info("Process file " + file.getPath() + "...");
			File sourceFile = new File(configuration.getSourceDirectory(),
					file.getPath());
			MultiLanguageTranslations i18nSources = collectI18NSource(sourceFile);
			if (i18nSources.hasTranslations()) {
				File i18nFile = new File(configuration.getI18nDirectory(),
						I18NFile.getResource(sourceFile).getPath());
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
}
