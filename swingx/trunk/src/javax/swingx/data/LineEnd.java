package javax.swingx.data;

public enum LineEnd {
    WINDOWS("\r\n"), UNIX("\n");

    private String string;

    private LineEnd(String string) {
	this.string = string;
    }

    public String getString() {
	return string;
    }
}
