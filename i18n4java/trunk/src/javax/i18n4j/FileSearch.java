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
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class was implemented for recursive file search.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class FileSearch {

    public static String wildcardsToRegExp(String pattern) {
	pattern = pattern.replaceAll("\\.", "\\\\.");
	pattern = pattern.replaceAll("\\*", "[^/]*");
	pattern = pattern.replaceAll("\\?", ".");
	pattern = pattern.replaceAll("(\\[\\^/\\]\\*){2}", ".*");
	return pattern;
    }

    public static List<File> find(File directory, String pattern) {
	pattern = wildcardsToRegExp(pattern);
	List<File> files = findFilesInDirectory(directory, Pattern
		.compile(new File(directory, pattern).getPath()), true);
	List<File> result = new ArrayList<File>();
	for (File file : files) {
	    String fileString = file.getPath().substring(
		    directory.getPath().length());
	    result.add(new File(fileString));
	}
	return result;
    }

    /**
     * This class is the recursive part of the file search.
     * 
     * @param directory
     * @param pattern
     * @param scanRecursive
     * @return
     */
    private static List<File> findFilesInDirectory(File directory,
	    Pattern pattern, boolean scanRecursive) {
	List<File> files = new ArrayList<File>();
	String[] filesInDirectory = directory.list();
	if (filesInDirectory == null) {
	    return files;
	}
	for (String fileToCheck : filesInDirectory) {
	    File file = new File(directory, fileToCheck);
	    if (file.isFile()) {
		if (pattern.matcher(new File(directory, fileToCheck).getPath())
			.matches()) {
		    files.add(file);
		}
	    } else if (file.isDirectory()) {
		if (scanRecursive) {
		    files.addAll(findFilesInDirectory(file, pattern,
			    scanRecursive));
		}
	    }
	}
	return files;
    }
}
