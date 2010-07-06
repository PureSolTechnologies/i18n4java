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

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swingx.connect.Slot;

public class Label extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static public Panel addTo(Component component, String text, int align) {
		Panel panel = new Panel();
		panel.setLayout(new BorderLayout());
		panel.add(component, BorderLayout.CENTER);
		if (align == Label.LEFT) {
			panel.add(new Label(text), BorderLayout.WEST);
		} else if (align == Label.TOP) {
			panel.add(new Label(text), BorderLayout.NORTH);
		} else if (align == Label.RIGHT) {
			panel.add(new Label(text), BorderLayout.EAST);
		} else if (align == Label.RIGHT) {
			panel.add(new Label(text), BorderLayout.SOUTH);
		} else {
			throw new IllegalArgumentException("Alignment " + align
					+ " is not supported!");
		}
		return panel;
	}

	public Label() {
		super();
	}

	public Label(String text) {
		super(text);
	}

	public Label(String text, int alignment) {
		super(text, alignment);
	}

	@Slot
	public void setText(String text) {
		super.setText(text);
	}

	@Slot
	public Boolean setTextWithSelection(String text, int dot, int mark) {
		StringBuffer str = new StringBuffer(text);
		if (dot != mark) {
			if (dot < mark) {
				str.insert(mark, '<');
				str.insert(dot, '>');
			} else {
				str.insert(dot, '<');
				str.insert(mark, '>');
			}
		}
		setText(str.toString());
		return true;
	}
}
