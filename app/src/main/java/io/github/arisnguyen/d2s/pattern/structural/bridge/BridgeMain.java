package io.github.arisnguyen.d2s.pattern.structural.bridge;

public class BridgeMain {
    public static void main(String[] args) {
        MessageSender emailSender = new EmailMessageSender();
        MessageSender slackSender = new SlackMessageSender();

        Notification alert = new AlertNotification(emailSender, AlertNotification.Priority.CRITICAL);
        alert.send("admin@company.com", "Server CPU load exceeds 95%");

        SummaryNotification summary = new SummaryNotification(slackSender);
        summary.addItem("Task #1 completed");
        summary.send("#general", "Daily Report");
    }
}
