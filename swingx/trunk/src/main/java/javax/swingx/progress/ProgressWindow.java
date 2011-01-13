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

package javax.swingx.progress;

import java.awt.Dimension;

import javax.i18n4java.Translator;
import javax.swing.BoxLayout;
import javax.swingx.Application;
import javax.swingx.Frame;
import javax.swingx.Panel;
import javax.swingx.ScrollPane;
import javax.swingx.connect.Signal;
import javax.swingx.connect.Slot;

/**
 * This is a dedicated window for showing a progress panel.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class ProgressWindow extends Frame {

	private static final long serialVersionUID = -5428306694138966408L;

	private static final Translator translator = Translator
			.getTranslator(ProgressWindow.class);

	private ProgressPanel progressPanel;
	private final RunnableProgressObservable observable;

	public ProgressWindow(RunnableProgressObservable observable) {
		super(translator.i18n("Progress..."));
		this.observable = observable;
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
		finished(observable);
		this.dispose();
	}

	@Signal
	public void finished() {
		emitSignal("finished");
	}

	@Signal
	public void finished(RunnableProgressObservable observable) {
		emitSignal("finished", observable);
	}
}
