package javax.i18n4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is for improving flexibility of the I18N framework. With special
 * 
 * @author rludwig
 * 
 */
public class I18NProjectConfiguration {

	public static final String CONFIGURATION_FILENAME = "i18n4java.properties";

	private static final String PROJECT_DIRECTORY_KEY = "i18n4java.project.directory";
	private static final String SOURCE_DIRECTORY_KEY = "i18n4java.project.directories.source";
	private static final String I18N_DIRECTORY_KEY = "i18n4java.project.directories.i18n";
	private static final String DESTINATION_DIRECTORY_KEY = "i18n4java.project.directories.destination";

	private final File projectDirectory;
	private final String relativeProjectTopDirectory;
	private final String relativeSourceDirectory;
	private final String relativeI18nDirectory;
	private final String relativeDestinationDirectory;

	/**
	 * 
	 * @param fileIOrDirectory
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public I18NProjectConfiguration(File fileOrDirectory)
			throws FileNotFoundException, IOException {
		super();
		if (!fileOrDirectory.exists()) {
			throw new FileNotFoundException("File or directory '"
					+ fileOrDirectory + "' is not found!");
		}
		File fileLocation;
		Properties props = new Properties();
		if (fileOrDirectory.isDirectory()) {
			props.load(new FileInputStream(new File(fileOrDirectory,
					CONFIGURATION_FILENAME)));
			fileLocation = fileOrDirectory;
		} else {
			props.load(new FileInputStream(fileOrDirectory));
			fileLocation = fileOrDirectory.getParentFile();
		}
		relativeProjectTopDirectory = props.contains(PROJECT_DIRECTORY_KEY) ? props
				.getProperty(PROJECT_DIRECTORY_KEY) : ".";
		relativeSourceDirectory = props.contains(SOURCE_DIRECTORY_KEY) ? props
				.getProperty(SOURCE_DIRECTORY_KEY) : "src";
		relativeI18nDirectory = props.contains(I18N_DIRECTORY_KEY) ? props
				.getProperty(I18N_DIRECTORY_KEY) : "i18n";
		relativeDestinationDirectory = props
				.contains(DESTINATION_DIRECTORY_KEY) ? props
				.getProperty(DESTINATION_DIRECTORY_KEY) : "res";
		projectDirectory = new File(fileLocation, relativeProjectTopDirectory);
	}

	/**
	 * @return the relativeProjectTopDirectory
	 */
	public String getRelativeProjectTopDirectory() {
		return relativeProjectTopDirectory;
	}

	/**
	 * @return the relativeSourceDirectory
	 */
	public String getRelativeSourceDirectory() {
		return relativeSourceDirectory;
	}

	/**
	 * @return the relativeI18nDirectory
	 */
	public String getRelativeI18nDirectory() {
		return relativeI18nDirectory;
	}

	/**
	 * @return the relativeDestinationDirectory
	 */
	public String getRelativeDestinationDirectory() {
		return relativeDestinationDirectory;
	}

	public File getProjectDirectory() {
		return projectDirectory;
	}

	public File getSourceDirectory() {
		return new File(projectDirectory, relativeSourceDirectory);
	}

	public File getI18nDirectory() {
		return new File(projectDirectory, relativeI18nDirectory);
	}

	public File getDestinationDirectory() {
		return new File(projectDirectory, relativeDestinationDirectory);
	}
}
