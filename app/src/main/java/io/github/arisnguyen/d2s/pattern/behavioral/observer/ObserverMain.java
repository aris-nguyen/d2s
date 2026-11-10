package io.github.arisnguyen.d2s.pattern.behavioral.observer;

public class ObserverMain {
    public static void main(String[] args) {
        NewsAgency agency = new NewsAgency();

        NewsChannel bbc = new NewsChannel("BBC");
        NewsChannel cnn = new NewsChannel("CNN");

        agency.registerObserver(bbc);
        agency.registerObserver(cnn);

        agency.setNews("Breaking: Antigravity releases new version!");

        System.out.println(bbc.getChannelName() + " received: " + bbc.getNews());
        System.out.println(cnn.getChannelName() + " received: " + cnn.getNews());
    }
}
