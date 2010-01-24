package javax.swingx.config;

import java.io.IOException;

/**
 * This exception is thrown in a case of any failure of the configuration
 * handling sub system.
 * 
 * @author Rick-Rainer Ludwig
 */
public class ConfigException extends IOException {

	private static final long serialVersionUID = 1L;

	/**
	 * This is the standard constructor if ConfigException. A message is given
	 * for the error message.
	 * 
	 * @param message
	 *            is a String containing the error message.
	 */
	public ConfigException(String message) {
		super(message);
	}
}
