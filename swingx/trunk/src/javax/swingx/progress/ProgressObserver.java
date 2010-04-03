package javax.swingx.progress;

/**
 * This interface is implemented into all classes which are used to observe
 * a progress of another thread or process.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public interface ProgressObserver {

    /**
     * This method sets some description output for the currently running
     * process.
     * 
     * @param description
     */
    public void setDescription(String description);

    /**
     * This method sets the range for the progress bar.
     * 
     * @param min
     * @param max
     */
    public void setRange(int min, int max);

    /**
     * This method sets the value for the status bar.
     * 
     * @param status
     */
    public void setStatus(int status);

    /**
     * This method sets the text for a dynamic text outside the progress
     * bar.
     * 
     * @param text
     */
    public void setText(String text);

    /**
     * This method sets the text within the progress bar.
     * 
     * @param text
     */
    public void setProgressText(String text);

    /**
     * This methods makes the observer showing the percentage within the
     * progress bar.
     */
    public void showProgressPercent();

    /**
     * This method is called when the process is finished to finish the
     * progress bar and to trigger post-process activities of the observer.
     */
    public void finish();

    /**
     * 
     */
    public ProgressObserver startSubProgress(ProgressObservable thread);
}
