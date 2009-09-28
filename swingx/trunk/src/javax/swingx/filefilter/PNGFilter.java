package javax.swingx.filefilter;

import java.io.Serializable;

import javax.i18n4j.Translator;

public class PNGFilter extends AbstractFileFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(PNGFilter.class);

	public String getDescription() {
		return translator.i18n("PNG images");
	}

	public String getSuffixes() {
		return ".png";
	}
}
