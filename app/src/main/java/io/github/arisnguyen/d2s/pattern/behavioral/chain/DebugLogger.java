package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class DebugLogger extends Logger {
    private final StringBuilder outputLog;

    public DebugLogger(StringBuilder outputLog) {
        super(LogLevel.DEBUG);
        this.outputLog = outputLog;
    }

    @Override
    protected void write(String message) {
        outputLog.append("[DEBUG]: ").append(message).append("\n");
    }
}
