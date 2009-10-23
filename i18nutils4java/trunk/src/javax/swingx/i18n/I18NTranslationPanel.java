package javax.swingx.i18n;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import javax.i18n4j.FileSearch;
import javax.i18n4j.I18NFile;
import javax.i18n4j.LanguageSet;
import javax.i18n4j.MultiLanguageTranslations;
import javax.i18n4j.SourceLocation;
import javax.i18n4j.Translator;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swingx.Application;
import javax.swingx.Label;
import javax.swingx.List;
import javax.swingx.Panel;
import javax.swingx.ScrollPane;
import javax.swingx.TextArea;
import javax.swingx.connect.Slot;

public class I18NTranslationPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(I18NTranslationPanel.class);

	private File directory = null;
	private File i18nFile = null;
	private List classes = null;
	private Vector<File> files = null;
	private MultiLanguageTranslations translationsHash = null;
	private LocaleChooser locales = null;
	private Locale currentLocale = Locale.getDefault();
	private List reservoir = null;
	private TextArea source = null;
	private TextArea translation = null;
	private TextArea location = null;
	private boolean changed = false;
	private String oldSource = "";
	private String oldTranslation = "";
	private String oldLanguage = currentLocale.getLanguage();

	public I18NTranslationPanel() {
		super();
		initializeDesktop();
	}

	private void initializeDesktop() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(5);
		borderLayout.setVgap(5);
		setLayout(borderLayout);

		locales = new LocaleChooser();
		locales.setSelectedLocale(currentLocale);
		locales.connect("changedSelection", this, "setLocale", Object.class);
		add(Label.addTo(new ScrollPane(locales), translator.i18n("Language"),
				Label.TOP), BorderLayout.NORTH);

		classes = new List();
		classes.connect("valueChanged", this, "openFile", Object.class);
		add(Label.addTo(new ScrollPane(classes), translator.i18n("Classes"),
				Label.TOP), BorderLayout.WEST);

		Panel centerPanel = new Panel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		reservoir = new List();
		reservoir.connect("valueChanged", this, "changeSource", Object.class);

		centerPanel.add(Label.addTo(new ScrollPane(reservoir), translator
				.i18n("Reservoir"), Label.TOP));
		source = new TextArea();
		source.setEditable(false);
		centerPanel.add(Label.addTo(new ScrollPane(source), translator
				.i18n("Source:"), Label.TOP));
		translation = new TextArea();
		centerPanel.add(Label.addTo(new ScrollPane(translation), translator
				.i18n("Translation:"), Label.TOP));
		add(centerPanel, BorderLayout.CENTER);

		location = new TextArea();
		location.setEditable(false);
		centerPanel.add(Label.addTo(new ScrollPane(location), translator
				.i18n("Location(s)"), Label.TOP));

		add(new Label("Some I18NFile statistics"), BorderLayout.SOUTH);
		add(new Label("<html>Some project<br/>statistics</html>"),
				BorderLayout.EAST);
	}

	public boolean hasChanged() {
		return changed;
	}

	public boolean saveIfChanged() {
		updateHash();
		if (!hasChanged()) {
			return true;
		}
		int result = JOptionPane.showConfirmDialog(Application.getInstance(),
				translator.i18n("Changes were made. Save?"), translator
						.i18n("Save"), JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.CANCEL_OPTION) {
			return false;
		}
		if (result == JOptionPane.NO_OPTION) {
			return true;
		}
		return saveFile();
	}

	public boolean saveFile() {
		if ((i18nFile != null) && (translationsHash != null)) {
			boolean result = I18NFile.writeMultiLanguageFile(i18nFile,
					translationsHash);
			if (result) {
				changed = false;
			}
			return result;
		}
		return true;
	}

	public void openDirectory(File directory) {
		if (!saveIfChanged()) {
			return;
		}
		this.directory = directory;
		files = FileSearch.find(directory.getPath() + "/res/**/*.i18n");
		Vector<String> listData = new Vector<String>();
		for (File file : files) {
			listData
					.add(file.getPath().substring(directory.getPath().length()));
		}
		classes.setListData(listData);
	}

	@Slot
	public void openFile(Object file) {
		if (!saveIfChanged()) {
			return;
		}
		i18nFile = new File(directory + "/" + file);
		try {
			setTranslationsHash(I18NFile.readMultiLanguageFile(i18nFile));
			updateReservoir();
			changed = false;
		} catch (FileNotFoundException e) {
			i18nFile = null;
			JOptionPane.showConfirmDialog(Application.getInstance(), translator
					.i18n("The file {0} could not be found!", i18nFile),
					translator.i18n("File not found"), JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setTranslationsHash(MultiLanguageTranslations translationsHash) {
		this.translationsHash = translationsHash;
	}

	@Slot
	public void setLocale(Object o) {
		currentLocale = locales.getSelectedLocale();
		updateTranslation();
	}

	private void updateReservoir() {
		if (currentLocale == null) {
			return;
		}
		if (translationsHash == null) {
			return;
		}
		Set<String> sources = translationsHash.getSources();
		reservoir.setListData(new Vector<String>(sources));
	}

	@Slot
	public void changeSource(Object source) {
		if (source != null) {
			this.source.setText(source.toString().replaceAll("\\\\n", "\n"));
		} else {
			this.source.setText("");
		}
		updateTranslation();
		updateLocation();
	}

	private void updateTranslation() {
		if (currentLocale == null) {
			translation.setText("");
			return;
		}
		if (translationsHash == null) {
			translation.setText("");
			return;
		}
		if (source.getText().isEmpty()) {
			translation.setText("");
			return;
		}
		updateHash();
		oldSource = source.getText().replaceAll("\\n", "\\\\n");
		oldLanguage = currentLocale.getLanguage();
		oldTranslation = translationsHash.get(oldSource, oldLanguage);
		translation.setText(oldTranslation.replaceAll("\\\\n", "\n"));
	}

	private void updateLocation() {
		if (currentLocale == null) {
			location.setText("");
			return;
		}
		if (translationsHash == null) {
			location.setText("");
			return;
		}
		if (source.getText().isEmpty()) {
			location.setText("");
			return;
		}
		StringBuffer locations = new StringBuffer();
		LanguageSet languageSet = translationsHash.get(source.getText()
				.replaceAll("\\n", "\\\\n"));
		if (languageSet == null) {
			location.setText("");
			return;
		}
		for (SourceLocation location : languageSet.getLocations()) {
			locations.append(location.toString()).append("\n");
		}
		location.setText(locations.toString());
	}

	private void updateHash() {
		if ((!oldTranslation.equals(translation.getText().replaceAll("\\n",
				"\\\\n")))
				&& (!translation.getText().isEmpty())) {
			if ((!oldSource.isEmpty()) && (!oldLanguage.isEmpty())) {
				translationsHash.set(oldSource, oldLanguage, translation
						.getText().replaceAll("\\n", "\\\\n"));
				changed = true;
			}
		}
	}

	public void removeWithoutLocation() {
		changeSource("");
		translationsHash.removeWithoutLocation();
		updateReservoir();
	}
}
