package javax.swingx;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.i18n4j.Translator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swingx.config.APIConfig;
import javax.swingx.connect.Slot;

import org.apache.log4j.Logger;

public class Application extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(Application.class);
	private static final Translator translator = Translator
			.getTranslator(Application.class);

	/**
	 * This variable keeps the reference to translator.
	 */
	static private Application instance = null;

	static public Application getInstance() {
		return instance;
	}

	static public void showNotImplementedMessage() {
		JOptionPane.showMessageDialog(getInstance(), translator
				.i18n("This function is not implemented yet!"), translator
				.i18n("No Implementation"), JOptionPane.INFORMATION_MESSAGE
				| JOptionPane.OK_OPTION);
	}

	public Application(String title) {
		super(title);
		APIConfig.setApplicationName(title);
		setInstance();
		setClosingBehavior();
	}

	private synchronized void setInstance() {
		if (instance != null) {
			throw new RuntimeException("Application was started double!");
		}
		instance = this;
		logger.info("Application instance with name '" + getTitle() + "("
				+ getName() + ")' was registered.");
	}

	private void setClosingBehavior() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});
	}

	@Override
	public void run() {
		pack();
		logger.info("Starting application...");
		setVisible(true);
	}

	@Slot
	public Boolean quit() {
		logger.info("Quitting application...");
		dispose();
		return true;
	}
}
