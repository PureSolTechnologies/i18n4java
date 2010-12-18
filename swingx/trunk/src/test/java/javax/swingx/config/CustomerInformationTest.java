package javax.swingx.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomerInformationTest {

	@Test
	public void testResource() {
		assertNotNull(getClass().getResource("/config/about"));
	}

	@Test
	public void testGetLongName() {
		String string = CustomerInformation.getLongName();
		assertNotNull(string);
		assertFalse(string.isEmpty());
		assertEquals("nobody", string);
	}

	@Test
	public void testGetShortName() {
		String string = CustomerInformation.getShortName();
		assertNotNull(string);
		assertFalse(string.isEmpty());
		assertEquals("nbdy", string);
	}

	@Test
	public void testGetCustomerInformation() {
		String string = CustomerInformation.getCustomerInformation();
		assertNotNull(string);
		assertFalse(string.isEmpty());
		assertEquals("<html><body>\n" + "<h1>nobody</h1>\n"
				+ "</body></html>\n", string);
	}

}
