package z808_gui.observerpattern;

import java.util.LinkedList;

public class ProgramPathEventManager {
    private static ProgramPathEventManager instance = null;
    private static final LinkedList<ProgramPathListener> subscribers = new LinkedList<>();

    private ProgramPathEventManager() {
    }

    public static ProgramPathEventManager getInstance() {
        if (instance == null) {
            instance = new ProgramPathEventManager();
        }

        return instance;
    }

    public void subscribe(ProgramPathListener l) {
        subscribers.add(l);
    }

    public void notifySubscribers(MessageType t) {
        for (ProgramPathListener l : subscribers) {
            l.update(t);
        }
    }
}
