package io.github.arisnguyen.d2s.pattern.creational.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DatabaseConnectionTest {

    @Test
    @DisplayName("Should always return the same connection instance")
    public void testSameInstance() {
        DatabaseConnection conn1 = DatabaseConnection.getInstance();
        DatabaseConnection conn2 = DatabaseConnection.getInstance();

        assertNotNull(conn1);
        assertSame(conn1, conn2);
    }

    @Test
    @DisplayName("Should manage connection state and execute queries")
    public void testConnectAndExecuteQuery() {
        DatabaseConnection conn = DatabaseConnection.getInstance();

        conn.connect();
        assertTrue(conn.isConnected());

        String result = conn.executeQuery("SELECT * FROM users");
        assertEquals("Query result for: SELECT * FROM users", result);

        conn.disconnect();
        assertFalse(conn.isConnected());

        assertThrows(IllegalStateException.class, () -> conn.executeQuery("SELECT * FROM users"));
    }

    @Test
    @DisplayName("Should remain thread-safe under concurrent access")
    public void testConcurrentAccess() throws InterruptedException {
        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threadCount);
        Set<DatabaseConnection> instances = Collections.newSetFromMap(new ConcurrentHashMap<>());

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    latch.await();
                    instances.add(DatabaseConnection.getInstance());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }

        latch.countDown();
        done.await();
        executor.shutdown();

        assertEquals(1, instances.size());
    }
}
