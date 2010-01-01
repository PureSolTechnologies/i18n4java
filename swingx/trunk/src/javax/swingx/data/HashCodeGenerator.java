package javax.swingx.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCodeGenerator {

	private static String get(String algorithm, String line) {
		/* Calculation */
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("Something strange is going on in "
					+ algorithm + "module! Is there no " + algorithm
					+ "supported!?");
		}
		digest.reset();
		digest.update(line.getBytes());
		byte[] result = digest.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			hexString.append(Integer.toHexString(0xFF & result[i]));
		}
		return hexString.toString();
	}

	public static String getMD5(String line) {
		return get("MD5", line);
	}

	public static String getSHA(String line) {
		return get("SHA", line);
	}
}
