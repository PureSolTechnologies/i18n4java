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

import javax.i18n4java.Translator;
import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swingx.connect.ConnectionManager;
import javax.swingx.connect.Signal;
import javax.swingx.connect.Slot;

public class Menu extends JMenu implements Widget, ActionListener, MenuListener {

	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator
			.getTranslator(Menu.class);

	/**
	 * This variable stores the reference to the local ConnectionManager.
	 */
	private ConnectionManager connectionManager = ConnectionManager
			.createFor(this);

	public Menu() {
		super();
		initialize();
	}

	public Menu(String text) {
		super(text);
		initialize();
	}

	public Menu(String text, boolean tearOff) {
		super(text, tearOff);
		initialize();
	}

	private void initialize() {
		addMenuListener(this);
	}

	public MenuItem addDefaultAboutItem() {
		MenuItem aboutItem = new MenuItem(translator.i18n("About..."));
		aboutItem.connect("start", this, "showAbout");
		add(aboutItem);
		return aboutItem;
	}

	/**
	 * This method shows the default about box.
	 */
	@Slot
	public void showAbout() {
		AboutBox.about();
	}

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
	public boolean isConnected(String signal, Object receiver, String slot,
			Class<?>... types) {
		return connectionManager.isConnected(signal, receiver, slot, types);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void release(String signal, Object receiver, String slot,
			Class<?>... types) {
		connectionManager.release(signal, receiver, slot, types);
	}

	@Signal
	public void start() {
		connectionManager.emitSignal("start");
	}

	@Signal
	public void actionPerformed(ActionEvent actionEvent) {
		start();
		connectionManager.emitSignal("actionPerformed", actionEvent);
	}

	@Signal
	public void menuCanceled(MenuEvent menuEvent) {
		connectionManager.emitSignal("menuCanceled", menuEvent);
	}

	@Signal
	public void menuDeselected(MenuEvent menuEvent) {
		connectionManager.emitSignal("menuDeselected", menuEvent);
	}

	public void menuSelected(MenuEvent menuEvent) {
		connectionManager.emitSignal("menuSelected", menuEvent);
	}

	public void addMediator(Mediator mediator) {
		connect("changed", mediator, "widgetChanged");
	}

	public void changed(Widget widget) {
		connectionManager.emitSignal("changed", widget);
	}
}
