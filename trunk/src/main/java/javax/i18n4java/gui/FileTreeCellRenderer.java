/****************************************************************************
 *
 *   FileTreeCellRenderer.java
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

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.i18n4java.data.I18NFile;
import javax.i18n4java.proc.I18NProjectConfiguration;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import org.apache.log4j.Logger;

public class FileTreeCellRenderer implements TreeCellRenderer {

	private static final Logger logger = Logger
			.getLogger(FileTreeCellRenderer.class);

	private I18NProjectConfiguration configuration = null;
	private Locale selectedLocale = Locale.getDefault();

	public FileTreeCellRenderer() {
		super();
	}

	public FileTreeCellRenderer(Locale locale) {
		super();
		this.selectedLocale = locale;
	}

	public FileTreeCellRenderer(I18NProjectConfiguration configuration) {
		super();
		this.configuration = configuration;
	}

	public FileTreeCellRenderer(I18NProjectConfiguration configuration,
			Locale locale) {
		super();
		this.configuration = configuration;
		this.selectedLocale = locale;
	}

	/**
	 * @return the configuration
	 */
	public I18NProjectConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(I18NProjectConfiguration configuration) {
		this.configuration = configuration;
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
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		FileTree fileTree = (FileTree) value;

		File file = getFile(fileTree);

		boolean finished = false;
		if (leaf) {
			finished = isFinished(file);
		} else {
			finished = isFinished(fileTree);
		}

		String text = ((FileTree) value).getName();
		if (leaf) {
			text += "/";
		}
		return new StatusComponent(text, selected, hasFocus, finished);
	}

	private File getFile(FileTree fileTree) {
		return new File(configuration.getI18nDirectory(), fileTree.getFile()
				.getPath());
	}

	private boolean isFinished(FileTree fileTree) {
		if (!fileTree.hashChildren()) {
			return isFinished(getFile(fileTree));
		}
		for (int id = 0; id < fileTree.getChildCount(); id++) {
			FileTree child = (FileTree) fileTree.getChild(id);
			if (!isFinished(child)) {
				return false;
			}
		}
		return true;
	}

	private boolean isFinished(File file) {
		try {
			if (file.exists() && file.isFile()) {
				if (!I18NFile.read(file).getAvailableLanguages()
						.contains(selectedLocale)) {
					return false;
				}
				return I18NFile.isFinished(file, selectedLocale);
			}
			return false;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

}
