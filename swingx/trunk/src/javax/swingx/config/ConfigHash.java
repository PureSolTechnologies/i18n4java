/*
 * Created on Oct 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package javax.swingx.config;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * This Hash stores the data of a single configuration file.
 * 
 * @author Rick-Rainer Ludwig
 */
public class ConfigHash extends Hashtable<String, Hashtable<String, String>> {

	private static final long serialVersionUID = 1L;

	/**
	 * This method is used for debugging. It prints the content of the hash to
	 * the stdout.
	 */
	public void println() {
		Enumeration<String> sectionEnum = this.keys();
		while (sectionEnum.hasMoreElements()) {
			String section = sectionEnum.nextElement();
			System.out.println("[" + section + "]");
			Enumeration<String> keyEnum = get(section).keys();
			while (keyEnum.hasMoreElements()) {
				String key = keyEnum.nextElement();
				String value = get(section).get(key);
				System.out.println(key + "=" + value);
			}
		}
	}
}
