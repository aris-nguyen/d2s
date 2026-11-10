package io.github.arisnguyen.d2s.pattern.behavioral.observer;

public class NewsChannel implements Observer {
    private final String channelName;
    private String news;

    public NewsChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void update(String news) {
        this.news = news;
    }

    public String getNews() {
        return news;
    }

    public String getChannelName() {
        return channelName;
    }
}
