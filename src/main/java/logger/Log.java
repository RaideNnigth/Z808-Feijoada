package logger;

public class Log {
    private LogType logType;
    private int line;
    private String message;
    public Log(LogType logType, int line, String message) {
        this.logType = logType;
        this.line = line;
        this.message = message;
    }

    public LogType getLogType() {
        return logType;
    }

    public int getLine() {
        return line;
    }

    public String getMessage() {
        return message;
    }
}
