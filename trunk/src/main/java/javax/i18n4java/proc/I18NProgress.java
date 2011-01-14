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
