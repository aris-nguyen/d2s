package io.github.arisnguyen.d2s.pattern.structural.proxy;

/**
 * Data Model representing a digital video entity.
 */
public class Video {
    private final String id;
    private final String title;
    private final int durationSeconds;

    public Video(String id, String title, int durationSeconds) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be null or blank");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be null or blank");
        }
        if (durationSeconds <= 0) {
            throw new IllegalArgumentException("durationSeconds must be positive");
        }
        this.id = id.trim();
        this.title = title.trim();
        this.durationSeconds = durationSeconds;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }
}
