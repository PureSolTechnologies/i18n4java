package javax.i18n4java.proc;

import java.util.ArrayList;
import java.util.List;

public class I18NProgress {

	private final List<ProgressListener> progressListeners = new ArrayList<ProgressListener>();

	public void addProgressListener(ProgressListener listener) {
		progressListeners.add(listener);
	}

	public void removeProgressListener(ProgressListener listener) {
		progressListeners.remove(listener);
	}

	protected void progressUpdate(int min, int max, int current, String string) {
		for (ProgressListener listener : progressListeners) {
			listener.progress(min, max, current, string);
		}
	}
}
