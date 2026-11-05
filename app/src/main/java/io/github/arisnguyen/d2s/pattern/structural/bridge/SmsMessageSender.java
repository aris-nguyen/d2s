package io.github.arisnguyen.d2s.pattern.structural.bridge;

/**
 * Concrete Implementor: Sends messages via SMS.
 */
public class SmsMessageSender implements MessageSender {
    private String lastMessageSent;

    @Override
    public void sendMessage(String recipient, String message) {
        this.lastMessageSent = String.format("[SMS to %s] %s", recipient, message);
    }

    @Override
    public String getLastMessageSent() {
        return lastMessageSent;
    }
}
