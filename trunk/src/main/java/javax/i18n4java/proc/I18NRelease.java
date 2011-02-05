/****************************************************************************
 *
 *   I18NRelease.java
 *   -------------------
 *   copyright            : (c) 2009-2011 by PureSol-Technologies
 *   author               : Rick-Rainer Ludwig
 *   email                : ludwig@puresol-technologies.com
 *
 ****************************************************************************/

/****************************************************************************
 *
 * Copyright 2009-2011 PureSol-Technologies
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
 ****************************************************************************/

package javax.i18n4java.proc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.i18n4java.data.I18NFile;
import javax.i18n4java.data.LanguageSet;
import javax.i18n4java.data.MultiLanguageTranslations;
import javax.i18n4java.data.SingleLanguageTranslations;
import javax.i18n4java.data.TRFile;
import javax.i18n4java.proc.I18NProjectConfiguration;
import javax.i18n4java.utils.FileSearch;

import org.apache.log4j.Logger;

/**
 * This application converts all i18n files in the the specified directory and
 * converts them into tr files for usage in internationalized applications.
 * 
 * @author Rick-Rainer Ludwig
 */
public class I18NRelease {

	private static final Logger logger = Logger.getLogger(I18NRelease.class);

	private final I18NProjectConfiguration configuration;
	private final List<File> inputFiles = new ArrayList<File>();

	public I18NRelease(File projectDirectory) throws FileNotFoundException,
			IOException {
		configuration = new I18NProjectConfiguration(projectDirectory);
	}

	public void release() {
		findAllInputFiles();
		processFiles();
	}

	private void findAllInputFiles() {
		inputFiles.addAll(FileSearch.find(configuration.getI18nDirectory(),
				"*.i18n"));
	}

	private void processFiles() {
		for (File file : inputFiles) {
			processFile(file);
		}
	}

	private void processFile(File file) {
		try {
			logger.info("Process file " + file.getPath() + "...");
			File sourceFile = new File(configuration.getI18nDirectory(),
					file.getPath());
			MultiLanguageTranslations mlTranslations = I18NFile
					.read(sourceFile);
			for (Locale language : mlTranslations.getAvailableLanguages()) {
				File destinationFile = new File(
						configuration.getDestinationDirectory(), file.getPath()
								.replaceAll("\\.i18n", "." + language + ".tr"));
				release(mlTranslations, language, destinationFile);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void release(MultiLanguageTranslations mlTranslations,
			Locale language, File file) throws IOException {
		logger.info("Release language " + language + "  to file "
				+ file.getPath());
		SingleLanguageTranslations translations = new SingleLanguageTranslations();
		Set<String> sources = mlTranslations.getSources();
		for (String source : sources) {
			LanguageSet set = mlTranslations.get(source);
			if (set.containsLanguage(language)) {
				translations.add(source, set.get(language));
			}
		}
		TRFile.write(file, translations);
	}
}
