package logger;

import z808_gui.components.LogTextArea;

import java.util.LinkedList;

public class Logger extends Observable {
    private final LinkedList<Log> logs = new LinkedList<>();
    private Log lastLog;
    private static Logger instance = null;
    private boolean interrupted;

    private Logger() {
        super();
        interrupted = false;
    }

    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();
        return instance;
    }

    public void addLog(Log log) {
        this.logs.add(log);
        this.lastLog = log;

        if (log.getLogType() == LogType.ERROR) {
            interrupted = true;
        }

        this.notifyObservers();
    }

    /*
    public void printLogs() {
        var logArea = LogTextArea.getInstance();
        for (Log log : logs) {
            logArea.appendText(log.getMessage());
        }
    }*/

    public boolean isInterrupted() {
        return interrupted;
    }

    public void reset() {
        logs.clear();
        lastLog = null;
        //LogTextArea.getInstance().clearText();
    }

    @Override
    public void notifyObservers() {
        if (this.lastLog == null)
            return;

        for (Observer observer : this.observers) {
            observer.update(String.format("%s (at line %d) : %s", this.lastLog.getLogType(), this.lastLog.getLine(), this.lastLog.getMessage()));
        }
    }

    public LinkedList<Log> getLogs() {
        return logs;
    }
}
