package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class ErrorLogger extends Logger {
    private final StringBuilder outputLog;

    public ErrorLogger(StringBuilder outputLog) {
        super(LogLevel.ERROR);
        this.outputLog = outputLog;
    }

    @Override
    protected void write(String message) {
        outputLog.append("[ERROR]: ").append(message).append("\n");
    }
}
