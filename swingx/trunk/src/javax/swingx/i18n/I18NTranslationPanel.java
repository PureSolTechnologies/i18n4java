/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *
 ***************************************************************************/

package javax.swingx.i18n;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
import javax.swingx.FreeList;
import javax.swingx.Panel;
import javax.swingx.ScrollPane;
import javax.swingx.TextArea;
import javax.swingx.connect.Slot;

import org.apache.log4j.Logger;

public class I18NTranslationPanel extends Panel {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger
	    .getLogger(I18NTranslationPanel.class);
    private static final Translator translator = Translator
	    .getTranslator(I18NTranslationPanel.class);

    private File i18nFile = null;
    private FreeList classes = null;
    private List<File> files = null;
    private MultiLanguageTranslations translationsHash = null;
    private LanguageChooser locales = null;
    private Locale currentLocale = Locale.getDefault();
    private FreeList reservoir = null;
    private TextArea source = null;
    private TextArea translation = null;
    private TextArea location = null;
    private boolean changed = false;
    private String oldSource = "";
    private String oldTranslation = "";
    private String oldLanguage = currentLocale.getLanguage();
    private boolean translationChanged = false;

    public I18NTranslationPanel() {
	super();
	initializeDesktop();
    }

    private void initializeDesktop() {
	BorderLayout borderLayout = new BorderLayout();
	borderLayout.setHgap(5);
	borderLayout.setVgap(5);
	setLayout(borderLayout);

	locales = new LanguageChooser();
	locales.setSelectedLocale(currentLocale);
	locales.connect("changedSelection", this, "setLocale", Object.class);
	add(Label.addTo(new ScrollPane(locales), translator.i18n("Language"),
		Label.TOP), BorderLayout.NORTH);

	classes = new FreeList();
	classes.connect("valueChanged", this, "openFile", Object.class);
	add(Label.addTo(new ScrollPane(classes), translator.i18n("Classes"),
		Label.TOP), BorderLayout.WEST);

	Panel centerPanel = new Panel();
	centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

	reservoir = new FreeList();
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
	translation.connect("changeText", this, "changeTranslation",
		String.class);
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
		translator.i18n("Changes were made.\nDo you want to save?"),
		translator.i18n("Save"), JOptionPane.YES_NO_CANCEL_OPTION,
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
	    boolean result = I18NFile.write(i18nFile, translationsHash);
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
	files = FileSearch.find(directory, "res/**/*.i18n");
	Collections.sort(files);
	Hashtable<Object, Object> listData = new Hashtable<Object, Object>();
	for (File file : files) {
	    boolean finished = I18NFile.isFinished(file);
	    String listEntry = "<html><body>";
	    if (finished) {
		listEntry += "<font color=\"green\">";
		logger.debug("File '" + file.getPath()
			+ "' is already finished.");
	    } else {
		listEntry += "<font color=\"red\">";
		logger.debug("File '" + file.getPath()
			+ "' is not finished, yet.");
	    }
	    listEntry += file.getPath().substring(directory.getPath().length());
	    listEntry += "</font>";
	    listEntry += "</body></html>";
	    listData.put(listEntry, file);
	}
	classes.setListData(listData);
    }

    @Slot
    public void openFile(Object file) {
	if (!saveIfChanged()) {
	    return;
	}
	i18nFile = (File) file;
	try {
	    setTranslationsHash(I18NFile.read(i18nFile));
	    updateReservoir();
	    changed = false;
	} catch (FileNotFoundException e) {
	    JOptionPane.showConfirmDialog(Application.getInstance(), translator
		    .i18n("The file {0} could not be found!", i18nFile),
		    translator.i18n("File not found"), JOptionPane.OK_OPTION,
		    JOptionPane.ERROR_MESSAGE);
	    i18nFile = null;
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

    @Slot
    public void changeTranslation(String text) {
	translationChanged = true;
    }

    private void updateReservoir() {
	if (currentLocale == null) {
	    return;
	}
	if (translationsHash == null) {
	    return;
	}
	Set<String> sources = translationsHash.getSources();
	Hashtable<Object, Object> listData = new Hashtable<Object, Object>();
	boolean color = false;
	for (String source : sources) {
	    boolean translated = translationsHash.getAvailableLanguages(source)
		    .contains(currentLocale.getLanguage());
	    String html = "<html><body><br/>";
	    if (translated) {
		html += "<font color=\"green\">";
	    } else {
		html += "<font color=\"red\">";
	    }
	    html += source.replaceAll("\\n", "<br/>");
	    html += "</font>";
	    html += "<br/><br/><body></html>";
	    listData.put(html, source);
	    color = !color;
	}
	reservoir.setListData(listData);
    }

    @Slot
    public void changeSource(Object source) {
	if (source != null) {
	    this.source.setText(source.toString());
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
	oldSource = source.getText();
	oldLanguage = currentLocale.getLanguage();
	oldTranslation = translationsHash.get(oldSource, oldLanguage);
	translation.setText(oldTranslation);
	translationChanged = false;
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
	LanguageSet languageSet = translationsHash.get(source.getText());
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
	if ((!translation.getText().isEmpty()) && (translationChanged)) {
	    if ((!oldSource.isEmpty()) && (!oldLanguage.isEmpty())) {
		translationsHash.set(oldSource, oldLanguage, translation
			.getText());
		changed = true;
		translationChanged = false;
	    }
	}
    }

    public void removeWithoutLocation() {
	changeSource("");
	translationsHash.removeWithoutLocation();
	updateReservoir();
    }
}
