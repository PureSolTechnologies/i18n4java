package javax.swingx.progress;

import junit.framework.TestCase;

public class ProgressWindowTest extends TestCase {

	public static void main(String args[]) {
		TestProgressObservable tp = new TestProgressObservable(true);
		ProgressWindow pw = new ProgressWindow(tp);
		pw.run();
	}

}
