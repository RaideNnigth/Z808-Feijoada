package logger;

public enum LogType {
    WARNING("WARNING"),
    ERROR("ERROR"),
    INFO("INFO");

    private final String label;

    LogType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
