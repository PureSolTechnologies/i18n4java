package javax.swingx.progress;

public interface ProgressObserver {

    public void setRange(int min, int max);

    public void setStatus(int status);

    public void setText(String text);

    public void finish();

}
