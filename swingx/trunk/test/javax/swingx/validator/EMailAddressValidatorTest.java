package javax.swingx.validator;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class EMailAddressValidatorTest extends TestCase {

	@Test
	public void testValid() {
		EMailAddressValidator validator = new EMailAddressValidator();
		Assert.assertTrue(validator.isValid("a@a.de"));
		Assert.assertTrue(validator.isValid("rl719236@sourceforge.net"));
	}

	@Test
	public void testInvalid() {
		EMailAddressValidator validator = new EMailAddressValidator();
		Assert.assertFalse(validator.isValid("a@a.a"));
		Assert.assertFalse(validator.isValid("aa@aa.a"));
	}
}
