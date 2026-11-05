package io.github.arisnguyen.d2s.pattern.structural.bridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Refined Abstraction for batched/summary notifications.
 */
public class SummaryNotification extends Notification {
    private final List<String> items = new ArrayList<>();

    public SummaryNotification(MessageSender sender) {
        super(sender);
    }

    public SummaryNotification addItem(String item) {
        if (item != null && !item.isBlank()) {
            items.add(item.trim());
        }
        return this;
    }

    public List<String> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public void send(String recipient, String title) {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("recipient must not be null or blank");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[SUMMARY: ").append(title != null ? title : "Daily Digest").append("] ");
        builder.append(items.size()).append(" item(s): ");
        builder.append(String.join(", ", items));

        sender.sendMessage(recipient, builder.toString());
    }
}
