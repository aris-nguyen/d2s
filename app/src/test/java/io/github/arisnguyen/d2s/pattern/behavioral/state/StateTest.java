package io.github.arisnguyen.d2s.pattern.behavioral.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StateTest {

    @Test
    void testStateTransitions() {
        AudioPlayer player = new AudioPlayer();
        assertEquals("STOPPED", player.getStatus());
        assertTrue(player.getState() instanceof StoppedState);

        player.pressPlay();
        assertEquals("PLAYING", player.getStatus());
        assertTrue(player.getState() instanceof PlayingState);

        player.pressPlay();
        assertEquals("PAUSED", player.getStatus());
        assertTrue(player.getState() instanceof PausedState);

        player.pressPlay();
        assertEquals("PLAYING", player.getStatus());
        assertTrue(player.getState() instanceof PlayingState);

        player.pressStop();
        assertEquals("STOPPED", player.getStatus());
        assertTrue(player.getState() instanceof StoppedState);
    }
}
