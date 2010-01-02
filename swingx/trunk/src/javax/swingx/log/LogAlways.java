package javax.swingx.log;

import org.apache.log4j.Level;

/**
 * This LogLevel is defined just to introduce a new level which is always logged
 * for information, but is not to be suppressed like log settings changes, which
 * are useful to know for reading logs, but it should always be there.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class LogAlways extends Level {

	private static final long serialVersionUID = 1L;
	public static final Level LOG_ALWAYS = new LogAlways();

	protected LogAlways() {
		super(100000, "INFO!", 7);
	}

}
