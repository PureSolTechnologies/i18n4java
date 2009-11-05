package javax.swingx.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

public class ValueTypeTest extends TestCase {

	@Test
	public void testRecognizeType() {
		Assert.assertEquals(Boolean.class, ValueType
				.recognizeFromString("true").getClassObject());
		Assert.assertEquals(Boolean.class, ValueType.recognizeFromString(
				"false").getClassObject());

		Assert.assertEquals(Byte.class, ValueType.recognizeFromString("-128")
				.getClassObject());
		Assert.assertEquals(Byte.class, ValueType.recognizeFromString("0")
				.getClassObject());
		Assert.assertEquals(Byte.class, ValueType.recognizeFromString("127")
				.getClassObject());

		Assert.assertEquals(Short.class, ValueType
				.recognizeFromString("-32768").getClassObject());
		Assert.assertEquals(Short.class, ValueType.recognizeFromString("-129")
				.getClassObject());
		Assert.assertEquals(Short.class, ValueType.recognizeFromString("128")
				.getClassObject());
		Assert.assertEquals(Short.class, ValueType.recognizeFromString("32767")
				.getClassObject());

		Assert.assertEquals(Integer.class, ValueType.recognizeFromString(
				"-2147483648").getClassObject());
		Assert.assertEquals(Integer.class, ValueType.recognizeFromString(
				"-32769").getClassObject());
		Assert.assertEquals(Integer.class, ValueType.recognizeFromString(
				"32768").getClassObject());
		Assert.assertEquals(Integer.class, ValueType.recognizeFromString(
				"2147483647").getClassObject());

		Assert.assertEquals(Long.class, ValueType.recognizeFromString(
				"-2147483649").getClassObject());
		Assert.assertEquals(Long.class, ValueType.recognizeFromString(
				"2147483648").getClassObject());

		Assert.assertEquals(Float.class, ValueType.recognizeFromString(
				"-1.1E+38").getClassObject());
		Assert.assertEquals(Float.class, ValueType.recognizeFromString("-1.1")
				.getClassObject());
		Assert.assertEquals(Float.class, ValueType.recognizeFromString("0.0")
				.getClassObject());
		Assert.assertEquals(Float.class, ValueType.recognizeFromString("1.1")
				.getClassObject());
		Assert.assertEquals(Float.class, ValueType.recognizeFromString(
				"1.1E+38").getClassObject());

		Assert.assertEquals(Double.class, ValueType.recognizeFromString(
				"-1.1E+308").getClassObject());
		Assert.assertEquals(Double.class, ValueType.recognizeFromString(
				"-1.1E+39").getClassObject());
		Assert.assertEquals(Double.class, ValueType.recognizeFromString(
				"1.1E+39").getClassObject());
		Assert.assertEquals(Double.class, ValueType.recognizeFromString(
				"1.1E+308").getClassObject());

		Assert.assertEquals(Character.class, ValueType.recognizeFromString("C")
				.getClassObject());

		Assert.assertEquals(String.class, ValueType.recognizeFromString(
				"-1.1E+309").getClassObject());
		Assert.assertEquals(String.class, ValueType.recognizeFromString(
				"1.1E+309").getClassObject());
	}

	@Test
	public void testSort() {
		List<ValueType> list = new ArrayList<ValueType>();
		list.add(ValueType.fromClass(String.class));
		list.add(ValueType.fromClass(Byte.class));
		list.add(ValueType.fromClass(Long.class));
		list.add(ValueType.fromClass(Integer.class));
		list.add(ValueType.fromClass(Short.class));
		list.add(ValueType.fromClass(Double.class));
		list.add(ValueType.fromClass(Float.class));
		list.add(ValueType.fromClass(Character.class));
		list.add(ValueType.fromClass(Boolean.class));
		Collections.sort(list);
		Assert.assertEquals(Byte.class, list.get(0).getClassObject());
		Assert.assertEquals(Short.class, list.get(1).getClassObject());
		Assert.assertEquals(Integer.class, list.get(2).getClassObject());
		Assert.assertEquals(Long.class, list.get(3).getClassObject());
		Assert.assertEquals(Float.class, list.get(4).getClassObject());
		Assert.assertEquals(Double.class, list.get(5).getClassObject());
		Assert.assertEquals(Character.class, list.get(6).getClassObject());
		Assert.assertEquals(Boolean.class, list.get(7).getClassObject());
		Assert.assertEquals(String.class, list.get(8).getClassObject());
	}
}
