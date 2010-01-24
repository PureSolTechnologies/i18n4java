package javax.swingx.data;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TypeWrapperTest extends TestCase {

	@Test
	public void testPrimitiveAndWrapperConfig() {
		Assert.assertEquals(8, TypeWrapper.PRIMITIVES.length);
		Assert.assertEquals(8, TypeWrapper.PRIMITIVE_WRAPPERS.length);
	}

	@Test
	public void testIsPrimitiveWrapper() {
		Assert.assertTrue(TypeWrapper.isPrimitiveWrapper(Byte.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveWrapper(Short.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveWrapper(Integer.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveWrapper(Long.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveWrapper(Float.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveWrapper(Double.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveWrapper(Character.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveWrapper(Boolean.class));
	}

	@Test
	public void testIsPrimitiveOrWrapper() {
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(Byte.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(Short.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(Integer.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(Long.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(Float.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(Double.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(Character.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveWrapper(Boolean.class));

		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(byte.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(short.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(int.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(long.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(float.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(double.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(char.class));
		Assert.assertTrue(TypeWrapper.isPrimitiveOrWrapper(boolean.class));
	}

	@Test
	public void testToPrimitive() {
		Assert.assertEquals(byte.class, TypeWrapper.toPrimitive(Byte.class));
		Assert.assertEquals(short.class, TypeWrapper.toPrimitive(Short.class));
		Assert.assertEquals(int.class, TypeWrapper.toPrimitive(Integer.class));
		Assert.assertEquals(long.class, TypeWrapper.toPrimitive(Long.class));
		Assert.assertEquals(float.class, TypeWrapper.toPrimitive(Float.class));
		Assert
				.assertEquals(double.class, TypeWrapper
						.toPrimitive(Double.class));
		Assert.assertEquals(char.class, TypeWrapper
				.toPrimitive(Character.class));
		Assert.assertEquals(boolean.class, TypeWrapper
				.toPrimitive(Boolean.class));
	}

	@Test
	public void testToPrimitiveWrapper() {
		Assert.assertEquals(Byte.class, TypeWrapper
				.toPrimitiveWrapper(byte.class));
		Assert.assertEquals(Short.class, TypeWrapper
				.toPrimitiveWrapper(short.class));
		Assert.assertEquals(Integer.class, TypeWrapper
				.toPrimitiveWrapper(int.class));
		Assert.assertEquals(Long.class, TypeWrapper
				.toPrimitiveWrapper(long.class));
		Assert.assertEquals(Float.class, TypeWrapper
				.toPrimitiveWrapper(float.class));
		Assert.assertEquals(Double.class, TypeWrapper
				.toPrimitiveWrapper(double.class));
		Assert.assertEquals(Character.class, TypeWrapper
				.toPrimitiveWrapper(char.class));
		Assert.assertEquals(Boolean.class, TypeWrapper
				.toPrimitiveWrapper(boolean.class));
	}
}
