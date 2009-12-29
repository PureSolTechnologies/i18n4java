package javax.swingx.config;

public enum ClassRegistryElementType {

    FACTORY("factory"), SINGLETON("singleton"), CLONED("cloned");

    private String name;

    private ClassRegistryElementType(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public boolean is(String name) {
	return (this.name.equalsIgnoreCase(name));
    }

    public static ClassRegistryElementType from(String name) {
	ClassRegistryElementType[] types =
		ClassRegistryElementType.class.getEnumConstants();
	for (ClassRegistryElementType type : types) {
	    if (type.is(name)) {
		return type;
	    }
	}
	throw new IllegalArgumentException(
		"'"
			+ name
			+ "' is not a supported name of a constant in ClassRegistryElementType");
    }
}
