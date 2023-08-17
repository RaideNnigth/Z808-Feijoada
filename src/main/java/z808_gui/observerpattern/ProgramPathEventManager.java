package z808_gui.observerpattern;

import java.util.LinkedList;

public class ProgramPathEventManager {
    private static ProgramPathEventManager instance = null;
    private static final LinkedList<Listener> subscribers = new LinkedList<>();

    private ProgramPathEventManager() {
    }

    public static ProgramPathEventManager getInstance() {
        if (instance == null) {
            instance = new ProgramPathEventManager();
        }

        return instance;
    }

    public void subscribe(Listener l) {
        subscribers.add(l);
    }

    public void notifySubscribers(MessageType t) {
        for (Listener l : subscribers) {
            l.update(t);
        }
    }
}
