package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class InfoLogger extends Logger {
    private final StringBuilder outputLog;

    public InfoLogger(StringBuilder outputLog) {
        super(LogLevel.INFO);
        this.outputLog = outputLog;
    }

    @Override
    protected void write(String message) {
        outputLog.append("[INFO]: ").append(message).append("\n");
    }
}
