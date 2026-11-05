package io.github.arisnguyen.d2s.pattern.structural.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Real Subject: Simulates downloading or fetching a video from a remote streaming server.
 */
public class RealVideoService implements VideoService {
    private final Map<String, Video> database = new HashMap<>();
    private int remoteFetchCount = 0;

    public RealVideoService() {
        // Pre-populate some dummy remote videos
        database.put("vid-01", new Video("vid-01", "Design Patterns Tutorial", 1200));
        database.put("vid-02", new Video("vid-02", "Data Structures in Java", 1800));
        database.put("vid-03", new Video("vid-03", "System Design Primer", 2400));
    }

    @Override
    public Video getVideo(String videoId) {
        if (videoId == null || videoId.isBlank()) {
            throw new IllegalArgumentException("videoId must not be null or blank");
        }
        remoteFetchCount++;
        return database.get(videoId.trim());
    }

    public int getRemoteFetchCount() {
        return remoteFetchCount;
    }
}
