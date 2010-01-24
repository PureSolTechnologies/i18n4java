package javax.swingx.progress;

public interface ProgressObservable extends Runnable {

    public void setMonitor(ProgressObserver observer);
}
