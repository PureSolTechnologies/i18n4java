/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
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
 ***************************************************************************/

package javax.swingx.config;

/**
 * This object stores all customer information like names, contacts and logos
 * for later use in classes with customization and copyright, contact and
 * customer information.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class CustomerConfig {

	/**
	 * This variable keeps the customers short name.
	 */
	private static String shortName = Configurator.getEntry("/config/customer",
			"GENERAL", "shortname", true);

	/**
	 * This variable keeps the customers short name.
	 */
	private static String longName = Configurator.getEntry("/config/customer",
			"GENERAL", "name", true);

	/**
	 * This method sets the customers short name for messages.
	 * 
	 * @param name
	 *            is the short name to be set.
	 * @throws IllegalArgumentException
	 *             is thrown if name is null or empty.
	 */
	public static void setShortName(String name)
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
		shortName = name;
	}

	/**
	 * This method returns the set customers short name.
	 * 
	 * @return A String containing the customers short name is returned.
	 */
	public static String getShortName() {
		return shortName;
	}

	/**
	 * This method sets the customers long name for messages.
	 * 
	 * @param name
	 *            is the short name to be set.
	 * @throws IllegalArgumentException
	 *             is thrown if name is null or empty.
	 */
	public static void setLongName(String name) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("name must not be null!");
		}
		if (name.isEmpty()) {
			throw new IllegalArgumentException(
					"The default value for this name is empty, but if "
							+ "this method is called a real name has to"
							+ " be set. Empty is illegal!");
		}
		longName = name;
	}

	/**
	 * This method returns the set customers long name.
	 * 
	 * @return A String containing the customers long name is returned.
	 */
	public static String getLongName() {
		return longName;
	}

	static public String getCustomerInformation() {
		return ConfigFile.readSection("config/customer", "ABOUT");
	}
}
