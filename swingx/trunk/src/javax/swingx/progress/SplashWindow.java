package javax.swingx.progress;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swingx.ImageBox;

import org.apache.log4j.Logger;

public class SplashWindow extends JWindow implements Runnable,
	ProgressObserver {

    private static final long serialVersionUID = 4191554073727049318L;

    private static final Logger logger =
	    Logger.getLogger(SplashWindow.class);

    private Thread splashThread = null;
    private Image image = null;
    private JProgressBar progressBar = null;

    public SplashWindow(URL imageURL, int width, int height) {
	this(imageURL, width, height, null);
    }

    public SplashWindow(URL imageURL, int width, int height, Frame f) {
	super(f);
	setImage(imageURL);
	initUI(width, height);
    }

    public SplashWindow(URL imageURL) {
	this(imageURL, null);
    }

    public SplashWindow(URL imageURL, Frame f) {
	super(f);
	setImage(imageURL);
	initUI(image.getWidth(this), image.getHeight(this));
    }

    private void initUI(int width, int height) {
	this.getContentPane().setLayout(new BorderLayout());
	ImageBox box = new ImageBox(image, width, height);
	box.setVisible(true);
	this.getContentPane().add(box, BorderLayout.CENTER);
	pack();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	setLocation(screenSize.width / 2 - (getWidth() / 2),
		screenSize.height / 2 - (getHeight() / 2));
    }

    private void setImage(URL imageURL) {
	image = Toolkit.getDefaultToolkit().getImage(imageURL);
    }

    public void showProgressBar(boolean visible) {
	if (visible) {
	    progressBar = new JProgressBar();
	    getContentPane().add(progressBar, BorderLayout.SOUTH);
	    pack();
	    Dimension screenSize =
		    Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation(screenSize.width / 2 - (getWidth() / 2),
		    screenSize.height / 2 - (getHeight() / 2));
	} else {
	    getContentPane().remove(progressBar);
	    progressBar = null;
	}
    }

    public void showText(boolean visible) {
	if (progressBar != null) {
	    progressBar.setStringPainted(visible);
	}
    }

    @Override
    public void setText(String text) {
	if (progressBar != null) {
	    progressBar.setString(text);
	}
    }

    @Override
    public void setRange(int min, int max) {
	if (progressBar != null) {
	    progressBar.setMinimum(min);
	    progressBar.setMaximum(max);
	}
    }

    @Override
    public void setStatus(int value) {
	if (progressBar != null) {
	    progressBar.setValue(value);
	}
    }

    public void setClosable(boolean dispose) {
	final boolean disposable = dispose;
	addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
		setVisible(false);
		if (disposable) {
		    dispose();
		}
	    }
	});
    }

    public void setTimer(int seconds) {
	final int pause = seconds * 1000;
	final Runnable closerRunner = new Runnable() {
	    public void run() {
		setVisible(false);
		dispose();
	    }
	};
	Runnable waitRunner = new Runnable() {
	    public void run() {
		try {
		    Thread.sleep(pause);
		    SwingUtilities.invokeAndWait(closerRunner);
		} catch (InterruptedException e) {
		    logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
		    logger.error(e.getMessage(), e);
		}
	    }
	};
	setVisible(true);
	splashThread = new Thread(waitRunner, "SplashThread");
    }

    public void run() {
	if (splashThread != null) {
	    splashThread.start();
	}
	setVisible(true);
    }

    public static void main(String[] args) {
	SplashWindow splash =
		new SplashWindow(SplashWindow.class
			.getResource("/splashtest.jpg"), 200, 150);
	splash.setClosable(true);
	// splash.setTimer(10);
	splash.showProgressBar(true);
	splash.showText(true);
	splash.setText(null);
	splash.setRange(0, 99);
	// Thread t = new Thread(splash,"splash");
	// t.run();
	splash.run();
	for (int i = 0; i < 100; i++) {
	    try {
		Thread.sleep(100);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    System.out.println(i);
	    // splash.setProgressBarText("Step: " + String.valueOf(i));
	    splash.setStatus(i);
	}
	System.out.println("Fertig...");
    }

    @Override
    public void finish() {
	dispose();
    }
}
