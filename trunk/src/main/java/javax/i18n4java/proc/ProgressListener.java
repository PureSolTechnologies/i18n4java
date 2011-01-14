package javax.i18n4java.proc;

/**
 * This interface is used for classes which want to be able to listen to
 * I18NRelease and I18NUpdate progresses.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public interface ProgressListener {

	/**
	 * This method is called in case of a new status.
	 * 
	 * @param min
	 * @param max
	 * @param current
	 * @param string
	 */
	public void progress(int min, int max, int current, String string);

}
