/****************************************************************************
 *
 *   FileTreeModel.java
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

package javax.i18n4java.linguist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.i18n4java.data.I18NFile;
import javax.i18n4java.proc.I18NProjectConfiguration;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

class FileTreeModel implements TreeModel {

	/**
	 * This method is static to express the functional character of the method
	 * and to avoid accidental usage of non static fields.
	 * 
	 * @param fileTree
	 * @param locale
	 * @return
	 * @throws IOException
	 */
	private static boolean isFinished(FileTree fileTree, Locale locale)
			throws IOException {
		if (!fileTree.hashChildren()) {
			return isFinished(fileTree.getFile(), locale);
		}
		for (int id = 0; id < fileTree.getChildCount(); id++) {
			FileTree child = (FileTree) fileTree.getChild(id);
			if (!isFinished(child, locale)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method is static to express the functional character of the method
	 * and to avoid accidental usage of non static fields.
	 * 
	 * @param file
	 * @param locale
	 * @return
	 * @throws IOException
	 */
	private static boolean isFinished(File file, Locale locale)
			throws IOException {
		if (file.exists() && file.isFile()) {
			if (!I18NFile.read(file).getAvailableLanguages().contains(locale)) {
				return false;
			}
			return I18NFile.isFinished(file, locale);
		}
		return false;
	}

	/**
	 * This method is static to express the functional character of the method
	 * and to avoid accidental usage of non static fields.
	 * 
	 * @param currentNode
	 */
	private static void setFinishedFlagsForDirectories(FileTree currentNode) {
		if (!currentNode.hashChildren()) {
			return;
		}
		boolean finished = true;
		for (int index = 0; index < currentNode.getChildCount(); index++) {
			FileTree child = currentNode.getChild(index);
			setFinishedFlagsForDirectories(child);
			if (!child.isFinished()) {
				finished = false;
			}
		}
		currentNode.setFinished(finished);
	}

	/**
	 * This method is static to express the functional character of the method
	 * and to avoid accidental usage of non static fields.
	 * 
	 * @param currentNode
	 * @param locale
	 * @throws IOException
	 */
	private static void setFinishedFlagsForFiles(FileTree currentNode,
			Locale locale) throws IOException {
		if (!currentNode.hashChildren()) {
			currentNode.setFinished(isFinished(currentNode, locale));
			return;
		}
		for (int index = 0; index < currentNode.getChildCount(); index++) {
			setFinishedFlagsForFiles(currentNode.getChild(index), locale);
		}
	}

	/*
	 * **********************************************************************
	 * Non static part
	 * **********************************************************************
	 */

	private final List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
	private Locale selectedLocale = Locale.getDefault();
	private FileTree fileTree = null;
	private I18NProjectConfiguration configuration = null;

	public FileTreeModel() {
		super();
	}

	public FileTreeModel(List<File> files,
			I18NProjectConfiguration configuration) throws IOException {
		super();
		this.configuration = configuration;
		setFiles(files, configuration);
	}

	public Locale getSelectedLocale() {
		return selectedLocale;
	}

	public void setSelectedLocale(Locale selectedLocale) throws IOException {
		this.selectedLocale = selectedLocale;
		setFinishedFlags();
		fireStructureChanged();
	}

	public I18NProjectConfiguration getConfiguration() {
		return configuration;
	}

	public void setFiles(List<File> files,
			I18NProjectConfiguration configuration) throws IOException {
		this.configuration = configuration;
		setFileTree(files);
		setFinishedFlags();
		fireStructureChanged();
	}

	private void setFileTree(List<File> files) {
		fileTree = new FileTree(configuration.getI18nDirectory().getPath()
				+ "/");
		for (File file : files) {
			List<String> parts = new ArrayList<String>();
			File currentFile = file;
			do {
				parts.add(0, currentFile.getName());
				currentFile = currentFile.getParentFile();
			} while (currentFile != null);
			FileTree currentNode = fileTree;
			for (String part : parts) {
				if (part.isEmpty()) {
					continue;
				}
				if (currentNode.containsChild(part)) {
					currentNode = currentNode.getChild(part);
				} else {
					currentNode = new FileTree(currentNode, part);
				}
			}
		}
	}

	private void setFinishedFlags() throws IOException {
		setFinishedFlagsForFiles(fileTree, selectedLocale);
		setFinishedFlagsForDirectories(fileTree);
	}

	private void fireStructureChanged() {
		for (TreeModelListener listener : listeners) {
			listener.treeStructureChanged(new TreeModelEvent(this,
					new TreePath(fileTree)));
		}
	}

	public void changedFile(File file) throws IOException {
		boolean finished = isFinished(file, selectedLocale);
		FileTree fileTreeElement = fileTree.getFileTreeElement(file);
		fileTreeElement.setFinished(finished);
		setFinishedFlags();
		fireStructureChanged();
	}

	@Override
	public void addTreeModelListener(TreeModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Object getChild(Object parent, int id) {
		return ((FileTree) parent).getChild(id);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((FileTree) parent).getChildCount();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return ((FileTree) parent).getIndexOfChild((FileTree) child);
	}

	@Override
	public Object getRoot() {
		return fileTree;
	}

	@Override
	public boolean isLeaf(Object child) {
		return !((FileTree) child).hashChildren();
	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
		throw new RuntimeException("Not implemented and supported!");
	}

	/**
	 * This method is for testing purposes only.
	 * 
	 * @return
	 */
	Object getFileTree() {
		return fileTree;
	}

}
