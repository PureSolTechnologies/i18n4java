/***************************************************************************
 *
 * Copyright 2009-2010 PureSol Technologies 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 *
 ***************************************************************************/

package javax.swingx;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class Tree extends JTree implements ConnectionHandler,
		TreeSelectionListener {

	private static final long serialVersionUID = 8972353294401051153L;

	public Tree() {
		super();
		init();
	}

	public Tree(Hashtable<?, ?> value) {
		super(value);
		init();
	}

	public Tree(Object[] value) {
		super(value);
		init();
	}

	public Tree(TreeModel newModel) {
		super(newModel);
		init();
	}

	public Tree(TreeNode root, boolean asksAllowsChildren) {
		super(root, asksAllowsChildren);
		init();
	}

	public Tree(TreeNode root) {
		super(root);
		init();
	}

	public Tree(Vector<?> value) {
		super(value);
		init();
	}

	private void init() {
		addTreeSelectionListener(this);
	}

	/**
	 * This variable keeps the instance of the connection manager.
	 */
	protected ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.connect(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.release(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		return connectionManager.isConnected(signal, receiver, slot, types);
	}

	@Override
	public void valueChanged(TreeSelectionEvent event) {
		valueChanged();
		valueChanged(event.getNewLeadSelectionPath());
	}

	@Signal
	public void valueChanged() {
		connectionManager.emitSignal("valueChanged");
	}

	@Signal
	public void valueChanged(TreePath treePath) {
		connectionManager.emitSignal("valueChanged", treePath);
	}
}
