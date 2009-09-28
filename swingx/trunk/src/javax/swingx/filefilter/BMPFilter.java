package javax.swingx.filefilter;

import java.io.Serializable;

import javax.i18n4j.Translator;

public class BMPFilter extends AbstractFileFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(BMPFilter.class);

	public String getDescription() {
		return translator.i18n("BMP images");
	}

	public String getSuffixes() {
		return ".bmp";
	}
}
