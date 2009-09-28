package javax.swingx.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

/**
 * This object handles the connectivity to all configuration files and assures,
 * that all configuration files are only read once and that the information is
 * statically stored in memory for usage of all other objects.
 * 
 * Reading configuration files is very time consuming and this reading should be
 * performed only when it's needed and only once per file! For an efficient
 * handling of the data this object is designed as a singleton pattern.
 * Therefore, only one instance is kept in the memory and all configuration
 * files read with this object are only read once and only one copy of the date
 * is stored in memory.
 * 
 * @author Rick-Rainer Ludwig
 */
public class Configurator {

	private static final Logger logger = Logger.getLogger(Configurator.class);

	/**
	 * This is the private instance variable for the one created instance.
	 */
	private static Configurator instance;

	/**
	 * This static variable keeps all information of all configuration files.
	 * This variable is static because of the wish, that each configuration file
	 * is only read once. Reading files is very time consuming.
	 */
	private static volatile Hashtable<String, ConfigHash> configuratorHash = new Hashtable<String, ConfigHash>();

	/**
	 * This is a standard configurator with default values. It's declared as
	 * private for the singleton pattern to be used.
	 */
	private Configurator() {
	}

	/**
	 * This method is used internally in the case the configuration file needed
	 * was not loaded yet. This method uses the ConfigFile obeject to read the
	 * whole file into a ConfigHash.
	 * 
	 * @param file
	 *            is the short file path.
	 * @throws ConfigException
	 *             is thrown in case of config read file failures.
	 */
	private void read(String file) throws ConfigException {
		readAll(file);
	}

	/**
	 * This method is used internally in the case the configuration file needed
	 * was not loaded yet. This method uses the ConfigFile obeject to read the
	 * whole file into a ConfigHash.
	 * 
	 * @param file
	 *            is the short file path.
	 * @throws ConfigException
	 *             is thrown in case of config read file failures.
	 */
	private boolean loadResource(String resource) {
		URL url = getClass().getResource(resource);
		if (url == null) {
			return false;
		}
		String file = url.getFile();
		return load(resource, new File(file));
	}

	public boolean load(String resource, File file) {
		try {
			ConfigFile f = new ConfigFile(file);
			configuratorHash.put(resource, f.readToHash());
			f.close();
			return true;
		} catch (IOException e) {
			// Logger.getInstance().logException(this, e);
		}
		return false;
	}

	/**
	 * This method reads the configuration in the appropriate order. At first
	 * it's looked for a file within the CLASSPATH. If this file is found no
	 * other directories are searched due to the assumption that this is the one
	 * and only package or distribution configuration.
	 * 
	 * If it's not a resource (the file is not in the CLASSPATH) the directories
	 * '/etc', 'user.home', 'user.home/.', 'user.dir' and 'user.dir/.' are
	 * searched.
	 * 
	 * If a file is present several times, the entry's last version is used.
	 * 
	 * @param file
	 *            is the short file path.
	 * @throws ConfigException
	 *             is thrown in case of config read file failures.
	 */
	private void readAll(String file) throws ConfigException {
		if (!configuratorHash.contains(file)) {
			configuratorHash.put(file, new ConfigHash());
		}
		boolean found = loadResource(file);
		if (!found) {
			ArrayList<String> files = ConfigFile.getAvailableConfigFiles(file);
			for (String configFile: files) {
				found |= load(file, new File(configFile));
			}
		}
		if (!found) {
			logger.warn("Could no read configuration file '" + file + "'");
			throw new ConfigException("Could not read configuration file '"
					+ file + "'!");
		}
	}

	/**
	 * This method is for internal use only and it reads the configHash and
	 * returns the value specified by file, section and key. If the specified
	 * file was not read before the file's content will be read and included
	 * into the configHash.
	 * 
	 * @param resource
	 *            is the short path to the configuration file. The reading will
	 *            be performed on different places regarding to the OS.
	 * @param section
	 *            is the section in the file which contains the needed key.
	 * @param key
	 *            is the key name to be read and returned.
	 * @return A String is returned containing the value of the key.
	 */
	private String readEntry(String resource, String section, String key) {
		try {
			if (!configuratorHash.containsKey(resource)) {
				read(resource);
			}
		} catch (ConfigException ce) {
			return "";
		}
		ConfigHash ch = configuratorHash.get(resource);
		if (ch == null) {
			return "";
		}
		Hashtable<String, String> sh = ch.get(section);
		if (sh == null) {
			return "";
		}
		return sh.get(key);
	}

	/**
	 * This method reads the configHash and returns the value specified by file,
	 * section and key. If the specified file was not read before the file's
	 * content will be read and included into the configHash.
	 * 
	 * @param resource
	 *            is the short path to the configuration file. The reading will
	 *            be performed on different places regarding to the OS.
	 * @param section
	 *            is the section in the file which contains the needed key.
	 * @param key
	 *            is the key name to be read and returned.
	 * @return A String is returned containing the value of the key.
	 */
	static public String getEntry(String resource, String section, String key) {
		return getInstance().readEntry(resource, section, key);
	}

	private ConfigHash returnResource(String resource) {
		try {
			if (!configuratorHash.containsKey(resource)) {
				read(resource);
			}
		} catch (ConfigException ce) {
			return null;
		}
		return configuratorHash.get(resource);
	}

	static public ConfigHash getResource(String resource) {
		return getInstance().returnResource(resource);
	}

	static private synchronized void createInstance() {
		if (instance == null) {
			instance = new Configurator();
		}
	}

	/**
	 * This is the method to get a reference to the Configurator instance which
	 * is created only once. This is the only way to get access to this class,
	 * because this class was designed in singleton pattern.
	 * 
	 * @return The reference to the Configurator class is returned.
	 */
	static public synchronized Configurator getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}
}
