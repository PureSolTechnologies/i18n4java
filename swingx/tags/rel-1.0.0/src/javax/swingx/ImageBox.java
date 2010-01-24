package javax.swingx;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

public class ImageBox extends Panel {

	private static final long serialVersionUID = 1574055456859542414L;

	private static final Logger logger = Logger.getLogger(ImageBox.class);

	private final Image image;

	public ImageBox(Image image) {
		super();
		this.image = image;
		setPreferredSize(new Dimension(image.getWidth(this), image
				.getHeight(this)));
		setSize(image.getWidth(this), image.getHeight(this));
	}

	public ImageBox(Image image, int width, int height) {
		super();
		this.image = image;
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
	}

	public void paint(Graphics g) {
		Component parent = this;
		MediaTracker mt = new MediaTracker(parent);
		mt.addImage(image, 0);
		try {
			mt.waitForID(0);
		} catch (InterruptedException e) {
			logger.warn(e.getMessage(), e);
		}
		BufferedImage logoImage;
		if ((image.getWidth(parent) > 0) && (image.getWidth(parent) > 0)) {
			logoImage = new BufferedImage(image.getWidth(parent), image
					.getHeight(parent), BufferedImage.TYPE_INT_RGB);
			logoImage.getGraphics().drawImage(image, 0, 0, parent);
		} else {
			logoImage = null;
		}
		Graphics2D g2d = (Graphics2D) g;

		AffineTransform at = new AffineTransform((double) getWidth()
				/ (double) image.getWidth(this), 0.0, 0.0, (double) getHeight()
				/ (double) image.getHeight(this), 0.0, 0.0);
		g2d.drawRenderedImage(logoImage, at);
	}
}
