package javax.swingx.data;

public class Encrypter {
	static public String encryptPassword(String password) {
		/**
		 * A combination of MD5 and SHA is used to overcome the weakness of MD5
		 * and SHA. In combination it should be strong enough!
		 */
		return HashCodeGenerator.getSHA(HashCodeGenerator.getMD5(password));
	}

}
