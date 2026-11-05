package io.github.arisnguyen.d2s.pattern.creational.builder;

import java.util.Objects;

/**
 * Real-world Builder pattern: Immutable User account object.
 * Solves the telescoping constructor anti-pattern when an object has many optional attributes.
 */
public final class User {

    // Mandatory fields
    private final String username;
    private final String email;

    // Optional fields
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String phone;

    private User(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
    }

    public static Builder builder(String username, String email) {
        return new Builder(username, email);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }

    /**
     * Fluent Builder for constructing immutable User instances.
     */
    public static final class Builder {

        private final String username;
        private final String email;
        private String firstName;
        private String lastName;
        private int age;
        private String phone;

        public Builder(String username, String email) {
            this.username = Objects.requireNonNull(username, "Username cannot be null");
            this.email = Objects.requireNonNull(email, "Email cannot be null");
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public User build() {
            if (username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            if (!email.contains("@")) {
                throw new IllegalArgumentException("Invalid email format");
            }
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            return new User(this);
        }
    }
}
