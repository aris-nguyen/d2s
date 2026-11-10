package io.github.arisnguyen.d2s.pattern.behavioral.chain;

public class ChainOfResponsibilityMain {
    public static void main(String[] args) {
        StringBuilder logSink = new StringBuilder();
        Logger info = new InfoLogger(logSink);
        Logger debug = new DebugLogger(logSink);
        Logger error = new ErrorLogger(logSink);

        info.setNext(debug).setNext(error);

        info.logMessage(LogLevel.INFO, "System started successfully.");
        info.logMessage(LogLevel.DEBUG, "Loading user configurations.");
        info.logMessage(LogLevel.ERROR, "Failed to connect to database!");

        System.out.println("Captured Logs:\n" + logSink);
    }
}
