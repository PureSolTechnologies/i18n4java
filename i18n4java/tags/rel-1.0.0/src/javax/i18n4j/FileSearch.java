/***************************************************************************
 *
 *   FileSearch.java
 *   -------------------
 *   copyright            : (c) 2009 by Rick-Rainer Ludwig
 *   author               : Rick-Rainer Ludwig
 *   email                : rl719236@sourceforge.net
 *
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package javax.i18n4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

public class FileSearch {

	static public ArrayList<File> find(File directory, String pattern) {
		ArrayList<File> files = find(directory.getPath() + "/" + pattern);
		ArrayList<File> result = new ArrayList<File>(); 
		for (File file : files) {
			String fileString = file.getPath().substring(
					directory.getPath().length());
			result.add(new File(fileString));
		}
		return result;
	}

	/**
	 * This method scans a directory Apache Foundation like with path pattern
	 * /some/thing/here/ * * / *.xml.
	 * 
	 * @param pattern
	 *            a Apache like directory pattern.
	 * @return A Vector of found File classes is returned.
	 */
	static public ArrayList<File> find(String pattern) {
		ArrayList<File> files = new ArrayList<File>();
		File source = new File(pattern);
		if (source.isFile()) {
			files.add(source);
			return files;
		}
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
			pattern = pattern.replaceAll("\\*", "[^/]*");
			pattern = pattern.replaceAll("\\[\\^/\\]\\*\\[\\^/\\]\\*", ".*");
		} else {
			pattern += "/.*";
		}
		if (source.isDirectory()) {
			files.addAll(findFilesInDirectory(source, Pattern.compile(pattern),
					recursive));
		}
		return files;
	}

	static private Vector<File> findFilesInDirectory(File directory,
			Pattern pattern, boolean recursive) {
		Vector<File> files = new Vector<File>();
		for (String fileToCheck : directory.list()) {
			File file = new File(directory.getPath() + "/" + fileToCheck);
			if (file.isFile()) {
				if (pattern.matcher(file.getPath()).matches()) {
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
