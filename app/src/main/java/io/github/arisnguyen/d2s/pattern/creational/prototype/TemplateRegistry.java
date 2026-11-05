package io.github.arisnguyen.d2s.pattern.creational.prototype;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Registry storing pre-initialized prototype templates by key.
 */
public class TemplateRegistry {

    private final Map<String, Prototype<?>> registry = new HashMap<>();

    public void register(String key, Prototype<?> prototype) {
        Objects.requireNonNull(key, "Key cannot be null");
        Objects.requireNonNull(prototype, "Prototype cannot be null");
        registry.put(key, prototype);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        Prototype<?> prototype = registry.get(key);
        if (prototype == null) {
            throw new NoSuchElementException("No prototype registered for key: " + key);
        }
        return (T) prototype.copy();
    }

    public boolean contains(String key) {
        return registry.containsKey(key);
    }
}
