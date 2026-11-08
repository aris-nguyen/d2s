package io.github.arisnguyen.d2s.pattern.structural.proxy;

public class ProxyMain {
    public static void main(String[] args) {
        VideoService service = new CachedVideoProxy(new RealVideoService());

        Video v1 = service.getVideo("vid-01");
        System.out.println("First call: " + v1.getTitle());

        Video v2 = service.getVideo("vid-01");
        System.out.println("Second call (same instance): " + (v1 == v2));
    }
}
