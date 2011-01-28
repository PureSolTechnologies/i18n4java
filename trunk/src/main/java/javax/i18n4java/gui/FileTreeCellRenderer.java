package javax.i18n4java.gui;

import java.awt.Component;
import java.io.File;

import javax.i18n4java.data.I18NFile;
import javax.i18n4java.proc.I18NProjectConfiguration;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

public class FileTreeCellRenderer implements TreeCellRenderer {

	private I18NProjectConfiguration configuration = null;

	public FileTreeCellRenderer() {
	}

	public FileTreeCellRenderer(I18NProjectConfiguration configuration) {
		this.configuration = configuration;
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
		FileTree node = fileTree;
		String path = "";
		do {
			path = node.getName() + File.separator + path;
			node = node.getParent();
		} while (node != null);
		return new File(configuration.getI18nDirectory(), path);
	}

	private boolean isFinished(File file) {
		if (file.exists() && file.isFile()) {
			return I18NFile.isFinished(file);
		}
		return false;
	}

	private boolean isFinished(FileTree fileTree) {
		if (!fileTree.hashChildren()) {
			return I18NFile.isFinished(getFile(fileTree));
		}
		for (int id = 0; id < fileTree.getChildCount(); id++) {
			FileTree child = (FileTree) fileTree.getChild(id);
			if (!isFinished(child)) {
				return false;
			}
		}
		return true;
	}

}
