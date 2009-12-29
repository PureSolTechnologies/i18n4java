package javax.swingx.config;

public class ClassRegistryElement {

    private ClassRegistryElementType type;
    private String className = "";

    public ClassRegistryElement(ClassRegistryElementType type,
	    String className) {
	this.type = type;
	this.className = className;
    }

    public ClassRegistryElementType getType() {
	return type;
    }

    public String getClassName() {
	return className;
    }
}
