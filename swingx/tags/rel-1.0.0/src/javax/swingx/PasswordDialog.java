package javax.swingx;

import java.io.IOException;

import javax.i18n4j.Translator;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swingx.connect.Slot;

/**
 * PasswordDialog is an standard object to present a password dialog. It is used
 * for all authentication processes with databases and within the user
 * administration.
 * 
 * @author Rick-Rainer Ludwig
 */
public final class PasswordDialog extends Dialog {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(PasswordDialog.class);

	private Label message = null;
	/**
	 * This JTextField holds the user name.
	 */
	private JTextField username = null;

	/**
	 * This JPasswordField holds the password. It is not shown and only
	 * represented as stars.
	 */
	private JPasswordField password = null;

	/**
	 * Ok is for starting the login process.
	 */
	private Button ok = null;

	/**
	 * Cancel is for interrupting the login process.
	 */
	private Button cancel = null;

	private boolean finishedByOk = false;

	/**
	 * This is the constructor for PasswordDialog.
	 * 
	 * @param owner
	 *            is the calling parent window. If the password dialog is to be
	 *            used during startup without a parent window, that null should
	 *            be used.
	 */
	public PasswordDialog() {
		super(translator.i18n("User Confirmation"), true);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(message = new Label(""));
		panel.add(new Label(translator.i18n("Username")));
		panel.add(username = new JTextField());
		panel.add(new Label(translator.i18n("Password")));
		panel.add(password = new JPasswordField());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(ok = new Button(translator.i18n("OK")));
		buttonPanel.add(cancel = new Button(translator.i18n("Cancel")));

		ok.connect("start", this, "quit");
		cancel.connect("start", this, "abort");

		panel.add(buttonPanel);
		setContentPane(panel);

		pack();
		getRootPane().setDefaultButton(ok);
	}

	public void setMessage(String message) {
		this.message.setText(message);
	}

	public String getMessage() {
		return message.getText();
	}

	public void setUsername(String username) {
		this.username.setText(username);
	}

	/**
	 * This method returns the set user name after the dialog was closed.
	 * 
	 * @return A string with the user name is returned.
	 * @throws IOException
	 *             is thrown in a case of IO error.
	 */
	public String getUsername() {
		if (!finishedByOk) {
			return "";
		}
		return username.getText();
	}

	/**
	 * This method returns the set password after the dialog was closed.
	 * 
	 * @return A string with the password is returned.
	 * @throws IOException
	 *             is thrown in a case of IO error.
	 */
	public String getPassword() {
		if (!finishedByOk) {
			return "";
		}
		return String.valueOf(password.getPassword());
	}

	public boolean run() {
		setVisible(true);
		return finishedByOk;
	}

	@Slot
	public void abort() {
		super.abort();
	}

	@Slot
	public void quit() {
		finishedByOk = true;
		super.quit();
	}
}
