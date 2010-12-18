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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class CheckBoxMenuItem extends JCheckBoxMenuItem implements Widget,
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public CheckBoxMenuItem() {
		super();
		initialize();
	}

	public CheckBoxMenuItem(String text) {
		super(text);
		initialize();
	}

	public CheckBoxMenuItem(String text, Icon icon) {
		super(text, icon);
		initialize();
	}

	public CheckBoxMenuItem(String text, boolean selected) {
		super(text, selected);
		initialize();
	}

	public CheckBoxMenuItem(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
		initialize();
	}

	private void initialize() {
		addActionListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void connect(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.connect(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		return connectionManager.isConnected(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.release(signal, receiver, slot, types);
	}

	@Signal
	public void start() {
		connectionManager.emitSignal("start");
		changed(isSelected());
	}

	@Signal
	public void changed(boolean state) {
		connectionManager.emitSignal("changed", state);
	}

	@Signal
	public void actionPerformed(ActionEvent actionEvent) {
		start();
		connectionManager.emitSignal("actionPerformed");
	}

	@Override
	public void addMediator(Mediator mediator) {
		connectionManager.connect("changed", mediator, "widgetChanged");
	}

	@Override
	public void removeMediator(Mediator mediator) {
		connectionManager.release("changed", mediator, "widgetChanged");
	}

	@Override
	public void changed(Widget widget) {
		connectionManager.emitSignal("changed", widget);
	}
}
