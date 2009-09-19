package javax.i18n4j;

import java.io.File;
import java.util.Vector;
import java.util.regex.Pattern;

public class FileSearch {

	/**
	 * This method scans a directory Apache Foundation like with path pattern
	 * /some/thing/here/ * * / *.xml.
	 * 
	 * @param pattern
	 *            a Apache like directory pattern.
	 * @return A Vector of found File classes is returned.
	 */
	static public Vector<File> find(String pattern) {
		Vector<File> files = new Vector<File>();
		File source = new File(pattern);
		if (source.isFile()) {
			files.add(source);
		} else {
			boolean recursive = false;
			if (pattern.contains("*")) {
				pattern = source.getPath();
				if (pattern.contains("**")) {
					recursive = true;
				}
				String directory = pattern.replaceAll("\\*.*$", "");
				directory = directory.replaceAll("/[^/]*$", "");
				if (directory.isEmpty()) {
					directory = ".";
				}
				source = new File(directory);
				pattern = pattern.replaceAll("\\.", "\\\\.");
				pattern = pattern.replaceAll("\\*", ".*");
				pattern = pattern.replaceAll("/\\.\\*\\.\\*/", "/(.*/)*");
			} else {
				pattern += "/.*";
			}
			if (source.isDirectory()) {
				files.addAll(findFilesInDirectory(source, pattern, recursive));
			}
		}
		return files;
	}

	static private Vector<File> findFilesInDirectory(File directory,
			String pattern, boolean recursive) {
		Vector<File> files = new Vector<File>();
		String[] fileList = directory.list();
		for (int index = 0; index < fileList.length; index++) {
			File file = new File(directory.getPath() + "/" + fileList[index]);
			if (file.isFile()) {
				if (Pattern.matches(pattern, file.getPath())) {
					files.add(file);
				}
			} else if (file.isDirectory()) {
				if (recursive) {
					files
							.addAll(findFilesInDirectory(file, pattern,
									recursive));
				}
			}
		}
		return files;
	}
}
