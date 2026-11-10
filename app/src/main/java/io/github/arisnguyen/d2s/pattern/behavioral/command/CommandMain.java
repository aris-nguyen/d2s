package io.github.arisnguyen.d2s.pattern.behavioral.command;

public class CommandMain {
    public static void main(String[] args) {
        Light livingRoomLight = new Light();
        RemoteControl remote = new RemoteControl();

        Command turnOn = new TurnOnLightCommand(livingRoomLight);
        Command turnOff = new TurnOffLightCommand(livingRoomLight);

        System.out.println("Initial light state: " + livingRoomLight.isOn());

        remote.submit(turnOn);
        System.out.println("After turnOn: " + livingRoomLight.isOn());

        remote.submit(turnOff);
        System.out.println("After turnOff: " + livingRoomLight.isOn());

        remote.undo();
        System.out.println("After undo: " + livingRoomLight.isOn());

        remote.undo();
        System.out.println("After second undo: " + livingRoomLight.isOn());
    }
}
