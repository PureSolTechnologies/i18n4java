package javax.swingx.filefilter;

import java.io.Serializable;

import javax.i18n4j.Translator;

public class JPEGFilter extends AbstractFileFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Translator translator = Translator
			.getTranslator(JPEGFilter.class);

	public String getDescription() {
		return translator.i18n("JPEG images");
	}

	@Override
	public String getSuffixes() {
		return ".jpeg,.jpg";
	}
}
