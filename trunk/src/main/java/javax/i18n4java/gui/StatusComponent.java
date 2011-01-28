package javax.i18n4java.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusComponent extends JPanel {

	private static final long serialVersionUID = 9057595345044306838L;

	public StatusComponent(String text, boolean selected, boolean focus,
			boolean finished) {
		super();

		setOpaque(false);
		BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(layout);

		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setVerticalAlignment(JLabel.VERTICAL);

		if (focus) {
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		if (finished) {
			label.setForeground(new Color(0, 127, 0));
			if (selected) {
				setOpaque(true);
				setBackground(new Color(192, 255, 192));
			}
		} else {
			label.setForeground(new Color(127, 0, 0));
			if (selected) {
				setOpaque(true);
				setBackground(new Color(255, 192, 192));
			}
		}
		add(label, BorderLayout.CENTER);
	}

}
