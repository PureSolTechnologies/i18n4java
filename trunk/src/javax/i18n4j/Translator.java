package javax.i18n4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * <PRE>
 * 
 * This small object was inspired by QT's approach to internationalization which
 * is much more flexible and much easier to use. This object was to be created
 * because the String object is final and a tr() function could not be
 * implemented in that way. The Translator object can be create several times.
 * The text reference list is static and is therefore loaded only once into the
 * memory. Additionally, the object is designed as singleton to save memory.
 * &lt;br/&gt; &lt;br/&gt; The translation file is a normal ASCII file with the suffix
 * '.&lt;language&gt;.tr'. For german it would be '.de.tr'. The format is very
 * easy. An entry starts with a line starting with '&gt;&gt;&gt;'. In the next
 * lines the English text which is used in the program is presented. The next
 * line is stating with '===' and after that the translation is presented. It is
 * to pay attention to the format of the text. The last line of an entry is just
 * starting with '&lt;&lt;&lt;'. &lt;br/&gt; &lt;br/&gt; example:&lt;br/&gt; &lt;br/&gt; &lt;tt&gt;
 * &lt;&lt;&lt;&lt;br/&gt;
 * English text&lt;br/&gt;
 * ===&lt;br/&gt;
 * Deutscher Text&lt;br/&gt;
 * &gt;&gt;&gt;
 * &lt;/tt&gt;
 * 
 * </PRE>
 * 
 * @author Rick-Rainer Ludwig
 */
public class Translator implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * This is the Hashtable for the context translations. Everything is kept in
	 * there. The context is the name of the class including the package name.
	 */
	private MultiLanguageTranslations translations = null;

	private static Locale defaultLocale = null;

	private static Locale[] additionalLocales = null;
	private static Logger logger = Logger.getLogger(Translator.class);

	/**
	 * This variable keeps the reference to the system wide unique instance.
	 */
	private static Hashtable<String, Translator> instance = new Hashtable<String, Translator>();
	private String context = null;

	/**
	 * This is the standard constructor which performs some default
	 * initializations if needed. This constructor is private to assure the
	 * Singleton pattern. It would not be wise to have a lot of Translator
	 * instances. The memory could be filled heavily if we would hold a lot of
	 * Translator objects and the time for reading the translation files
	 * multiple times is also not to be forgotten.
	 * 
	 * @param context
	 *            is the objects context for what the translator is to be
	 *            created.
	 */
	private Translator(String context) {
		defaultLocale = Locale.getDefault();
		this.context = context;
		readContextTranslation();
	}

	static public void setDefault(Locale locale) {
		Translator.defaultLocale = locale;
	}

	public Locale getDefault() {
		return defaultLocale;
	}

	public String getDefaultLanguage() {
		return defaultLocale.getLanguage();
	}

	public String getDefaultCountry() {
		return defaultLocale.getCountry();
	}

	public void setSingleLanguageMode() {
		additionalLocales = null;
	}

	static public void setAdditionalLocales(Locale... additionalLocales) {
		Translator.additionalLocales = additionalLocales;
	}

	public Locale[] getAdditionalLocales() {
		return additionalLocales;
	}

	public void setTranslation(String source, String language,
			String translation) {
		translations.set(source, language, translation);
	}

	public void addTranslations(MultiLanguageTranslations translations) {
		this.translations.add(translations);
	}

	public void setTranslation(String source, Locale locale, String translation) {
		setTranslation(source, locale.getLanguage(), translation);
	}

	public MultiLanguageTranslations getTranslationsHash() {
		return translations;
	}

	private void readContextTranslation() {
		if (getDefaultLanguage().equals("en")) {
			this.translations = new MultiLanguageTranslations();
		}
		File file = I18NFile.getI18NFile(context, getDefault());
		InputStream is = this.getClass().getResourceAsStream(file.getPath());
		MultiLanguageTranslations translations;
		try {
			translations = I18NFile.readWithJAXB(is);
			translations.print();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			logger.warn("Could not read the translation '" + file.getPath()
					+ "' for context " + context + "!");
			translations = new MultiLanguageTranslations();
		}
		this.translations = translations;
	}

	public String i18n(String text) {
		String translation = translations.get(text, getDefaultLanguage());
		if (additionalLocales != null) {
			for (int index = 0; index < additionalLocales.length; index++) {
				translation += "\n("
						+ translations.get(text, additionalLocales[index]
								.getLanguage()) + ")";
			}
		}
		return translation;
	}

	/**
	 * {@inheritDoc}
	 */
	public String i18n(String text, Object... params) {
		String translation = new MessageFormat(translations.get(text,
				getDefaultLanguage()), getDefault()).format(params);
		if (additionalLocales != null) {
			for (int index = 0; index < additionalLocales.length; index++) {
				translation += "\n("
						+ new MessageFormat(translations.get(text,
								additionalLocales[index].getLanguage()),
								additionalLocales[index]).format(params) + ")";
			}
		}
		return translation;
	}

	/**
	 * This method creates a new instance of Translator. This method should only
	 * be used, if it is really necessary. getInstance() should be used instead.
	 * 
	 * @see #getTranslator()
	 * 
	 * @return A reference to a newly created Translator object is returned.
	 */
	public static Translator newInstance(String context) {
		return new Translator(context);
	}

	/**
	 * This method creates a the unique instance of Translator for Singleton
	 * pattern. This method is synchronized to assure only one instance. This
	 * method is exclusively called by getInstance to create the unique instance
	 * in case it was not created before.
	 * 
	 * @see #getTranslator()
	 * 
	 * @return A reference to a newly created Translator object is returned.
	 */
	private static synchronized Translator createInstance(String context) {
		if (!instance.containsKey(context)) {
			instance.put(context, newInstance(context));
		}
		return instance.get(context);
	}

	/**
	 * This method returns the static instance to Translator which is unique to
	 * the whole system. This is the static method to build the Singleton
	 * pattern.
	 * 
	 * @return A reference to the static held Translator object is returned.
	 */
	public static Translator getTranslator(Class<?> context) {
		Translator translator = (Translator) instance.get(context.getName());
		if (translator == null) {
			translator = createInstance(context.getName());
		}
		return translator;
	}
}
