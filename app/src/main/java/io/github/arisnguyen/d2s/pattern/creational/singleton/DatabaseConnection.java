package io.github.arisnguyen.d2s.pattern.creational.singleton;

/**
 * Real-world Singleton: Database Connection Manager.
 * Ensures the entire application shares a single database connection instance.
 */
public final class DatabaseConnection {

    private static volatile DatabaseConnection instance;

    private final String url;
    private boolean connected;

    private DatabaseConnection() {
        this.url = "jdbc:postgresql://localhost:5432/app_db";
        this.connected = false;
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public void connect() {
        this.connected = true;
    }

    public void disconnect() {
        this.connected = false;
    }

    public String executeQuery(String sql) {
        if (!connected) {
            throw new IllegalStateException("Database is not connected. Call connect() first.");
        }
        return "Query result for: " + sql;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getUrl() {
        return url;
    }
}
