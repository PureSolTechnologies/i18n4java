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
