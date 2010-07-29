/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
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
 * This class is designed as value object for thread safty.
 * 
 * This class is thread safe.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceLocation implements Cloneable, Serializable,
		Comparable<SourceLocation> {

	private static final long serialVersionUID = 1L;

	private String file;
	private int line;
	private int lineCount;

	public SourceLocation() {
	}

	public SourceLocation(SourceLocation sourceLocation) {
		this.file = sourceLocation.getFile();
		this.line = sourceLocation.getLine();
		this.lineCount = sourceLocation.getLineCount();
	}

	public SourceLocation(String file, int line) {
		this(file, line, 1);
	}

	public SourceLocation(String file, int line, int lineCount) {
		if (file == null) {
			throw new IllegalArgumentException("file must not be null!");
		}
		if (file.isEmpty()) {
			throw new IllegalArgumentException("file must not be empty!");
		}
		this.file = file;
		if (line < 1) {
			throw new IllegalArgumentException("Line has to be 1 or greater!");
		}
		this.line = line;
		if (lineCount < 1) {
			throw new IllegalArgumentException(
					"Line count has to be 1 or greater!");
		}
		this.lineCount = lineCount;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * @param line
	 *            the line to set
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * @param lineCount
	 *            the lineCount to set
	 */
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	public String getFile() {
		return file;
	}

	public int getLine() {
		return line;
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
	public SourceLocation clone() {
		return new SourceLocation(this);
	}

	@Override
	public int compareTo(SourceLocation other) {
		if (this == other)
			return 0;
		if (other == null)
			return -1;
		if (getClass() != other.getClass())
			return -1;
		if (!file.equals(other.file)) {
			return (file.compareTo(other.file) > 0 ? 1 : -1);
		}
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
		result = prime * result + file.hashCode();
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
		if (!file.equals(other.file))
			return false;
		if (line != other.line)
			return false;
		if (lineCount != other.lineCount)
			return false;
		return true;
	}
}
