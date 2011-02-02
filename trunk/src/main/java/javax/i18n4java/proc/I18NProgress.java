/****************************************************************************
 *
 *   I18NProgress.java
 *   -------------------
 *   copyright            : (c) 2009-2011 by PureSol-Technologies
 *   author               : Rick-Rainer Ludwig
 *   email                : ludwig@puresol-technologies.com
 *
 ****************************************************************************/

/****************************************************************************
 *
 * Copyright 2009-2011 PureSol-Technologies
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
 ****************************************************************************/
 
package javax.i18n4java.proc;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the parent class for all classes within I18N4Java which are to
 * be monitored during their work. At the time of writing I18NRelesase and
 * I18NUpdate are using this.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class I18NProgress {

	private final List<ProgressListener> progressListeners = new ArrayList<ProgressListener>();

	/**
	 * Adds an progress listener.
	 * 
	 * @param listener
	 */
	public void addProgressListener(ProgressListener listener) {
		progressListeners.add(listener);
	}

	/**
	 * Removes a progress listener.
	 * 
	 * @param listener
	 */
	public void removeProgressListener(ProgressListener listener) {
		progressListeners.remove(listener);
	}

	/**
	 * This method is called to notify all observers about a new status.
	 * 
	 * @param min
	 * @param max
	 * @param current
	 * @param string
	 */
	protected void progressUpdate(int min, int max, int current, String string) {
		for (ProgressListener listener : progressListeners) {
			listener.progress(min, max, current, string);
		}
	}
}
