package javax.swingx.data;

import java.util.Date;

/**
 * This class represents the complete information about the type of a variable.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public class ValueType {

	/**
	 * This variable keeps the class object stored for later use within the
	 * object.
	 */
	protected Class<?> classObject = null;

	/**
	 * This variable stores the information whether the variable is descrete or
	 * not.
	 */
	protected boolean descrete = false;

	/**
	 * This variable stores whether the type is numeric or not.
	 */
	protected boolean numeric = false;

	/**
	 * This variable stores whether the type is a string or not.
	 */
	protected boolean string = false;

	static public ValueType fromClassName(String className) {
		return new ValueType(className);
	}

	static public ValueType fromClass(Class<?> clazz) {
		return new ValueType(clazz);
	}

	static public ValueType fromObject(Object object) {
		return new ValueType(object.getClass());
	}

	/**
	 * This constructor sets all internal values depended to a Class the
	 * variable is type of.
	 * 
	 * @param c
	 *            is the Class of the variable.
	 */
	private ValueType(Class<?> c) {
		set(c);
	}

	/**
	 * This constructor sets all internal values depended to a Class the
	 * variable is type of.
	 * 
	 * @param c
	 *            is the Class of the variable.
	 */
	private ValueType(String className) {
		Class<?> c;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Class '" + className
					+ "' was not found.");
		}
		set(c);
	}

	/**
	 * This method sets the internal values in dependence to a Class.
	 * 
	 * @param c
	 *            is the Class the properties are taken out.
	 */
	public void set(Class<?> c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException("c is null!");
		}
		classObject = c;
		if (Integer.class.equals(c)) {
			descrete = true;
			numeric = true;
			string = false;
		} else if (Float.class.equals(c)) {
			descrete = false;
			numeric = true;
			string = false;
		} else if (Double.class.equals(c)) {
			descrete = false;
			numeric = true;
			string = false;
		} else if (String.class.equals(c)) {
			descrete = false;
			numeric = false;
			string = true;
		} else if (Boolean.class.equals(c)) {
			descrete = true;
			numeric = false;
			string = false;
		} else if (Date.class.equals(c)) {
			descrete = true;
			numeric = false;
			string = false;
		} else {
			descrete = false;
			numeric = false;
			string = false;
		}
	}

	/**
	 * This method returns the variable type's class name.
	 * 
	 * @return The name of the variable type's class is returned.
	 */
	public String getClassName() {
		return classObject.getName();
	}

	/**
	 * This method returns the variable class.
	 * 
	 * @return The Class of the variable class is returned.
	 */
	public Class<?> getClassObject() {
		return classObject;
	}

	/**
	 * This method returns the descrete property.
	 * 
	 * @return True is returned, if the type is descrete like an Integer.
	 *         Otherwise false is returned.
	 */
	public boolean isDescrete() {
		return descrete;
	}

	/**
	 * This method returns the numeric property.
	 * 
	 * @return True is returned, if the type is numeric like an Integer or
	 *         Double. Otherwise false is returned.
	 */
	public boolean isNumeric() {
		return numeric;
	}

	/**
	 * This method returns the string property.
	 * 
	 * @return True is returned, if the type is a string like a String.
	 *         Otherwise false is returned.
	 */
	public boolean isString() {
		return string;
	}

	/**
	 * This method checks whether another class is equal to this variable type
	 * or not.
	 * 
	 * @param c
	 *            is a class which to check against.
	 * @return true is returned if the stored variable type is equal to c.
	 *         Otherwise false is returned.
	 */
	public boolean is(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return getClassObject().equals(clazz);
	}

	/**
	 * This method checks whether another class is equal to this variable type
	 * or not.
	 * 
	 * @param c
	 *            is a class which to check against.
	 * @return true is returned if the stored variable type is equal to c.
	 *         Otherwise false is returned.
	 */
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		return toString().equals(o.toString());
	}

	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * This method checks whether another class is instance of this variable
	 * type or not.
	 * 
	 * @param c
	 *            is a class which to check against.
	 * @return true is returned if the stored variable type is cast able to c.
	 *         Otherwise false is returned.
	 */
	public boolean isAssignableFrom(Class<?> c) {
		return this.classObject.isAssignableFrom(c);
	}

	public String toString() {
		return getClassName();
	}
}
