package javax.i18n4j.apps;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Locale;

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

public class I18NLinguist extends Application {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(I18NLinguist.class);

	private I18NTranslationPanel translationPanel = null;

	public I18NLinguist() {
		super("I18NLinguist");

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
		Menu helpMenu = new Menu(translator.i18n("Help"));

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		MenuItem update = new MenuItem(translator.i18n("Update..."));
		update.connect("start", this, "update");

		MenuItem release = new MenuItem(translator.i18n("Release..."));
		release.connect("start", this, "release");

		MenuItem open = new MenuItem(translator.i18n("Open..."));
		open.connect("start", this, "open");

		MenuItem save = new MenuItem(translator.i18n("Save"));
		save.connect("start", this, "save");

		MenuItem exit = new MenuItem(translator.i18n("Quit"));
		exit.connect("start", this, "quit");

		fileMenu.add(update);
		fileMenu.add(release);
		fileMenu.addSeparator();
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.addSeparator();
		fileMenu.add(exit);

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
		if (isCorrectProjectDirectory(fileChooser.getSelectedFile())) {
			I18NUpdate update = new I18NUpdate(fileChooser.getSelectedFile()
					.getPath());
			update.update();
		}
	}

	@Slot
	public void release() {
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
		I18NRelease release = new I18NRelease(fileChooser.getSelectedFile()
				.getPath());
		release.release();
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
		if (isCorrectProjectDirectory(fileChooser.getSelectedFile())) {
			translationPanel.openDirectory(fileChooser.getSelectedFile());
		}
	}

	@Slot
	public void save() {
		if (!translationPanel.saveFile()) {
			JOptionPane.showConfirmDialog(Application.getInstance(), translator
					.i18n("I18NFile could not be saved!"), translator
					.i18n("Error"), JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean isCorrectProjectDirectory(File directory) {
		if (!I18NUpdate.isCorrectProjectDirectory(directory)) {
			JOptionPane
					.showConfirmDialog(
							Application.getInstance(),
							translator
									.i18n("Directory does not contain a weither a src or res directory!"),
							translator.i18n("Error"),
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	static public void main(String[] args) {
		Locale.setDefault(new Locale("de", "DE"));
		I18NLinguist app = new I18NLinguist();
		app.run();
	}
}
