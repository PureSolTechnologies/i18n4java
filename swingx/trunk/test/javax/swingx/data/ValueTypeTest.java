package javax.swingx.data;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class ValueTypeTest extends TestCase {

	@Test
	public void testRecognizeType() {
		Assert.assertEquals(Boolean.class, ValueType.recognizeType("true"));
		Assert.assertEquals(Boolean.class, ValueType.recognizeType("false"));

		Assert.assertEquals(Byte.class, ValueType.recognizeType("-128"));
		Assert.assertEquals(Byte.class, ValueType.recognizeType("0"));
		Assert.assertEquals(Byte.class, ValueType.recognizeType("127"));

		Assert.assertEquals(Short.class, ValueType.recognizeType("-32768"));
		Assert.assertEquals(Short.class, ValueType.recognizeType("-129"));
		Assert.assertEquals(Short.class, ValueType.recognizeType("128"));
		Assert.assertEquals(Short.class, ValueType.recognizeType("32767"));

		Assert.assertEquals(Integer.class, ValueType
				.recognizeType("-2147483648"));
		Assert.assertEquals(Integer.class, ValueType.recognizeType("-32769"));
		Assert.assertEquals(Integer.class, ValueType.recognizeType("32768"));
		Assert.assertEquals(Integer.class, ValueType
				.recognizeType("2147483647"));

		Assert.assertEquals(Long.class, ValueType.recognizeType("-2147483649"));
		Assert.assertEquals(Long.class, ValueType.recognizeType("2147483648"));

		Assert.assertEquals(Float.class, ValueType.recognizeType("-1.1E+38"));
		Assert.assertEquals(Float.class, ValueType.recognizeType("-1.1"));
		Assert.assertEquals(Float.class, ValueType.recognizeType("0.0"));
		Assert.assertEquals(Float.class, ValueType.recognizeType("1.1"));
		Assert.assertEquals(Float.class, ValueType.recognizeType("1.1E+38"));

		System.out.println(Float.valueOf("1E+1000").isInfinite());
		System.out.println(Float.valueOf("-1E+1000").isInfinite());

		Assert.assertEquals(Double.class, ValueType.recognizeType("-1.1E+308"));
		Assert.assertEquals(Double.class, ValueType.recognizeType("-1.1E+39"));
		Assert.assertEquals(Double.class, ValueType.recognizeType("1.1E+39"));
		Assert.assertEquals(Double.class, ValueType.recognizeType("1.1E+308"));

		Assert.assertEquals(Character.class, ValueType.recognizeType("C"));
		
		Assert.assertEquals(String.class, ValueType.recognizeType("-1.1E+309"));
		Assert.assertEquals(String.class, ValueType.recognizeType("1.1E+309"));
	}

}
