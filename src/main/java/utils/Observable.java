package utils;

import java.util.LinkedList;

public abstract class Observable {
    protected final LinkedList<Observer> observers = new LinkedList<>();

    public void subscribe(Observer ob) {
        if (ob != null)
            observers.add(ob);
    }

    public abstract void notifyObservers();
}
