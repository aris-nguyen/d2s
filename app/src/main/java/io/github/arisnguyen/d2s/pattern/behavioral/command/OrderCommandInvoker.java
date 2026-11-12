package io.github.arisnguyen.d2s.pattern.behavioral.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class OrderCommandInvoker {
    private final Deque<OrderCommand> executedCommands = new ArrayDeque<>();
    private final List<String> auditLogs = new ArrayList<>();

    public void executeCommand(OrderCommand command) {
        command.execute();
        executedCommands.push(command);
        auditLogs.add("EXECUTED: " + command.getDescription());
    }

    public void undoLastCommand() {
        if (!executedCommands.isEmpty()) {
            OrderCommand command = executedCommands.pop();
            command.undo();
            auditLogs.add("UNDONE: " + command.getDescription());
        }
    }

    public boolean canUndo() {
        return !executedCommands.isEmpty();
    }

    public List<String> getAuditLogs() {
        return auditLogs;
    }
}
