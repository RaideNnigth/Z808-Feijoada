package assembler;

import z808_gui.components.LogTextArea;

import java.util.LinkedList;

public class Logger {
    private final LinkedList<Log> logs = new LinkedList<>();

    public Logger() {

    }

    public void addLog(Log log) {
        logs.add(log);

        if (log.getLogType() == LogType.ERROR) {
            Assembler.getInstance().setLoggerInterruption(true);
        }
    }

    public void printLogs() {
        var logArea = LogTextArea.getInstance();
        for (Log log : logs) {
            logArea.appendText(log.getMessage());
        }
    }

    public void reset() {
        logs.clear();
        LogTextArea.getInstance().clearText();
    }
}
