/****************************************************************************
 *
 *   I18NProjectTranslationPanel.java
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
 
package javax.i18n4java.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.i18n4java.TranslationUpdater;
import javax.i18n4java.Translator;
import javax.i18n4java.data.I18NFile;
import javax.i18n4java.data.LanguageSet;
import javax.i18n4java.data.MultiLanguageTranslations;
import javax.i18n4java.data.SourceLocation;
import javax.i18n4java.proc.I18NProjectConfiguration;
import javax.i18n4java.utils.FileSearch;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

public class I18NProjectTranslationPanel extends JPanel implements
		ActionListener, ListSelectionListener, CaretListener {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(I18NProjectTranslationPanel.class);
	private static final Translator translator = Translator
			.getTranslator(I18NProjectTranslationPanel.class);

	private final TranslationUpdater translationUpdater = new TranslationUpdater();

	private final TitledBorder languageBorder = BorderFactory
			.createTitledBorder("");

	// GUI elements...
	private final LocaleChooser localeChooser = new LocaleChooser();
	private final JList classes = new JList();
	private final JList reservoir = new JList();
	private final JTextArea source = new JTextArea();
	private final JTextArea translation = new JTextArea();
	private final JTextArea location = new JTextArea();

	private final List<File> files = new ArrayList<File>();
	private final Map<String, File> fileData = new HashMap<String, File>();
	private final Map<String, String> reservoirData = new HashMap<String, String>();

	// fields...
	private File i18nFile = null;
	private MultiLanguageTranslations translationsHash = null;

	private boolean changed = false;
	private String oldSource = "";
	private String oldTranslation = "";
	private Locale oldLanguage = Locale.getDefault();
	private boolean translationChanged = false;

	public I18NProjectTranslationPanel() {
		super();
		initializeDesktop();
	}

	private void initializeDesktop() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(5);
		borderLayout.setVgap(5);
		setLayout(borderLayout);

		localeChooser.addActionListener(this);
		classes.addListSelectionListener(this);

		localeChooser.setSelectedLocale(Locale.getDefault());
		localeChooser.setBorder(translationUpdater.i18n("Language", translator,
				localeChooser, languageBorder));
		add(localeChooser, BorderLayout.NORTH);

		classes.setBorder(translationUpdater.i18n("Classes", translator,
				classes, BorderFactory.createTitledBorder("")));
		add(new JScrollPane(classes), BorderLayout.WEST);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		reservoir.addListSelectionListener(this);

		reservoir.setBorder(translationUpdater.i18n("Reservoir", translator,
				reservoir, BorderFactory.createTitledBorder("")));
		centerPanel.add(new JScrollPane(reservoir));

		source.setEditable(false);
		source.setBorder(translationUpdater.i18n("Source", translator, source,
				BorderFactory.createTitledBorder("")));
		centerPanel.add(new JScrollPane(source));

		translation.setBorder(translationUpdater.i18n("Translation:",
				translator, translation, BorderFactory.createTitledBorder("")));
		centerPanel.add(new JScrollPane(translation));
		translation.addCaretListener(this);
		add(centerPanel, BorderLayout.CENTER);

		location.setEditable(false);
		location.setBorder(translationUpdater.i18n("Location(s):", translator,
				location, BorderFactory.createTitledBorder("")));
		centerPanel.add(new JScrollPane(location));

		add(new JLabel("Some I18NFile statistics"), BorderLayout.SOUTH);
		add(new JLabel("<html>Some project<br/>statistics</html>"),
				BorderLayout.EAST);
	}

	public boolean hasChanged() {
		return changed;
	}

	public boolean saveIfChanged() {
		updateHash();
		if (!hasChanged()) {
			return true;
		}
		int result = JOptionPane.showConfirmDialog(this,
				translator.i18n("Changes were made.\nDo you want to save?"),
				translator.i18n("Save"), JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.CANCEL_OPTION) {
			return false;
		}
		if (result == JOptionPane.NO_OPTION) {
			return true;
		}
		return saveFile();
	}

	public boolean saveFile() {
		if ((i18nFile != null) && (translationsHash != null)) {
			boolean result = I18NFile.write(i18nFile, translationsHash);
			if (result) {
				changed = false;
			}
			return result;
		}
		return true;
	}

	public void openDirectory(File directory) {
		try {
			if (!saveIfChanged()) {
				return;
			}
			I18NProjectConfiguration configuration = new I18NProjectConfiguration(
					directory);
			files.clear();
			files.addAll(FileSearch.find(configuration.getI18nDirectory(),
					"*.i18n"));
			Collections.sort(files);
			fileData.clear();
			Vector<String> listData = new Vector<String>();
			for (File file : files) {
				boolean finished = I18NFile.isFinished(new File(configuration
						.getI18nDirectory(), file.getPath()));
				String listEntry = "<html><body>";
				if (finished) {
					listEntry += "<font color=\"green\">";
					logger.debug("File '" + file.getPath()
							+ "' is already finished.");
				} else {
					listEntry += "<font color=\"red\">";
					logger.debug("File '" + file.getPath()
							+ "' is not finished, yet.");
				}
				listEntry += file.getPath();
				listEntry += "</font>";
				listEntry += "</body></html>";
				fileData.put(
						listEntry,
						new File(configuration.getI18nDirectory(), file
								.getPath()));
				listData.add(listEntry);
			}
			classes.setListData(listData);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
					translator.i18n("File was not found."),
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
					translator.i18n("IO error in file reading."),
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}

	public void openFile(Object file) {
		try {
			if (!saveIfChanged()) {
				return;
			}
			i18nFile = (File) file;
			setTranslationsHash(I18NFile.read(i18nFile));
			updateReservoir();
			changed = false;
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(this, translator.i18n(
					"The file {0} could not be read!", i18nFile), translator
					.i18n("File not found"), JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE);
			i18nFile = null;
		}
	}

	public void setTranslationsHash(MultiLanguageTranslations translationsHash) {
		this.translationsHash = translationsHash;
	}

	public void changeTranslation(String text) {
		translationChanged = true;
	}

	private void updateReservoir() {
		if (translationsHash == null) {
			return;
		}
		List<String> sources = new ArrayList<String>(
				translationsHash.getSources());
		Collections.sort(sources);
		reservoirData.clear();
		Vector<String> listData = new Vector<String>();
		boolean color = false;
		for (String source : sources) {
			boolean translated = translationsHash.getAvailableLanguages(source)
					.contains(localeChooser.getSelectedLocale());
			String html = "<html><body><br/>";
			if (translated) {
				html += "<font color=\"green\">";
			} else {
				html += "<font color=\"red\">";
			}
			html += source.replaceAll("\\n", "<br/>");
			html += "</font>";
			html += "<br/><br/><body></html>";
			reservoirData.put(html, source);
			listData.add(html);
			color = !color;
		}
		reservoir.setListData(listData);
	}

	public void changeSource(String source) {
		this.source.setText(source != null ? source.toString() : "");
		updateTranslation();
		updateLocation();
	}

	private void updateTranslation() {
		if (translationsHash == null) {
			translation.setText("");
			return;
		}
		if (source.getText().isEmpty()) {
			translation.setText("");
			return;
		}
		updateHash();
		oldSource = source.getText();
		oldLanguage = localeChooser.getSelectedLocale();
		oldTranslation = translationsHash.get(oldSource, oldLanguage);
		translation.setText(oldTranslation);
		translationChanged = false;
	}

	private void updateLocation() {
		if (translationsHash == null) {
			location.setText("");
			return;
		}
		if (source.getText().isEmpty()) {
			location.setText("");
			return;
		}
		StringBuffer locations = new StringBuffer();
		LanguageSet languageSet = translationsHash.get(source.getText());
		if (languageSet == null) {
			location.setText("");
			return;
		}
		for (SourceLocation location : languageSet.getLocations()) {
			locations.append(location.toString()).append("\n");
		}
		location.setText(locations.toString());
	}

	private void updateHash() {
		if ((!translation.getText().isEmpty()) && (translationChanged)) {
			if (!oldSource.isEmpty()) {
				translationsHash.set(oldSource, oldLanguage,
						translation.getText());
				changed = true;
				translationChanged = false;
			}
		}
	}

	public void removeWithoutLocation() {
		changeSource("");
		translationsHash.removeWithoutLocation();
		updateReservoir();
	}

	@Override
	public void actionPerformed(ActionEvent o) {
		if (o.getSource() == localeChooser) {
			updateTranslation();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent o) {
		if (o.getSource() == classes) {
			openFile(fileData.get(classes.getSelectedValue()));
		} else if (o.getSource() == this.reservoir) {
			changeSource(reservoirData.get(reservoir.getSelectedValue()));
		}
	}

	@Override
	public void caretUpdate(CaretEvent o) {
		if (o.getSource() == translation) {
			changeTranslation(translation.getText());
		}
	}
}
