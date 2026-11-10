package io.github.arisnguyen.d2s.pattern.behavioral.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class RemoteControl {
    private final Deque<Command> history = new ArrayDeque<>();

    public void submit(Command command) {
        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            command.undo();
        }
    }

    public boolean canUndo() {
        return !history.isEmpty();
    }
}
