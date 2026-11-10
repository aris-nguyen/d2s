package io.github.arisnguyen.d2s.pattern.behavioral.state;

public class PlayingState implements State {
    @Override
    public void pressPlay(AudioPlayer player) {
        player.setState(new PausedState());
    }

    @Override
    public void pressStop(AudioPlayer player) {
        player.setState(new StoppedState());
    }

    @Override
    public String getStatus() {
        return "PLAYING";
    }
}
