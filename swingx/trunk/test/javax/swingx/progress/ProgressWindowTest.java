package javax.swingx.progress;

import org.junit.Test;

import junit.framework.TestCase;

public class ProgressWindowTest extends TestCase {

	@Test
	public void test() {
		/*
		 * This method is just for surpressing the "no test found" warning.
		 */
	}

	public static void main(String args[]) {
		TestProgressObservable tp = new TestProgressObservable(true);
		ProgressWindow pw = new ProgressWindow(tp);
		pw.run();
	}

}
