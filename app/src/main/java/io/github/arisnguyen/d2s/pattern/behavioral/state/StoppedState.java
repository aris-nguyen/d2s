package io.github.arisnguyen.d2s.pattern.behavioral.state;

public class StoppedState implements State {
    @Override
    public void pressPlay(AudioPlayer player) {
        player.setState(new PlayingState());
    }

    @Override
    public void pressStop(AudioPlayer player) {
        // Already stopped, no action
    }

    @Override
    public String getStatus() {
        return "STOPPED";
    }
}
