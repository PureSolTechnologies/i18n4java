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

package apps;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import javax.i18n4j.I18NRelease;
import javax.i18n4j.I18NUpdate;
import javax.i18n4j.Translator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swingx.Application;
import javax.swingx.Button;
import javax.swingx.Menu;
import javax.swingx.MenuBar;
import javax.swingx.MenuItem;
import javax.swingx.ToolBar;
import javax.swingx.connect.Slot;
import javax.swingx.i18n.I18NTranslationPanel;
import javax.swingx.progress.SplashWindow;

public class I18NLinguist extends Application {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(I18NLinguist.class);

	private static SplashWindow splash = null;

	private I18NTranslationPanel translationPanel = null;

	public I18NLinguist() {
		super("I18NLinguist", "v0.1.0");

		initializeVariables();
		initializeMenu();
		initializeDesktop();
		initializeToolBar();
	}

	private void initializeVariables() {

	}

	private void initializeMenu() {
		MenuBar menuBar = new MenuBar();
		setJMenuBar(menuBar);

		Menu fileMenu = new Menu(translator.i18n("File"));
		Menu toolsMenu = new Menu(translator.i18n("Tools"));
		Menu helpMenu = new Menu(translator.i18n("Help"));

		menuBar.add(fileMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);

		MenuItem update = new MenuItem(translator.i18n("Update..."));
		update.connect("start", this, "update");

		MenuItem release = new MenuItem(translator.i18n("Release..."));
		release.connect("start", this, "release");

		MenuItem clear = new MenuItem(translator.i18n("Clear"));
		clear.connect("start", this, "clear");

		MenuItem open = new MenuItem(translator.i18n("Open..."));
		open.connect("start", this, "open");

		MenuItem save = new MenuItem(translator.i18n("Save"));
		save.connect("start", this, "save");

		MenuItem exit = new MenuItem(translator.i18n("Quit"));
		exit.connect("start", this, "quit");

		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		toolsMenu.add(update);
		toolsMenu.add(release);
		toolsMenu.add(clear);

		helpMenu.addDefaultAboutItem();
	}

	private void initializeDesktop() {
		setLayout(new BorderLayout());
		add(translationPanel = new I18NTranslationPanel());
	}

	private void initializeToolBar() {
		ToolBar tools = new ToolBar();
		Button open = new Button(translator.i18n("Open..."));
		open.connect("start", this, "open");
		Button update = new Button(translator.i18n("Update..."));
		update.connect("start", this, "update");
		Button release = new Button(translator.i18n("Release..."));
		release.connect("start", this, "release");
		tools.add(open);
		tools.add(update);
		tools.add(release);
		add(tools, BorderLayout.NORTH);
	}

	@Slot
	public void update() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				return;
			}
			if (result == JFileChooser.ERROR_OPTION) {
				JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
						translator.i18n("Error while choosing file."),
						JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			I18NUpdate update = new I18NUpdate(fileChooser.getSelectedFile());
			update.update();
		} catch (FileNotFoundException e) {
			JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
					translator.i18n("File was not found."),
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
					translator.i18n("IO error in file reading."),
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}

	@Slot
	public void release() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.CANCEL_OPTION) {
				return;
			}
			if (result == JFileChooser.ERROR_OPTION) {
				JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
						translator.i18n("Error while choosing file."),
						JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			I18NRelease release = new I18NRelease(fileChooser.getSelectedFile());
			release.release();
		} catch (FileNotFoundException e) {
			JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
					translator.i18n("File was not found."),
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
					translator.i18n("IO error in file reading."),
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
		}
	}

	@Slot
	public void clear() {
		translationPanel.removeWithoutLocation();
	}

	@Slot
	public void open() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}
		if (result == JFileChooser.ERROR_OPTION) {
			JOptionPane.showConfirmDialog(this, translator.i18n("Error"),
					translator.i18n("Error while choosing file."),
					JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
			return;
		}
		translationPanel.openDirectory(fileChooser.getSelectedFile());
	}

	@Slot
	public void save() {
		if (!translationPanel.saveFile()) {
			JOptionPane.showConfirmDialog(Application.getInstance(),
					translator.i18n("I18NFile could not be saved!"),
					translator.i18n("Error"), JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	static public void main(String[] args) {
		splash = new SplashWindow(
				I18NLinguist.class.getResource("/splashtest.jpg"), 400, 300);
		splash.run();
		Locale.setDefault(new Locale("de", "DE"));
		Translator.setDefault(Locale.getDefault());
		I18NLinguist app = new I18NLinguist();
		splash.dispose();
		app.run();
	}
}
