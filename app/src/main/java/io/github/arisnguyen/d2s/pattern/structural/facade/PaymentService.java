package io.github.arisnguyen.d2s.pattern.structural.facade;

import java.util.HashSet;
import java.util.Set;

/**
 * Subsystem 2: Handles billing and payment transactions.
 */
public class PaymentService {
    private final Set<String> blockedAccounts = new HashSet<>();

    public void blockAccount(String accountId) {
        if (accountId != null) {
            blockedAccounts.add(accountId);
        }
    }

    public boolean processPayment(String accountId, double amount) {
        if (accountId == null || accountId.isBlank() || amount <= 0) {
            return false;
        }
        return !blockedAccounts.contains(accountId);
    }
}
