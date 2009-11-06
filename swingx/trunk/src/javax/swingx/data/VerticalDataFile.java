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
				while (line.endsWith("\n") || line.endsWith("\r")) {
					line = line.substring(0, line.length() - 1);
				}
			}
			return line;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private static VerticalData createTable(RandomAccessFile f, String[] headers) {
		int width = headers.length;
		String line = "";
		VerticalData data = new VerticalData();
		ValueType[] types = new ValueType[headers.length];
		for (int index = 0; index < types.length; index++) {
			types[index] = ValueType
					.fromClass(TypeWrapper.PRIMITIVE_WRAPPERS[0]);
		}
		while ((line = readLine(f)) != null) {
			logger.debug(line);
			String[] splits = line.split("\t");
			for (int index = 0; index < (width < splits.length ? width
					: splits.length); index++) {
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
			int lineNum = 0;
			String[] cols = null;
			int width = 0;
			while ((line = readLine(f)) != null) {
				lineNum++;
				if (line.isEmpty()) {
					continue;
				}
				String[] splits = line.split("\t");
				if (isHeader) {
					long pointer = f.getFilePointer();
					data = createTable(f, splits);
					f.seek(pointer);
					isHeader = false;
					width = data.getColumnNumber();
					cols = new String[width];
					continue;
				}
				for (int index = 0; index < width; index++) {
					if (splits.length > index) {
						cols[index] = splits[index];
					} else {
						cols[index] = "";
					}
				}
				data.addRow((Object[]) cols);
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

	public static void write(File file, VerticalData data) {
		try {
			RandomAccessFile f = new RandomAccessFile(file, "rw");
			boolean first = true;
			for (String columnName : data.columnNames) {
				if (first) {
					first = false;
				} else {
					f.writeBytes("\t");
				}
				f.writeBytes(columnName);
			}
			f.writeBytes("\n");
			for (int row = 0; row < data.getRowNumber(); row++) {
				first = true;
				for (int col = 0; col < data.getColumnNumber(); col++) {
					if (first) {
						first = false;
					} else {
						f.writeBytes("\t");
					}
					f.writeBytes(data.getString(row, col));
				}
				f.writeBytes("\n");
			}
			f.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		VerticalData data = VerticalDataFile.read(new File(
				"/home/ludwig/DIAGS.txt"));
		data.println();
		VerticalDataFile.write(new File("/home/ludwig/test.csv"), data);
	}
}
