package io.github.arisnguyen.d2s.pattern.structural.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Proxy: Controls access to RealVideoService by providing transparent caching.
 */
public class CachedVideoProxy implements VideoService {
    private final VideoService realService;
    private final Map<String, Video> cache = new HashMap<>();
    private int cacheHitCount = 0;
    private int cacheMissCount = 0;

    public CachedVideoProxy(VideoService realService) {
        this.realService = Objects.requireNonNull(realService, "realService must not be null");
    }

    @Override
    public Video getVideo(String videoId) {
        if (videoId == null || videoId.isBlank()) {
            throw new IllegalArgumentException("videoId must not be null or blank");
        }
        String key = videoId.trim();
        if (cache.containsKey(key)) {
            cacheHitCount++;
            return cache.get(key);
        }

        cacheMissCount++;
        Video video = realService.getVideo(key);
        if (video != null) {
            cache.put(key, video);
        }
        return video;
    }

    public void invalidate(String videoId) {
        if (videoId != null) {
            cache.remove(videoId.trim());
        }
    }

    public void clearCache() {
        cache.clear();
    }

    public int getCacheHitCount() {
        return cacheHitCount;
    }

    public int getCacheMissCount() {
        return cacheMissCount;
    }

    public int getCacheSize() {
        return cache.size();
    }
}
