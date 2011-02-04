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
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.Vector;

import javax.i18n4java.TranslationUpdater;
import javax.i18n4java.Translator;
import javax.i18n4java.data.I18NFile;
import javax.i18n4java.data.LanguageSet;
import javax.i18n4java.data.MultiLanguageTranslations;
import javax.i18n4java.data.SourceLocation;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FileTranslationPanel extends JPanel implements ActionListener,
		ListSelectionListener, CaretListener {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(FileTranslationPanel.class);

	private final TranslationUpdater translationUpdater = new TranslationUpdater();

	// GUI elements...
	private final ReservoirCellRenderer reservoirCellRenderer = new ReservoirCellRenderer();
	private final JList reservoir = new JList();
	private final JTextArea source = new JTextArea();
	private final JTextArea translation = new JTextArea();
	private final JTextArea location = new JTextArea();

	// fields...
	private File i18nFile = null;
	private MultiLanguageTranslations translationsHash = null;

	private boolean changed = false;
	private String oldSource = "";
	private String oldTranslation = "";
	private Locale oldLanguage = Locale.getDefault();
	private boolean translationChanged = false;
	private Locale selectedLocale = Locale.getDefault();

	public FileTranslationPanel() {
		super();
		initializeDesktop();
	}

	private void initializeDesktop() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(5);
		borderLayout.setVgap(5);
		setLayout(borderLayout);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		/* Reservoir */
		reservoir.setCellRenderer(reservoirCellRenderer);
		reservoir.setBorder(translationUpdater.i18n("Reservoir", translator,
				reservoir, BorderFactory.createTitledBorder("")));
		reservoir.addListSelectionListener(this);
		panel.add(new JScrollPane(reservoir));

		/* Source */
		source.setEditable(false);
		source.setBorder(translationUpdater.i18n("Source", translator, source,
				BorderFactory.createTitledBorder("")));
		panel.add(new JScrollPane(source));

		/* Translation */
		translation.setBorder(translationUpdater.i18n("Translation:",
				translator, translation, BorderFactory.createTitledBorder("")));
		translation.addCaretListener(this);
		panel.add(new JScrollPane(translation));

		/* Location */
		location.setEditable(false);
		location.setBorder(translationUpdater.i18n("Location(s):", translator,
				location, BorderFactory.createTitledBorder("")));
		panel.add(new JScrollPane(location));

		add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, new JLabel(
				"<html>Some file<br/>statistics</html>")), BorderLayout.CENTER);
	}

	/**
	 * @return the selectedLocale
	 */
	public Locale getSelectedLocale() {
		return selectedLocale;
	}

	/**
	 * @param selectedLocale
	 *            the selectedLocale to set
	 */
	public void setSelectedLocale(Locale selectedLocale) {
		this.selectedLocale = selectedLocale;
		reservoirCellRenderer.setSelectedLocale(selectedLocale);
		reservoir.repaint();
		updateTranslation();
	}

	public boolean hasChanged() {
		return changed;
	}

	/**
	 * This method checks whether the content of the file was changed or not. If
	 * a change was performed, the user is asked for saving the file or not. If
	 * the used chooses cancel the file is not saved and this method returns
	 * false to signal to abort the current process.
	 * 
	 * @return True is returned if the process was satisfied and the calling
	 *         method can proceed normally. False is returned in case the used
	 *         chose cancel to abort the current process.
	 * @throws IOException
	 */
	public boolean saveIfChanged() throws IOException {
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
		saveFile();
		return true;
	}

	public void saveFile() throws IOException {
		if ((i18nFile != null) && (translationsHash != null)) {
			I18NFile.write(i18nFile, translationsHash);
		}
	}

	public void openFile(File file) {
		try {
			if (!saveIfChanged()) {
				return;
			}
			i18nFile = file;
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
		Vector<String> listData = new Vector<String>(
				translationsHash.getSources());
		Collections.sort(listData);
		reservoirCellRenderer.setTranslationsHash(translationsHash);
		reservoir.setListData(listData);
	}

	public void setSource(String source) {
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
		oldLanguage = selectedLocale;
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

	public void removeObsoletePhrases() {
		setSource("");
		translationsHash.removeWithoutLocation();
		updateReservoir();
	}

	@Override
	public void actionPerformed(ActionEvent o) {
		if (o.getSource() == selectedLocale) {
			updateTranslation();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent o) {
		if (o.getSource() == this.reservoir) {
			setSource((String) reservoir.getSelectedValue());
		}
	}

	@Override
	public void caretUpdate(CaretEvent o) {
		if (o.getSource() == translation) {
			changeTranslation(translation.getText());
		}
	}
}
