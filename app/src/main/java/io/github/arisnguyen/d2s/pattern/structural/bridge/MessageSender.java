package io.github.arisnguyen.d2s.pattern.structural.bridge;

/**
 * Bridge Implementor interface for transport/delivery mechanisms.
 */
public interface MessageSender {
    void sendMessage(String recipient, String message);
    String getLastMessageSent();
}
