package logger;

import utils.Observable;
import utils.Observer;

import java.util.LinkedList;

public class Logger extends Observable {
    private final LinkedList<Log> logs = new LinkedList<>();
    private static Logger instance = null;

    private Logger() {
        super();
    }

    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();
        return instance;
    }

    public void addLog(Log log) {
        this.logs.add(log);
        this.notifyObservers();
    }

    public void error(int lineNumber, String message) {
        Log log = new Log(LogType.ERROR, lineNumber, message);
        this.addLog(log);
    }

    public void error(String message) {
        Log log = new Log(LogType.ERROR, 0, message);
        this.addLog(log);
    }

    public void warning(int lineNumber, String message) {
        Log log = new Log(LogType.ERROR, lineNumber, message);
        this.addLog(log);
    }

    public void info(int lineNumber, String message) {
        Log log = new Log(LogType.INFO, lineNumber, message);
        this.addLog(log);
    }

    public void info(String message) {
        Log log = new Log(LogType.INFO, 0, message);
        this.addLog(log);
    }

    public void debug(int lineNumber, String message) {
        Log log = new Log(LogType.INFO, lineNumber, message);
        this.addLog(log);
    }

    public void reset() {
        logs.clear();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(String.format("%s (at line %d) : %s", this.logs.getLast().getLogType(), this.logs.getLast().getLine(), this.logs.getLast().getMessage()));
        }
    }

    public LinkedList<Log> getLogs() {
        return logs;
    }
}
