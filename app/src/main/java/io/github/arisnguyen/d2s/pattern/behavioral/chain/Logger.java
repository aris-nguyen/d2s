package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public abstract class Logger {
    private final LogLevel level;
    private Logger nextLogger;

    protected Logger(LogLevel level) {
        this.level = level;
    }

    public Logger setNext(Logger nextLogger) {
        this.nextLogger = nextLogger;
        return nextLogger;
    }

    public void logMessage(LogLevel level, String message) {
        if (this.level == level) {
            write(message);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }

    protected abstract void write(String message);
}
