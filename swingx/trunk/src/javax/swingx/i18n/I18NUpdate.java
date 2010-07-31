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

package javax.swingx.i18n;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.i18n4java.ProgressListener;
import javax.swingx.progress.ProgressObservable;
import javax.swingx.progress.ProgressObserver;

/**
 * This applications reads files and directories given by command line and
 * search the found files for strings to be translated. This tools is similar to
 * Trolltech's lupdate.
 * 
 * @author Rick-Rainer Ludwig
 */
public class I18NUpdate extends javax.i18n4java.I18NUpdate implements
		ProgressObservable, ProgressListener {

	private ProgressObserver observer = null;

	public I18NUpdate(File projectDirectory) throws FileNotFoundException,
			IOException {
		super(projectDirectory);
		addProgressListener(this);
	}

	@Override
	public void run() {
		super.update();
		if (observer != null) {
			observer.finish();
		}
	}

	@Override
	public void setMonitor(ProgressObserver observer) {
		this.observer = observer;
	}

	@Override
	public void progress(int min, int max, int current, String string) {
		if (observer != null) {
			observer.setRange(min, max);
			observer.setStatus(current);
			observer.setText(string);
		}
	}
}
