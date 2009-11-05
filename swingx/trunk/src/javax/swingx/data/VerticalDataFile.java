package javax.swingx.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

public class VerticalDataFile {

	private static final Logger logger = Logger
			.getLogger(VerticalDataFile.class);

	public static VerticalData createTable(RandomAccessFile f, String[] headers) {
		VerticalData data = new VerticalData();
		ValueType[] types = new ValueType[headers.length];
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
			while ((line = f.readLine()) != null) {
				lineNum++;
				if (line.contains("#")) {
					line = line.substring(0, line.indexOf('#'));
				}
				line = line.trim();
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
					// TODO proceed here...
				}
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
