package javax.swingx.progress;

import javax.i18n4j.Translator;
import javax.swing.BoxLayout;
import javax.swing.JProgressBar;
import javax.swingx.Button;
import javax.swingx.Frame;
import javax.swingx.Label;
import javax.swingx.Panel;
import javax.swingx.connect.Signal;
import javax.swingx.connect.Slot;

public class ProgressWindow extends Frame implements ProgressObserver {

    private static final long serialVersionUID = -5428306694138966408L;

    private static final Translator translator =
	    Translator.getTranslator(ProgressWindow.class);

    private Label label;
    private JProgressBar progressBar;
    private Button cancel;
    private ProgressObservable observable;
    private Thread thread = null;

    public ProgressWindow(ProgressObservable thread) {
	super(translator.i18n("Progress..."));
	this.observable = thread;
	observable.setMonitor(this);
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	initUI();
    }

    private void initUI() {
	Panel panel = new Panel();
	setContentPane(panel);
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	panel.add(label = new Label());
	panel.add(progressBar = new JProgressBar());
	panel.add(cancel = new Button(translator.i18n("Cancel")));
	cancel.connect("start", this, "cancel");
	pack();
    }

    @Override
    public void setRange(int min, int max) {
	progressBar.setMinimum(min);
	progressBar.setMaximum(max);
    }

    @Override
    public void setStatus(int status) {
	progressBar.setValue(status);
    }

    @Override
    public void setText(String text) {
	label.setText(text);
    }

    public void run() {
	setVisible(true);
	thread = new Thread(observable);
	thread.start();
    }

    @Slot
    public void cancel() {
	thread.interrupt();
	finish();
    }

    @Override
    public void finish() {
	finished();
	this.dispose();
    }

    @Signal
    public void finished() {
	emitSignal("finished");
    }

}
