package assembler;

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
}
