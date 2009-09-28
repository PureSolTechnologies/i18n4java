package javax.swingx;

import java.awt.BorderLayout;
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

	protected BorderLayoutWidget borderLayoutWidget = null;

	protected MenuBar menuBar = null;

	/**
	 * This variable keeps the reference to translator.
	 */
	static private Application instance = null;

	public Application(String title) {
		super(title);
		APIConfig.setApplicationName(title);
		setInstance();
		setClosingBehavior();
		setViewDefaults();
		setClassVariables();
	}

	private synchronized void setInstance() {
		if (instance != null) {
			throw new RuntimeException("Application was started double!");
		}
		instance = this;
		logger.info("Application instance with name '" + getName()
				+ "' was registered.");
	}

	private void setClosingBehavior() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});
	}

	private void setViewDefaults() {
		borderLayoutWidget = new BorderLayoutWidget();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(borderLayoutWidget, BorderLayout.CENTER);
	}

	private void setClassVariables() {
	}

	static public Application getInstance() {
		return instance;
	}

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

	/**
	 * This method is called if any widget has changed. The reference of the
	 * changed widget is transmitted as parameter.
	 * 
	 * @param widget
	 *            is the reference to the currently updated widget.
	 */
	public void widgetChanged(Widget widget) {
		/*
		 * There is nothing to do in an empty application. This method can be
		 * overridden in inheriting classes.
		 */
	}

	static public void showNotImplementedMessage() {
		JOptionPane.showMessageDialog(getInstance(), translator
				.i18n("This function is not implemented yet!"), translator
				.i18n("No Implementation"), JOptionPane.INFORMATION_MESSAGE
				| JOptionPane.OK_OPTION);
	}
}
