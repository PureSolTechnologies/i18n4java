package javax.swingx.log;

import java.awt.BorderLayout;

import javax.swingx.BorderLayoutWidget;
import javax.swingx.Button;
import javax.swingx.Dialog;
import javax.swingx.ScrollPane;
import javax.swingx.TextArea;
import javax.swingx.connect.Slot;

/**
 * This class is an interactive logging dialog for Logger. This dialog is flying
 * over the application, so the application is free for use. The dialog shows
 * interactively the logs which newly appear. The log viewer is normally created
 * and view via Logger.showLogViewer
 * 
 * @see com.qsys.api.log.Logger#showLogViewer()
 * 
 * @author Rick-Rainer Ludwig
 */
public class LogViewer extends Dialog {

	static public void startNonModal() {
		new LogViewer().run();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This text area is the place to view the logs.
	 */
	private TextArea textArea;

	/**
	 * This button is for closing the dialog.
	 */
	private Button ok;

	/**
	 * This is the standard constructor. All initializations are performed here.
	 * 
	 */
	public LogViewer() {
		super();
		this.setTitle("Log Viewer");
		this.setModal(false);
	}

	protected void dialogInit() {
		super.dialogInit();
		BorderLayoutWidget pane = new BorderLayoutWidget();
		pane.setLayout(new BorderLayout());

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setViewportView(textArea = new TextArea());
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.add(scrollPane, BorderLayout.CENTER);

		ok = new Button("ok");
		pane.add(ok, BorderLayout.SOUTH);
		ok.connect("start", this, "quit");

		LogViewerAppender.getInstance().addViewer(this);

		setContentPane(pane);
	}

	@Slot
	public void addLog(String message) {
		if (message == null) {
			return;
		}
		if (textArea == null) {
			return;
		}
		textArea.append(message);
	}

	@Slot
	public void quit() {
		LogViewerAppender.getInstance().removeViewer(this);
		super.quit();
	}
}
