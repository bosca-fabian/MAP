package Utils;

public interface ServiceObserver {

    void addObserver(ControllerObserver e);
    void notifyObservers();
}
