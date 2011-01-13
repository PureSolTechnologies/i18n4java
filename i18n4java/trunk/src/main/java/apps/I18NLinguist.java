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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import javax.i18n4java.Translator;
import javax.i18n4java.gui.I18NTranslationPanel;
import javax.i18n4java.proc.I18NRelease;
import javax.i18n4java.proc.I18NUpdate;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

public class I18NLinguist extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(I18NLinguist.class);

	private final JMenuItem update = new JMenuItem(translator.i18n("Update..."));
	private final JMenuItem release = new JMenuItem(
			translator.i18n("Release..."));
	private final JMenuItem clear = new JMenuItem(translator.i18n("Clear"));
	private final JMenuItem open = new JMenuItem(translator.i18n("Open..."));
	private final JMenuItem save = new JMenuItem(translator.i18n("Save"));
	private final JMenuItem exit = new JMenuItem(translator.i18n("Quit"));
	private final JMenuItem about = new JMenuItem(translator.i18n("About..."));

	private final JButton openButton = new JButton(translator.i18n("Open..."));
	private final JButton updateButton = new JButton(
			translator.i18n("Update..."));
	private final JButton releaseButton = new JButton(
			translator.i18n("Release..."));
	private final JButton clearButton = new JButton(translator.i18n("Clear"));

	private I18NTranslationPanel translationPanel = null;

	public I18NLinguist() {
		super("I18NLinguist v0.1.0");

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		initializeVariables();
		initializeMenu();
		initializeDesktop();
		initializeToolBar();
	}

	private void initializeVariables() {

	}

	private void initializeMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu(translator.i18n("File"));
		JMenu toolsMenu = new JMenu(translator.i18n("Tools"));
		JMenu helpMenu = new JMenu(translator.i18n("Help"));

		menuBar.add(fileMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);

		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		toolsMenu.add(update);
		toolsMenu.add(release);
		toolsMenu.add(clear);

		helpMenu.add(about);

		update.addActionListener(this);
		release.addActionListener(this);
		clear.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);
		about.addActionListener(this);
	}

	private void initializeDesktop() {
		setLayout(new BorderLayout());
		add(translationPanel = new I18NTranslationPanel());
	}

	private void initializeToolBar() {
		JToolBar tools = new JToolBar();
		tools.add(openButton);
		tools.add(updateButton);
		tools.add(releaseButton);
		tools.add(clearButton);
		add(tools, BorderLayout.NORTH);

		openButton.addActionListener(this);
		updateButton.addActionListener(this);
		releaseButton.addActionListener(this);
		clearButton.addActionListener(this);
	}

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

	public void clear() {
		translationPanel.removeWithoutLocation();
	}

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

	public void save() {
		if (!translationPanel.saveFile()) {
			JOptionPane.showConfirmDialog(this,
					translator.i18n("I18NFile could not be saved!"),
					translator.i18n("Error"), JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void about() {
		JOptionPane.showMessageDialog(this,
				translator.i18n("About message..."), translator.i18n("About"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent o) {
		if ((o.getSource() == update) || (o.getSource() == updateButton)) {
			update();
		} else if ((o.getSource() == release)
				|| (o.getSource() == releaseButton)) {
			release();
		} else if ((o.getSource() == clear) || (o.getSource() == clearButton)) {
			clear();
		} else if ((o.getSource() == open) || (o.getSource() == openButton)) {
			open();
		} else if (o.getSource() == save) {
			save();
		} else if (o.getSource() == exit) {
			dispose();
		} else if (o.getSource() == about) {
			about();
		}
	}

	static public void main(String[] args) {
		Locale.setDefault(new Locale("de", "DE"));
		Translator.setDefault(Locale.getDefault());
		I18NLinguist app = new I18NLinguist();
		app.setVisible(true);
	}
}
