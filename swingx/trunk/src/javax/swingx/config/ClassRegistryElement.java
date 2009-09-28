package javax.swingx.config;

public class ClassRegistryElement {

	static public final int FACTORY = 0;
	static public final int SINGLETON = 1;
	static public final int CLONING = 2;

	private int type = 0;
	private String className = "";

	public ClassRegistryElement(int type, String className) {
		this.type = type;
		this.className = className;
	}

	public int getType() {
		return type;
	}

	public String getClassName() {
		return className;
	}
}
