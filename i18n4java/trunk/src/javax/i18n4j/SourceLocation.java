/***************************************************************************
 *
 *   SourceLocation.java
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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class just keeps the information about a single location within a source
 * file. The location with the source is specified by a file and a line number.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
@XmlRootElement(name = "location", namespace = "http://ludwig.endofinternet.net")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceLocation implements Serializable, Comparable<SourceLocation> {

	private static final long serialVersionUID = 1L;

	private String file = "";
	private int line = -1;
	private int lineCount = 0;

	public SourceLocation() {
	}

	public SourceLocation(String file, int line) {
		this.file = file;
		this.line = line;
		this.lineCount = 1;
	}

	public SourceLocation(String file, int line, int lineCount) {
		this.file = file;
		this.line = line;
		this.lineCount = lineCount;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFile() {
		return file;
	}

	public void setLine(int line) {
		if (line < 1) {
			throw new IllegalArgumentException("Line has to be 1 or greater!");
		}
		this.line = line;
	}

	public int getLine() {
		return line;
	}

	public void setLineCount(int lineCount) {
		if (lineCount < 1) {
			throw new IllegalArgumentException(
					"Line count has to be 1 or greater!");
		}
		this.lineCount = lineCount;
	}

	public int getLineCount() {
		return lineCount;
	}

	public String toString() {
		if (lineCount == 1) {
			return file + ":" + line;
		} else {
			return file + ":" + line + "-" + (line + lineCount - 1);
		}
	}

	public int compareTo(SourceLocation other) {
		if (other == null) {
			return -1;
		}
		return toString().compareTo(other.toString());
	}

	public boolean equals(Object o) {
		if (getClass().equals(o.getClass())) {
			return false;
		}
		return toString().equals(((SourceLocation) o).toString());
	}
}
