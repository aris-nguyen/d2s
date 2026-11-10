package io.github.arisnguyen.d2s.pattern.behavioral.state;

public interface State {
    void pressPlay(AudioPlayer player);
    void pressStop(AudioPlayer player);
    String getStatus();
}
