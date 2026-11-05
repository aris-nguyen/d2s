package io.github.arisnguyen.d2s.pattern.structural.proxy;

/**
 * Subject interface declaring the common contract for video operations.
 */
public interface VideoService {
    Video getVideo(String videoId);
}
