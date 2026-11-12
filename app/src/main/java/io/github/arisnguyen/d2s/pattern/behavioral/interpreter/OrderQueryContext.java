package io.github.arisnguyen.d2s.pattern.behavioral.interpreter;

import java.util.Collections;
import java.util.Map;

public class OrderQueryContext {
    private final Map<String, Object> attributes;

    public OrderQueryContext(Map<String, Object> attributes) {
        this.attributes = attributes != null ? attributes : Collections.emptyMap();
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }
}
