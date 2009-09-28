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

	static public Panel addFor(Component component, String text, int align) {
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
