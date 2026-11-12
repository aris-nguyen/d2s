package io.github.arisnguyen.d2s.pattern.behavioral.command;

public interface OrderCommand {
    void execute();
    void undo();
    String getDescription();
}
