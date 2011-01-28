package javax.i18n4java.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileTree implements Comparable<FileTree> {

	private final List<FileTree> children = new ArrayList<FileTree>();
	private final FileTree parent;
	private final String name;

	public FileTree(String file) {
		super();
		parent = null;
		this.name = file;
	}

	public FileTree(FileTree parent, String file) {
		super();
		this.parent = parent;
		this.name = file;
		parent.addChild(this);
	}

	private void addChild(FileTree child) {
		children.add(child);
		Collections.sort(children);
	}

	public FileTree getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public Object getChild(int id) {
		return children.get(id);
	}

	public int getChildCount() {
		return children.size();
	}

	public int getIndexOfChild(FileTree child) {
		return children.indexOf(child);
	}

	public boolean hashChildren() {
		return children.size() > 0;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean containsChild(String part) {
		for (FileTree child : children) {
			if (child.getName().equals(part)) {
				return true;
			}
		}
		return false;
	}

	public FileTree getChild(String part) {
		for (FileTree child : children) {
			if (child.getName().equals(part)) {
				return child;
			}
		}
		return null;
	}

	@Override
	public int compareTo(FileTree other) {
		if ((!this.hashChildren()) && (other.hashChildren())) {
			return 1;
		}
		if ((this.hashChildren()) && (!other.hashChildren())) {
			return -1;
		}
		return this.name.compareTo(other.name);
	}
}
