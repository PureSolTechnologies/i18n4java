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
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.i18n4java.TranslationUpdater;
import javax.i18n4java.Translator;
import javax.i18n4java.proc.I18NProjectConfiguration;
import javax.i18n4java.utils.FileSearch;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.apache.log4j.Logger;

public class LanguageTranslationPanel extends JPanel implements
		TreeSelectionListener {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(LanguageTranslationPanel.class);
	private static final Translator translator = Translator
			.getTranslator(LanguageTranslationPanel.class);

	private final TranslationUpdater translationUpdater = new TranslationUpdater();

	// GUI elements...
	private final JTree fileTree = new JTree(new FileTreeModel());
	private final FileTreeCellRenderer fileTreeCellRenderer = new FileTreeCellRenderer();

	private final FileTranslationPanel fileTranslationPanel = new FileTranslationPanel();

	private I18NProjectConfiguration configuration = null;

	public LanguageTranslationPanel() {
		super();
		initializeDesktop();
	}

	private void initializeDesktop() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(5);
		borderLayout.setVgap(5);
		setLayout(borderLayout);

		fileTree.setCellRenderer(fileTreeCellRenderer);
		fileTree.setBorder(translationUpdater.i18n("Classes", translator,
				fileTree, BorderFactory.createTitledBorder("")));
		fileTree.addTreeSelectionListener(this);

		add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(
				fileTree), fileTranslationPanel));

		add(new JLabel("Some project statistics"), BorderLayout.SOUTH);
	}

	/**
	 * @param selectedLocale
	 *            the selectedLocale to set
	 */
	public void setSelectedLocale(Locale selectedLocale) {
		fileTranslationPanel.setSelectedLocale(selectedLocale);
		fileTreeCellRenderer.setSelectedLocale(selectedLocale);
		fileTree.repaint();
	}

	public boolean hasChanged() {
		return fileTranslationPanel.hasChanged();
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
		return fileTranslationPanel.saveIfChanged();
	}

	public void saveFile() throws IOException {
		fileTranslationPanel.saveFile();
	}

	public void openDirectory(File directory) {
		try {
			if (!saveIfChanged()) {
				return;
			}
			configuration = new I18NProjectConfiguration(directory);
			fileTreeCellRenderer.setConfiguration(configuration);

			List<File> files = FileSearch.find(
					configuration.getI18nDirectory(), "*.i18n");
			fileTree.setModel(new FileTreeModel(files));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
					translator.i18n("IO error in file reading."),
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}

	public void openFile(File file) {
		fileTranslationPanel.openFile(file);
	}

	public void removeObsoletePhrases() {
		fileTranslationPanel.removeObsoletePhrases();
	}

	@Override
	public void valueChanged(TreeSelectionEvent o) {
		if (o.getSource() == fileTree) {
			FileTree fileTree = (FileTree) o.getPath().getLastPathComponent();
			if (!fileTree.hashChildren()) {
				openFile(new File(configuration.getI18nDirectory(), fileTree
						.getFile().getPath()));
			}
		}
	}
}
