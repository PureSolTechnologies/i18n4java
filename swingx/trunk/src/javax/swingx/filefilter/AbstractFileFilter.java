package javax.swingx.filefilter;

import java.io.File;
import java.io.Serializable;

import javax.swing.filechooser.FileFilter;

/**
 * This is an abstract implementation of an enhance file filter for
 * application's file open dialogs. Additionally, the check for correct
 * suffixes, the appendation of suffixes and the search for primary suffix are
 * integrated which were not implemented in FileFilter.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
abstract public class AbstractFileFilter extends FileFilter implements
		Serializable {

	private static final long serialVersionUID = 1L;

	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		return hasCorrectSuffix(f);
	}

	public boolean hasCorrectSuffix(File f) {
		String[] extensions = getSuffixes().split(",");
		for (int index = 0; index < extensions.length; index++) {
			if (f.getName().toLowerCase().endsWith(extensions[index])) {
				return true;
			}
		}
		return false;
	}

	public String getFirstSuffix() {
		return getSuffixes().split(",")[0];
	}

	public File appendSuffix(File file) {
		if (hasCorrectSuffix(file)) {
			return file;
		}
		return new File(file.getPath() + getFirstSuffix());
	}

	abstract public String getSuffixes();

	abstract public String getDescription();
}
