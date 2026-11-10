package io.github.arisnguyen.d2s.pattern.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomMediator implements ChatMediator {
    private final List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void sendMessage(String message, User sender) {
        for (User user : users) {
            // Message should not be received by the sender
            if (user != sender) {
                user.receive(message);
            }
        }
    }

    public List<User> getUsers() {
        return users;
    }
}
