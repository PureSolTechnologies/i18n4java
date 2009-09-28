package javax.swingx.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCodeGenerator {

	static public String getMD5(String line) {
		/* Calculation */
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Something strange is going on in MD5 module! Is there no MD5 supported!?");
		}
		md5.reset();
		md5.update(line.getBytes());
		byte[] result = md5.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			hexString.append(Integer.toHexString(0xFF & result[i]));
		}
		return hexString.toString();
	}

	static public String getSHA(String line) {
		/* Calculation */
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Something strange is going on in SHA module! Is there no MD5 supported!?");
		}
		sha.reset();
		sha.update(line.getBytes());
		byte[] result = sha.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			hexString.append(Integer.toHexString(0xFF & result[i]));
		}
		return hexString.toString();
	}
}
