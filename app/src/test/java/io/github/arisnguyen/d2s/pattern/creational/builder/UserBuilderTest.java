package io.github.arisnguyen.d2s.pattern.creational.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserBuilderTest {

    @Test
    @DisplayName("Should build User with mandatory fields only")
    public void testBuildUserWithMandatoryFieldsOnly() {
        User user = User.builder("john_doe", "john@example.com")
                .build();

        assertNotNull(user);
        assertEquals("john_doe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertEquals(0, user.getAge());
        assertNull(user.getPhone());
    }

    @Test
    @DisplayName("Should build User with all optional fields configured")
    public void testBuildUserWithAllFields() {
        User user = User.builder("jane_doe", "jane@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .age(28)
                .phone("+123456789")
                .build();

        assertEquals("jane_doe", user.getUsername());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(28, user.getAge());
        assertEquals("+123456789", user.getPhone());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when email is invalid")
    public void testInvalidEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                User.builder("alex", "invalid-email").build());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when username is empty")
    public void testEmptyUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                User.builder("   ", "alex@example.com").build());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when age is negative")
    public void testNegativeAgeThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                User.builder("alex", "alex@example.com").age(-5).build());
    }

    @Test
    @DisplayName("Should throw NullPointerException when mandatory fields are null")
    public void testNullMandatoryFieldsThrowsException() {
        assertThrows(NullPointerException.class, () -> User.builder(null, "alex@example.com"));
        assertThrows(NullPointerException.class, () -> User.builder("alex", null));
    }
}
