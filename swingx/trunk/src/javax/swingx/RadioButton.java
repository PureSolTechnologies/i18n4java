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

import javax.swing.JRadioButton;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;

public class RadioButton extends JRadioButton implements Widget, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public RadioButton() {
		super();
		addActionListener(this);
	}

	public RadioButton(String text) {
		super(text);
		addActionListener(this);
	}

	public RadioButton(String text, boolean checked) {
		super(text, checked);
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
	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.release(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		return connectionManager.isConnected(signal, receiver, slot, types);
	}

	@Signal
	public void start() {
		connectionManager.emitSignal("start");
		changed(this);
	}

	@Signal
	public void actionPerformed(ActionEvent actionEvent) {
		connectionManager.emitSignal("actionPerformed", actionEvent);
		start();
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
