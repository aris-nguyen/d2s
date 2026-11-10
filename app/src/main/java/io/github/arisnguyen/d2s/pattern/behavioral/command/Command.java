package io.github.arisnguyen.d2s.pattern.behavioral.command;

public interface Command {
    void execute();
    void undo();
}
