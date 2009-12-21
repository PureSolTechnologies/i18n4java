package javax.swingx;

public class MemoryMonitor extends Label implements Runnable {

    private static final long serialVersionUID = 4484584488099486969L;

    private Runtime runtime = null;

    public MemoryMonitor() {
	super();
	init();
    }

    private void init() {
	runtime = Runtime.getRuntime();
	Thread thread = new Thread(this);
	thread.start();
	Application.getInstance().addThread(thread);
    }

    @Override
    public void run() {
	try {
	    while (true) {
		double max =
			(double) runtime.maxMemory() / 1024.0 / 1024.0;
		double total =
			(double) runtime.totalMemory() / 1024.0 / 1024.0;
		double free =
			(double) runtime.freeMemory() / 1024.0 / 1024.0;
		double usage = (max - total) / max * 100.0;
		max = Math.round(max * 100.0) / 100.0;
		total = Math.round(total * 100.0) / 100.0;
		free = Math.round(free * 100.0) / 100.0;
		usage = Math.round(usage * 100.0) / 100.0;
		setText("max: " + max + "MB, total: " + total
			+ "MB, free: " + free + "MB (usage: " + usage
			+ "%)");
		Thread.sleep(1000);
	    }
	} catch (InterruptedException e) {
	}
    }
}
