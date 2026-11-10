package io.github.arisnguyen.d2s.pattern.behavioral.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class History {
    private final Deque<EditorMemento> history = new ArrayDeque<>();

    public void push(EditorMemento memento) {
        history.push(memento);
    }

    public EditorMemento pop() {
        return history.isEmpty() ? null : history.pop();
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
}
