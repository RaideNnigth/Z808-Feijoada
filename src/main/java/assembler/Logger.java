package assembler;

import z808_gui.components.LogTextArea;

import java.util.LinkedList;

public class Logger {
    private final LinkedList<Log> logs = new LinkedList<>();
    private static Logger instance = null;
    private boolean interrupted;

    private Logger() {
        interrupted = false;
    }

    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();
        return instance;
    }

    public void addLog(Log log) {
        logs.add(log);

        if (log.getLogType() == LogType.ERROR) {
            interrupted = true;
        }
    }

    public void printLogs() {
        var logArea = LogTextArea.getInstance();
        for (Log log : logs) {
            logArea.appendText(log.getMessage());
        }
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void reset() {
        logs.clear();
        LogTextArea.getInstance().clearText();
    }
}
