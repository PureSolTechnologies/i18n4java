package javax.swingx.data;

import java.util.Hashtable;

/**
 * This class converts primitive types to wrapper classes and vice versa.
 * 
 * The classes used are: byte <--> Byte, short <--> Short, int <--> Integer,
 * long <--> Long, float <--> Float, double <--> Double, char <--> Character,
 * and boolean <--> Boolean.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class TypeWrapper {

	// constants...
	public static final Class<?> PRIMITIVE_WRAPPERS[] = { Byte.class,
			Short.class, Integer.class, Long.class, Float.class, Double.class,
			Character.class, Boolean.class };

	public static final Class<?> PRIMITIVES[] = { byte.class, short.class,
			int.class, long.class, float.class, double.class, char.class,
			boolean.class };

	private static Hashtable<Class<?>, Class<?>> prim2wrap;
	private static Hashtable<Class<?>, Class<?>> wrap2prim;

	// initialize the static assignment hashes for fast conversion...
	static {
		if (PRIMITIVE_WRAPPERS.length != PRIMITIVES.length)
			throw new RuntimeException(
					"The number of primitives and wrappers have to be equal!");
		prim2wrap = new Hashtable<Class<?>, Class<?>>();
		wrap2prim = new Hashtable<Class<?>, Class<?>>();
		for (int i = 0; i < PRIMITIVE_WRAPPERS.length; i++) {
			prim2wrap.put(PRIMITIVES[i], PRIMITIVE_WRAPPERS[i]);
			wrap2prim.put(PRIMITIVE_WRAPPERS[i], PRIMITIVES[i]);
		}
	}

	/**
	 * Checks weither a clazz is a primitive or a wrapper class.
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
		return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
	}

	/**
	 * Checks for primitive wrapper.
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isPrimitiveWrapper(Class<?> clazz) {
		return wrap2prim.containsKey(clazz);
	}

	public static Class<?> toPrimitive(Class<?> clazz) {
		if (clazz.isPrimitive()) {
			return clazz;
		}
		return wrap2prim.get(clazz);
	}

	public static Class<?> toPrimitiveWrapper(Class<?> clazz) {
		if (isPrimitiveWrapper(clazz)) {
			return clazz;
		}
		return prim2wrap.get(clazz);
	}
}
