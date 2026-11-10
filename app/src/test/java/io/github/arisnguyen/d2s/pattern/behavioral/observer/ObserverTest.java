package io.github.arisnguyen.d2s.pattern.behavioral.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ObserverTest {

    @Test
    void testObserverReceivesUpdate() {
        NewsAgency agency = new NewsAgency();
        NewsChannel ch1 = new NewsChannel("CH1");
        NewsChannel ch2 = new NewsChannel("CH2");

        agency.registerObserver(ch1);
        agency.registerObserver(ch2);

        agency.setNews("Market update");
        assertEquals("Market update", ch1.getNews());
        assertEquals("Market update", ch2.getNews());
    }

    @Test
    void testRemoveObserver() {
        NewsAgency agency = new NewsAgency();
        NewsChannel ch1 = new NewsChannel("CH1");
        NewsChannel ch2 = new NewsChannel("CH2");

        agency.registerObserver(ch1);
        agency.registerObserver(ch2);
        agency.removeObserver(ch2);

        agency.setNews("Weather update");
        assertEquals("Weather update", ch1.getNews());
        assertNull(ch2.getNews());
    }
}
