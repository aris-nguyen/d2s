package io.github.arisnguyen.d2s.pattern.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class EmailNotificationService implements OrderObserver {
    private final List<String> sentEmails = new ArrayList<>();

    @Override
    public void onOrderStatusChanged(OrderEvent event) {
        String msg = "Email to " + event.getCustomerEmail() + ": Order " + event.getOrderId()
                + " status is now " + event.getStatus();
        sentEmails.add(msg);
    }

    public List<String> getSentEmails() {
        return sentEmails;
    }
}
