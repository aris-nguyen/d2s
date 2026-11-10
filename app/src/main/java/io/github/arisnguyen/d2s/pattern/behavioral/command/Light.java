package io.github.arisnguyen.d2s.pattern.behavioral.command;

public class Light {
    private boolean on;

    public void turnOn() {
        this.on = true;
    }

    public void turnOff() {
        this.on = false;
    }

    public boolean isOn() {
        return on;
    }
}
