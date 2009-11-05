package javax.swingx.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

public class VerticalDataFile {

	private static final Logger logger = Logger
			.getLogger(VerticalDataFile.class);

	public static String readLine(RandomAccessFile f) {
		try {
			String line;
			line = f.readLine();
			if (line != null) {
				if (line.contains("#")) {
					line = line.substring(0, line.indexOf('#'));
				}
				line = line.trim();
			}
			return line;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static VerticalData createTable(RandomAccessFile f, String[] headers) {
		int width = headers.length;
		String line = "";
		VerticalData data = new VerticalData();
		ValueType[] types = new ValueType[headers.length];
		for (int index = 0; index < types.length; index++) {
			types[index] = ValueType.fromClass(Boolean.class);
		}
		while ((line = readLine(f)) != null) {
			String[] splits = line.split("\t");
			if (splits.length != width) {
				logger.warn("Table width not constant! Abort parsing!");
				return null;
			}
			for (int index = 0; index < width; index++) {
				ValueType newType = ValueType
						.recognizeFromString(splits[index]);
				if (types[index].compareTo(newType) < 0) {
					types[index] = newType;
				}
			}
		}
		for (int index = 0; index < types.length; index++) {
			data.addColumn(headers[index], types[index]);
		}
		return data;
	}

	public static VerticalData read(File file) {
		RandomAccessFile f = null;
		try {
			VerticalData data = null;
			f = new RandomAccessFile(file, "r");
			String line = "";
			boolean isHeader = true;
			int cols = 0;
			int lineNum = 0;
			while ((line = readLine(f)) != null) {
				lineNum++;
				if (line.isEmpty()) {
					continue;
				}
				String[] splits = line.split("\t");
				if (cols == 0) {
					cols = splits.length;
				} else if (cols != splits.length) {
					throw new IOException("Wrong number of columns in line "
							+ lineNum);
				}
				if (isHeader) {
					long pointer = f.getFilePointer();
					data = createTable(f, splits);
					f.seek(pointer);
					isHeader = false;
					continue;
				}
				// TODO do normal reading here!!!
			}
			f.close();
			return data;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		if (f != null) {
			try {
				f.close();
			} catch (IOException e) {
				// nothing to catch...
			}
		}
		return null;
	}
}
