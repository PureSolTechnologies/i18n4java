package javax.swingx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;
import javax.swingx.filefilter.TXTFilter;

public class SaveableTextArea extends TextArea implements Saveable {

	private static final long serialVersionUID = 4701684933678531691L;

	@Override
	public FileFilter[] getPossibleFileFilters() {
		return new FileFilter[] { new TXTFilter() };
	}

	@Override
	public void save(File file) throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(getText());
		} finally {
			writer.close();
		}
	}

}
