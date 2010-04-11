package javax.swingx.progress;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.i18n4j.Translator;
import javax.swing.BoxLayout;
import javax.swing.JProgressBar;
import javax.swingx.Button;
import javax.swingx.Label;
import javax.swingx.Panel;
import javax.swingx.connect.Signal;
import javax.swingx.connect.Slot;

public class ProgressPanel extends Panel implements ProgressObserver {

	private static final long serialVersionUID = -5428306694138966408L;

	private static final Translator translator = Translator
			.getTranslator(ProgressPanel.class);

	private Label description;
	private Label label;
	private JProgressBar progressBar;
	private Button cancel;
	private Thread thread = null;
	private boolean ownThreadFinished = false;
	private final ProgressObservable observable;
	private final ArrayList<ProgressPanel> subProgressPanels = new ArrayList<ProgressPanel>();

	public ProgressPanel(ProgressObservable thread) {
		super();
		this.observable = thread;
		thread.setMonitor(this);
		initUI();
	}

	private void initUI() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		add(description = new Label());
		add(label = new Label());
		add(progressBar = new JProgressBar());
		add(cancel = new Button(translator.i18n("Cancel")));
		cancel.connect("start", this, "cancel");
		progressBar.setStringPainted(true);
		progressBar.setString(null);
		setMinimumSize(new Dimension(400, 100));
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
		ownThreadFinished = true;
		finish();
	}

	@Override
	public void finish() {
		ownThreadFinished = true;
		if (isFinished()) {
			sendFinishedSignal();
		}
	}

	private void sendFinishedSignal() {
		finished();
		finished(this);
		finished(observable);
	}

	@Signal
	public void finished() {
		connectionManager.emitSignal("finished");
	}

	@Signal
	public void finished(ProgressPanel progressPanel) {
		connectionManager.emitSignal("finished", progressPanel);
	}

	@Signal
	public void finished(ProgressObservable progressObservable) {
		connectionManager.emitSignal("finished", progressObservable);
	}

	public boolean isFinished() {
		return (ownThreadFinished && (subProgressPanels.size() == 0));
	}

	@Override
	public void setDescription(String description) {
		this.description.setText(description);
	}

	@Override
	public void setProgressText(String text) {
		progressBar.setStringPainted(true);
		progressBar.setString(text);
	}

	@Override
	public void showProgressPercent() {
		progressBar.setStringPainted(true);
		progressBar.setString(null);
	}

	@Override
	public ProgressObserver startSubProgress(ProgressObservable thread) {
		ProgressPanel subProgressPanel = new ProgressPanel(thread);
		subProgressPanels.add(subProgressPanel);
		add(subProgressPanel);
		subProgressPanel.connect("finished", this, "finishedSubPanel",
				ProgressPanel.class);
		subProgressPanel.run();
		return null;
	}

	@Slot
	public void finishedSubPanel(ProgressPanel progressPanel) {
		remove(progressPanel);
		repaint();
		subProgressPanels.remove(progressPanel);
		if (isFinished()) {
			sendFinishedSignal();
		}
	}

}
