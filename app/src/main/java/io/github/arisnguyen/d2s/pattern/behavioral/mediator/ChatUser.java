package io.github.arisnguyen.d2s.pattern.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

public class ChatUser extends User {
    private final List<String> receivedMessages = new ArrayList<>();

    public ChatUser(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void send(String message) {
        mediator.sendMessage(message, this);
    }

    @Override
    public void receive(String message) {
        receivedMessages.add(message);
    }

    public List<String> getReceivedMessages() {
        return receivedMessages;
    }
}
