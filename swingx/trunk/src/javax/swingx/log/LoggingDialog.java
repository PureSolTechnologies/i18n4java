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

package javax.swingx.log;

import java.awt.BorderLayout;

import javax.i18n4java.Translator;
import javax.swingx.ComboBox;
import javax.swingx.Dialog;
import javax.swingx.Label;
import javax.swingx.connect.Slot;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This dialog is for setting the logging level during runtime. More detailed
 * runtime settings for logging might be implemented later.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class LoggingDialog extends Dialog {

	private static final long serialVersionUID = 4122721639814570183L;

	private static final Logger logger = Logger.getLogger(LoggingDialog.class);
	private static final Translator translator = Translator
			.getTranslator(LoggingDialog.class);

	public LoggingDialog() {
		super(translator.i18n("Configure Logging"), false);
		initUI();
	}

	private void initUI() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(10);
		borderLayout.setVgap(10);
		setLayout(borderLayout);
		add(getDefaultOKButton(), BorderLayout.SOUTH);
		ComboBox logLevels = new ComboBox();
		logLevels.addItem(Level.TRACE);
		logLevels.addItem(Level.DEBUG);
		logLevels.addItem(Level.INFO);
		logLevels.addItem(Level.WARN);
		logLevels.addItem(Level.ERROR);
		logLevels.addItem(Level.FATAL);
		logLevels.setSelectedItem(Logger.getRootLogger().getLevel());
		logLevels.connect("changedSelection", this, "changeLogLevel",
				Object.class);
		add(logLevels, BorderLayout.CENTER);
		add(new Label(translator.i18n("Set log level:")), BorderLayout.NORTH);
		pack();
	}

	@Slot
	public void changeLogLevel(Object o) {
		Level level = (Level) o;
		if (!level.equals(Logger.getRootLogger().getLevel())) {
			logger.log(LogAlways.LOG_ALWAYS, "Changed log level to '" + level
					+ "'!");
			Logger.getRootLogger().setLevel(level);
		}
	}

	public static void main(String[] args) {
		new LoggingDialog().run();
	}
}
