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
public class SourceLocation implements Cloneable, Serializable,
		Comparable<SourceLocation> {

	private static final long serialVersionUID = 1L;

	private String file = "";
	private int line = 0;
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

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int compareTo(SourceLocation other) {
		if (this == other)
			return 0;
		if (other == null)
			return -1;
		if (getClass() != other.getClass())
			return -1;
		if (file == null) {
			if (other.file != null)
				return 1;
		} else if (!file.equals(other.file))
			return file.compareTo(other.file);
		if (line != other.line) {
			if (line > other.line)
				return 1;
			else
				return -1;
		}
		if (lineCount != other.lineCount) {
			if (lineCount > other.lineCount)
				return 1;
			else
				return -1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + line;
		result = prime * result + lineCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SourceLocation other = (SourceLocation) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (line != other.line)
			return false;
		if (lineCount != other.lineCount)
			return false;
		return true;
	}
}
