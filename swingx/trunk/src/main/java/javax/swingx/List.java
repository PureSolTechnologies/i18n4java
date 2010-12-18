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

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swingx.connect.ConnectionHandler;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class List extends JList implements ListSelectionListener,
		ConnectionHandler {

	private static final long serialVersionUID = 1L;

	private ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public List() {
		super();
		addListSelectionListener(this);
	}

	public List(ListModel listModel) {
		super(listModel);
		addListSelectionListener(this);
	}

	@Signal
	public void valueChanged(Object value) {
		connectionManager.emitSignal("valueChanged", value);
	}

	@Signal
	public void valuesChanged(Object[] values) {
		connectionManager.emitSignal("valuesChanged", values);
	}

	@Signal
	public void indexChanged(int index) {
		connectionManager.emitSignal("indexChanged", index);
	}

	@Signal
	public void indicesChanged(int[] indices) {
		connectionManager.emitSignal("indicesChanged", indices);
	}

	@Override
	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.connect(signal, receiver, slot, types);
	}

	@Override
	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		return connectionManager.isConnected(signal, receiver, slot, types);
	}

	@Override
	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.release(signal, receiver, slot, types);
	}

	public void valueChanged(ListSelectionEvent event) {
		if (!event.getValueIsAdjusting()) {
			valueChanged(getSelectedValue());
			valuesChanged(getSelectedValues());
			indexChanged(getSelectedIndex());
			indicesChanged(getSelectedIndices());
		}
	}
}
