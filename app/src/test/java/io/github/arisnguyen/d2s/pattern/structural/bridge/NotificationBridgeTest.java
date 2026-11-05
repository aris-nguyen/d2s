package io.github.arisnguyen.d2s.pattern.structural.bridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bridge Pattern Tests - Notification System")
class NotificationBridgeTest {

    @Test
    @DisplayName("Should send alert notification via Email sender")
    void testAlertNotificationViaEmail() {
        MessageSender emailSender = new EmailMessageSender();
        Notification alert = new AlertNotification(emailSender, AlertNotification.Priority.CRITICAL);

        alert.send("admin@company.com", "Server CPU load exceeds 95%");

        assertEquals(
            "[EMAIL to admin@company.com] [ALERT - CRITICAL] Server CPU load exceeds 95%",
            emailSender.getLastMessageSent()
        );
    }

    @Test
    @DisplayName("Should send alert notification via SMS sender")
    void testAlertNotificationViaSms() {
        MessageSender smsSender = new SmsMessageSender();
        Notification alert = new AlertNotification(smsSender, AlertNotification.Priority.HIGH);

        alert.send("+1234567890", "Database connection lost");

        assertEquals(
            "[SMS to +1234567890] [ALERT - HIGH] Database connection lost",
            smsSender.getLastMessageSent()
        );
    }

    @Test
    @DisplayName("Should send summary notification via Slack sender")
    void testSummaryNotificationViaSlack() {
        MessageSender slackSender = new SlackMessageSender();
        SummaryNotification summary = new SummaryNotification(slackSender);
        summary.addItem("Bug #101 closed")
               .addItem("Feature #202 deployed")
               .addItem("PR #303 merged");

        summary.send("#dev-team", "Sprint Highlights");

        assertEquals(
            "[SLACK to #dev-team] [SUMMARY: Sprint Highlights] 3 item(s): Bug #101 closed, Feature #202 deployed, PR #303 merged",
            slackSender.getLastMessageSent()
        );
    }

    @Test
    @DisplayName("Should throw exception when sender is null")
    void testNullSenderThrowsException() {
        assertThrows(NullPointerException.class, () ->
            new AlertNotification(null, AlertNotification.Priority.MEDIUM)
        );
    }

    @Test
    @DisplayName("Should throw exception when recipient is blank")
    void testBlankRecipientThrowsException() {
        MessageSender emailSender = new EmailMessageSender();
        Notification alert = new AlertNotification(emailSender, AlertNotification.Priority.LOW);

        assertThrows(IllegalArgumentException.class, () -> alert.send("  ", "Hello"));
    }
}
