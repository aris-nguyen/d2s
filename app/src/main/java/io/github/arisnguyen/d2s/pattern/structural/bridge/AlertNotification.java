package io.github.arisnguyen.d2s.pattern.structural.bridge;

/**
 * Refined Abstraction for urgent/alert notifications.
 */
public class AlertNotification extends Notification {
    public enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    private final Priority priority;

    public AlertNotification(MessageSender sender, Priority priority) {
        super(sender);
        this.priority = priority != null ? priority : Priority.MEDIUM;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public void send(String recipient, String message) {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("recipient must not be null or blank");
        }
        String formatted = String.format("[ALERT - %s] %s", priority, message != null ? message : "");
        sender.sendMessage(recipient, formatted);
    }
}
