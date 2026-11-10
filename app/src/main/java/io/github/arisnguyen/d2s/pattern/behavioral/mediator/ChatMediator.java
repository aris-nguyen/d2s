package io.github.arisnguyen.d2s.pattern.behavioral.mediator;

public interface ChatMediator {
    void sendMessage(String message, User user);
    void addUser(User user);
}
