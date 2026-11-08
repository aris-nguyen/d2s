package io.github.arisnguyen.d2s.pattern.creational.builder;

public class BuilderMain {
    public static void main(String[] args) {
        User user = User.builder("johndoe", "john@example.com")
                .firstName("John")
                .lastName("Doe")
                .age(28)
                .phone("+123456789")
                .build();

        System.out.println("User: " + user.getUsername() + " (" + user.getEmail() + ")");
    }
}

