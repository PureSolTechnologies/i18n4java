package javax.swingx.config;

import java.io.IOException;
import java.io.OutputStream;

public class ConfigOutputStream extends OutputStream {

	private OutputStream outStream;

	public ConfigOutputStream(OutputStream outStream) {
		super();
		this.outStream = outStream;
	}

	@Override
	public void write(int b) throws IOException {
		outStream.write(b);
	}

	public void writeSection(String section) throws IOException {
		write("[" + section + "]\n");
	}

	public void writeEntry(String key, String value) throws IOException {
		write(key + "=" + value + "\n");
	}

	public void write(String text) throws IOException {
		write(text.getBytes());
	}
}
