package javax.swingx.validator;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class IntegerValidatorTest extends TestCase {

	@Test
	public void testValid() {
		IntegerValidator validator = new IntegerValidator();
		Assert.assertTrue(validator.isValid(1));
		Assert.assertTrue(validator.isValid("1"));
		Assert.assertTrue(validator.isValid("0"));
		Assert.assertTrue(validator.isValid("-1"));
	}

	@Test
	public void testInvalid() {
		IntegerValidator validator = new IntegerValidator();
		Assert.assertFalse(validator.isValid(1.1));
		Assert.assertFalse(validator.isValid("1.1"));
		Assert.assertFalse(validator.isValid("string"));
		Assert.assertFalse(validator.isValid(true));
	}
}
