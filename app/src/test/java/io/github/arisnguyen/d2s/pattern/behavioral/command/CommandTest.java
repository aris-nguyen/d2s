package io.github.arisnguyen.d2s.pattern.behavioral.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandTest {

    private Light light;
    private RemoteControl remote;

    @BeforeEach
    void setUp() {
        light = new Light();
        remote = new RemoteControl();
    }

    @Test
    void testTurnOnCommand() {
        remote.submit(new TurnOnLightCommand(light));
        assertTrue(light.isOn());
    }

    @Test
    void testTurnOffCommand() {
        light.turnOn();
        remote.submit(new TurnOffLightCommand(light));
        assertFalse(light.isOn());
    }

    @Test
    void testUndoCommand() {
        remote.submit(new TurnOnLightCommand(light));
        assertTrue(light.isOn());

        remote.undo();
        assertFalse(light.isOn());
    }

    @Test
    void testMultipleUndo() {
        remote.submit(new TurnOnLightCommand(light));
        remote.submit(new TurnOffLightCommand(light));
        assertFalse(light.isOn());

        remote.undo();
        assertTrue(light.isOn());

        remote.undo();
        assertFalse(light.isOn());
    }
}
