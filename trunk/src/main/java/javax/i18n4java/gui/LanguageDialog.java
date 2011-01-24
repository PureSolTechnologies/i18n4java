package javax.i18n4java.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.i18n4java.LanguageChangeListener;
import javax.i18n4java.Translator;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class LanguageDialog extends JDialog implements
		LanguageChangeListener, ActionListener {

	private static final long serialVersionUID = -5526911970098174813L;

	private static final Translator translator = Translator
			.getTranslator(LanguageDialog.class);

	private final JLabel label = new JLabel(translator.i18n("Language:"));
	private final LanguageChooser languages = new LanguageChooser();
	private final JButton ok = new JButton(translator.i18n("OK"));
	private final JButton cancel = new JButton(translator.i18n("Cancel"));
	private final Locale startLocale;

	public LanguageDialog(JFrame frame) {
		super(frame, translator.i18n("Translation"), false);
		translator.addLanguageChangeListener(this);
		startLocale = Translator.getDefault();
		initUI();
	}

	private void initUI() {
		getRootPane().registerKeyboardAction(this, "ESC",
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JRootPane.WHEN_IN_FOCUSED_WINDOW);

		Container panel = getContentPane();
		panel.setLayout(new BorderLayout());

		JPanel languagePanel = new JPanel();
		languagePanel.setLayout(new BoxLayout(languagePanel,
				BoxLayout.PAGE_AXIS));

		languagePanel.add(label);
		languagePanel.add(languages);
		languages.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		buttonPanel.add(ok);
		ok.addActionListener(this);
		ok.setMnemonic('o');
		getRootPane().setDefaultButton(ok);

		buttonPanel.add(cancel);
		cancel.addActionListener(this);
		cancel.setMnemonic('c');

		panel.add(languagePanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}

	@Override
	public void translationChanged(Translator translator) {
		label.setText(translator.i18n("Language:"));
		ok.setText(translator.i18n("OK"));
		cancel.setText(translator.i18n("Cancel"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			setVisible(false);
		} else if (e.getSource() == cancel) {
			Translator.setDefault(startLocale);
			setVisible(false);
		} else if (e.getSource() == languages) {
			Translator.setDefault(languages.getSelectedLocale());
		} else if (e.getSource() == getRootPane()) {
			setVisible(false);
		}
	}

}
