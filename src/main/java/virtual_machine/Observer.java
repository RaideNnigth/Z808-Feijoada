package virtual_machine;

public interface Observer {
    public void update();
    public void setObservable(Observable observable);
}
