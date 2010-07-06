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

public class BorderLayoutWidget extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BorderLayout borderLayout = new BorderLayout();

	private Component north = null;

	private Component south = null;

	private Component center = null;

	private Component east = null;

	private Component west = null;

	public BorderLayoutWidget() {
		super();
		setLayout(borderLayout);
	}

	public void setNorth(Component widget) {
		north = widget;
		add(north, BorderLayout.NORTH);
	}

	public Component getNorth() {
		return north;
	}

	public void setSouth(Component widget) {
		south = widget;
		add(south, BorderLayout.SOUTH);
	}

	public Component getSouth() {
		return south;
	}

	public void setCenter(Component widget) {
		center = widget;
		add(center, BorderLayout.CENTER);
	}

	public Component getCenter() {
		return center;
	}

	public void setEast(Component widget) {
		east = widget;
		add(east, BorderLayout.EAST);
	}

	public Component getEast() {
		return east;
	}

	public void setWest(Component widget) {
		west = widget;
		add(west, BorderLayout.WEST);
	}

	public Component getWest() {
		return west;
	}
}
