package io.github.arisnguyen.d2s.pattern.behavioral.chain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChainOfResponsibilityTest {

    private StringBuilder logSink;
    private Logger loggerChain;

    @BeforeEach
    void setUp() {
        logSink = new StringBuilder();
        Logger info = new InfoLogger(logSink);
        Logger debug = new DebugLogger(logSink);
        Logger error = new ErrorLogger(logSink);

        info.setNext(debug).setNext(error);
        loggerChain = info;
    }

    @Test
    void testInfoLogging() {
        loggerChain.logMessage(LogLevel.INFO, "Server running");
        String output = logSink.toString();
        assertTrue(output.contains("[INFO]: Server running"));
        assertFalse(output.contains("[DEBUG]"));
        assertFalse(output.contains("[ERROR]"));
    }

    @Test
    void testDebugLogging() {
        loggerChain.logMessage(LogLevel.DEBUG, "Variable x = 10");
        String output = logSink.toString();
        assertTrue(output.contains("[DEBUG]: Variable x = 10"));
        assertFalse(output.contains("[INFO]"));
        assertFalse(output.contains("[ERROR]"));
    }

    @Test
    void testErrorLogging() {
        loggerChain.logMessage(LogLevel.ERROR, "Out of memory");
        String output = logSink.toString();
        assertTrue(output.contains("[ERROR]: Out of memory"));
        assertFalse(output.contains("[INFO]"));
        assertFalse(output.contains("[DEBUG]"));
    }
}
