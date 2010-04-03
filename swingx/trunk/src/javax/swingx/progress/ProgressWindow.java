package javax.swingx.progress;

import java.awt.Dimension;

import javax.i18n4j.Translator;
import javax.swing.BoxLayout;
import javax.swingx.Application;
import javax.swingx.Frame;
import javax.swingx.Panel;
import javax.swingx.ScrollPane;
import javax.swingx.connect.Signal;
import javax.swingx.connect.Slot;

public class ProgressWindow extends Frame {

	private static final long serialVersionUID = -5428306694138966408L;

	private static final Translator translator = Translator
			.getTranslator(ProgressWindow.class);

	private ProgressPanel progressPanel;
	private ProgressObservable observable;

	public ProgressWindow(ProgressObservable thread) {
		super(translator.i18n("Progress..."));
		this.observable = thread;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initUI();
	}

	private void initUI() {
		Panel panel = new Panel();
		setContentPane(panel);
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);

		progressPanel = new ProgressPanel(observable);
		panel.add(new ScrollPane(progressPanel));
		progressPanel.connect("finished", this, "finishedPanel");

		setMinimumSize(new Dimension(400, 100));
		pack();
	}

	public void pack() {
		super.pack();
		if (Application.getInstance() != null) {
			setLocationRelativeTo(Application.getInstance());
		}
	}

	public void run() {
		setVisible(true);
		progressPanel.run();
	}

	@Slot
	public void finishedPanel() {
		finished();
		this.dispose();
	}

	@Signal
	public void finished() {
		emitSignal("finished");
	}
}
