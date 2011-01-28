package javax.i18n4java.gui;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileTreeTest {

	@Test
	public void testInstance() {
		assertNotNull(new FileTree("/"));
		assertNotNull(new FileTree(new FileTree("/"), "/test"));
	}

	@Test
	public void testDefaultValue() {
		FileTree fileTree = new FileTree("/");
		assertEquals(null, fileTree.getParent());
		assertEquals("/", fileTree.getName());

		assertEquals(0, fileTree.getChildCount());
		assertFalse(fileTree.hashChildren());

		FileTree root = new FileTree("/");
		fileTree = new FileTree(root, "/test");
		assertSame(root, fileTree.getParent());
		assertEquals("/test", fileTree.getName());
	}

}
