package javax.i18n4java.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileTreeModel implements TreeModel {

	private final List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
	private FileTree fileTree = null;

	public FileTreeModel() {
		super();
	}

	public FileTreeModel(List<File> files) {
		super();
		setFiles(files);
	}

	public void setFiles(List<File> files) {
		fileTree = new FileTree("/");
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
		// TODO
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
