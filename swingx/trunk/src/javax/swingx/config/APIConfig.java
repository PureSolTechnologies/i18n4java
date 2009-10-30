package javax.swingx.config;

/**
 * This object contains API constants and configuration settings used for API
 * wide settings and behavior storage.
 * 
 * @author Rick-Rainer Ludwig
 */
public class APIConfig {

	/**
	 * This is the version of the whole API package. The idea is:
	 * major.minor.fix<br/>
	 * 
	 * major = change if compatibility is not granted or a major update was
	 * released
	 * 
	 * minor = update and functional enhancement and improvement, but
	 * compatibility is granted
	 * 
	 * fix = bug fix release with no functional changes, but bugs are fixed
	 */
	public static final String PACKAGE_VERSION = Configurator.getEntry(
			"/config/about", "GENERAL", "version", true);

	/**
	 * This is the constants containing the year range of development. The
	 * starting point was January 2009.
	 */
	public static final String PACKAGE_YEARS = Configurator.getEntry(
			"/config/about", "GENERAL", "years", true);

	/**
	 * These are all developers which were working on this release as architects
	 * and leaders. The number of programmers would exceed this variable very
	 * fast.
	 */
	public static final String PACKAGE_AUTHOR = Configurator.getEntry(
			"/config/about", "GENERAL", "author", true);

	/**
	 * This is the email address used for bug reports. This is shown to
	 * customers to have an email contact.
	 */
	public static final String PACKAGE_BUGREPORT = Configurator.getEntry(
			"/config/about", "GENERAL", "bugreport", true);

	/**
	 * This is the copyright message for about boxes.
	 */
	public static final String PACKAGE_COPYRIGHT = Configurator.getEntry(
			"/config/about", "GENERAL", "copyright", true);

	/**
	 * This is the legal owner of the software package.
	 */
	public static final String PACKAGE_OWNER = Configurator.getEntry(
			"/config/about", "GENERAL", "owner", true);

	/**
	 * This variable is for global usage and is used to keep the help request
	 * information. This variable is set by RTAParser for instance.
	 */
	private static int help = 0;

	/**
	 * This variable is for global usage and is used to keep the version request
	 * information. This variable is set by RTAParser for instance.
	 */
	private static int version = 0;

	/**
	 * This variable contains the name of the application which was set for
	 * copyright messages and other information messages.
	 */
	private static String applicationName = "";

	/**
	 * This method sets the application name which is to be shown in copyright
	 * messages.
	 * 
	 * @param name
	 *            is the name of the application.
	 * @throws IllegalArgumentException
	 *             is thrown if name is null or empty.
	 */
	public static void setApplicationName(String name)
			throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("name must not be null!");
		}
		if (name.isEmpty()) {
			throw new IllegalArgumentException(
					"The default value for this name is empty, but if "
							+ "this method is called a real name has to"
							+ " be set. Empty is illegal!");
		}
		applicationName = name;
	}

	/**
	 * This method returns the set application name.
	 * 
	 * @return A String is returned containing the application name.
	 */
	public static String getApplicationName() {
		return applicationName;
	}

	/**
	 * This method sets the help request flag of the API.
	 * 
	 * @param helpRequest
	 *            is the value to set for the help request flag. 0 means no help
	 *            screen is requested. Otherwise a help screen is requested and
	 *            to show in the console or a special window.
	 * @throws IllegalArgumentException
	 *             is thrown if helpRequest is less than zero.
	 */
	public static void setHelpRequest(int helpRequest)
			throws IllegalArgumentException {
		if (helpRequest < 0) {
			throw new IllegalArgumentException(
					"helpRequest is alwayse greater than or equal to zero!");
		}
		help = helpRequest;
	}

	/**
	 * This method returns the currently set help request flag.
	 * 
	 * @return The currentely set help request flag is returned. 0 means no help
	 *         screen is requested. Otherwise a help screen is requested and to
	 *         show in the console or a special window.
	 */
	public static int getHelpRequest() {
		return help;
	}

	/**
	 * This method sets the version request flag of the API.
	 * 
	 * @param versionRequest
	 *            is the value to set for the version request flag. 0 means no
	 *            version screen is requested. Otherwise a version screen is
	 *            requested and to show in the console or a special window.
	 * @throws IllegalArgumentException
	 *             is thrown if verionRequest is less than zero.
	 */
	public static void setVersionRequest(int versionRequest)
			throws IllegalArgumentException {
		if (versionRequest < 0) {
			throw new IllegalArgumentException(
					"versionRequest is alwayse greater than or equal to zero!");
		}
		version = versionRequest;
	}

	/**
	 * This method returns the currently set version request flag.
	 * 
	 * @return The currently set version request flag is returned. 0 means no
	 *         version screen is requested. Otherwise a version screen is
	 *         requested and to show in the console or a special window.
	 */
	public static int getVersionRequest() {
		return version;
	}

	static private String createAboutMessage(String file, String section) {
		String message = ConfigFile.readSection(file, section);
		message = message.replaceAll("%APPLICATION%", getApplicationName());
		message = message.replaceAll("%VERSION%", PACKAGE_VERSION);
		message = message.replaceAll("%YEARS%", PACKAGE_YEARS);
		message = message.replaceAll("%OWNER%", PACKAGE_OWNER);
		message = message.replaceAll("%AUTHOR%", PACKAGE_AUTHOR);
		message = message.replaceAll("%COPYRIGHT%", PACKAGE_COPYRIGHT);
		message = message.replaceAll("%BUGREPORT%", PACKAGE_BUGREPORT);
		return message;
	}

	/**
	 * This method generates the default copyright message of this API in HTML
	 * code.
	 * 
	 * @return A String containing the copyright information is returned in HTML
	 *         code.
	 */
	public static String getCopyrightMessage() {
		return createAboutMessage("config/about", "COPYRIGHT");
	}

	/**
	 * This method generates the default vendor information of this API in HTML
	 * code.
	 * 
	 * @return A String containing the vendor information is returned in HTML
	 *         code.
	 */
	public static String getVendorInformation() {
		return createAboutMessage("config/about", "VENDOR");
	}

	/**
	 * This method generates the default contact information of this API in HTML
	 * code.
	 * 
	 * @return A String containing the contact information is returned in HTML
	 *         code.
	 */
	public static String getContactInformation() {
		return createAboutMessage("config/about", "CONTACT");
	}
}
