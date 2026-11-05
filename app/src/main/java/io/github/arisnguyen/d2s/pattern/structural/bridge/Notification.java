package io.github.arisnguyen.d2s.pattern.structural.bridge;

import java.util.Objects;

/**
 * Abstraction in the Bridge pattern.
 */
public abstract class Notification {
    protected final MessageSender sender;

    protected Notification(MessageSender sender) {
        this.sender = Objects.requireNonNull(sender, "sender must not be null");
    }

    public MessageSender getSender() {
        return sender;
    }

    public abstract void send(String recipient, String message);
}
