package javax.swingx.validator;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DoubleValidatorTest extends TestCase {

	@Test
	public void testValid() {
		DoubleValidator validator = new DoubleValidator();
		Assert.assertTrue(validator.isValid(1));
		Assert.assertTrue(validator.isValid("1.1"));
		Assert.assertTrue(validator.isValid("0"));
		Assert.assertTrue(validator.isValid("-1.1"));
		Assert.assertTrue(validator.isValid("42"));
		Assert.assertTrue(validator.isValid("1.2345e+6"));
	}

	@Test
	public void testInvalid() {
		DoubleValidator validator = new DoubleValidator();
		Assert.assertFalse(validator.isValid("string"));
		Assert.assertFalse(validator.isValid(true));
	}
}
