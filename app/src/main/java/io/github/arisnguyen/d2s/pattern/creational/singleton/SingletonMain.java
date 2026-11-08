package io.github.arisnguyen.d2s.pattern.creational.singleton;

public class SingletonMain {
    public static void main(String[] args) {
        DatabaseConnection conn1 = DatabaseConnection.getInstance();
        DatabaseConnection conn2 = DatabaseConnection.getInstance();

        conn1.connect();
        System.out.println("Same instance: " + (conn1 == conn2));
        System.out.println("Connected: " + conn2.isConnected());
        System.out.println(conn2.executeQuery("SELECT * FROM users"));
        conn1.disconnect();
    }
}
