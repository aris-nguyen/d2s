package io.github.arisnguyen.d2s.pattern.behavioral.state;

public class AudioPlayer {
    private State state;

    public AudioPlayer() {
        this.state = new StoppedState();
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void pressPlay() {
        state.pressPlay(this);
    }

    public void pressStop() {
        state.pressStop(this);
    }

    public String getStatus() {
        return state.getStatus();
    }
}
