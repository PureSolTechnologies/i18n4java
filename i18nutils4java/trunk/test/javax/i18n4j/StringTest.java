package javax.i18n4j;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class StringTest extends TestCase {

	@Test
	public void testReplaceLineBreak() {
		String string = "1\n2\\n3\n";
		System.out.println("'" + string + "'");
		String string2 = string.replaceAll("\\\\n", "\n");
		System.out.println("'" + string2 + "'");
		Assert.assertEquals("1\n2\n3\n", string2);
		String string3 = string.replaceAll("\\n", "\\\\n");
		System.out.println("'" + string3 + "'");
		Assert.assertEquals("1\\n2\\n3\\n", string3);
	}
}
