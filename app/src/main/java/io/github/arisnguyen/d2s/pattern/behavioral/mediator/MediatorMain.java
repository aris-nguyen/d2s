package io.github.arisnguyen.d2s.pattern.behavioral.mediator;

public class MediatorMain {
    public static void main(String[] args) {
        ChatMediator chatRoom = new ChatRoomMediator();

        User alice = new ChatUser(chatRoom, "Alice");
        User bob = new ChatUser(chatRoom, "Bob");
        User charlie = new ChatUser(chatRoom, "Charlie");

        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);

        alice.send("Hello everyone!");
        bob.send("Hi Alice!");

        System.out.println("Alice received: " + ((ChatUser) alice).getReceivedMessages());
        System.out.println("Bob received: " + ((ChatUser) bob).getReceivedMessages());
        System.out.println("Charlie received: " + ((ChatUser) charlie).getReceivedMessages());
    }
}
