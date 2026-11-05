package io.github.arisnguyen.d2s.pattern.structural.proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Proxy Pattern Tests - Video Streaming Service Proxy")
class CachedVideoProxyTest {

    private RealVideoService realService;
    private CachedVideoProxy proxy;

    @BeforeEach
    void setUp() {
        realService = new RealVideoService();
        proxy = new CachedVideoProxy(realService);
    }

    @Test
    @DisplayName("Should fetch from real service on first call and return cached copy on subsequent calls")
    void testCacheHitAndMiss() {
        // First access: Cache Miss
        Video video1 = proxy.getVideo("vid-01");
        assertNotNull(video1);
        assertEquals("Design Patterns Tutorial", video1.getTitle());
        assertEquals(1, realService.getRemoteFetchCount());
        assertEquals(1, proxy.getCacheMissCount());
        assertEquals(0, proxy.getCacheHitCount());

        // Second access: Cache Hit
        Video video2 = proxy.getVideo("vid-01");
        assertSame(video1, video2);
        // Remote fetch count must remain 1
        assertEquals(1, realService.getRemoteFetchCount());
        assertEquals(1, proxy.getCacheMissCount());
        assertEquals(1, proxy.getCacheHitCount());
    }

    @Test
    @DisplayName("Should fetch again from real service after cache invalidation")
    void testCacheInvalidation() {
        proxy.getVideo("vid-02");
        assertEquals(1, realService.getRemoteFetchCount());

        proxy.invalidate("vid-02");
        assertEquals(0, proxy.getCacheSize());

        // Should trigger new fetch
        proxy.getVideo("vid-02");
        assertEquals(2, realService.getRemoteFetchCount());
        assertEquals(2, proxy.getCacheMissCount());
    }

    @Test
    @DisplayName("Should return null and not cache non-existent videos")
    void testNonExistentVideo() {
        Video video = proxy.getVideo("unknown-id");
        assertNull(video);
        assertEquals(1, proxy.getCacheMissCount());
        assertEquals(0, proxy.getCacheSize());
    }

    @Test
    @DisplayName("Should throw exception for null or blank videoId")
    void testInvalidVideoId() {
        assertThrows(IllegalArgumentException.class, () -> proxy.getVideo(null));
        assertThrows(IllegalArgumentException.class, () -> proxy.getVideo("   "));
        assertThrows(NullPointerException.class, () -> new CachedVideoProxy(null));
    }
}
