package io.github.arisnguyen.d2s.pattern.behavioral.mediator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MediatorTest {

    @Test
    void testMessageBroadcasting() {
        ChatMediator chatRoom = new ChatRoomMediator();

        ChatUser user1 = new ChatUser(chatRoom, "User1");
        ChatUser user2 = new ChatUser(chatRoom, "User2");
        ChatUser user3 = new ChatUser(chatRoom, "User3");

        chatRoom.addUser(user1);
        chatRoom.addUser(user2);
        chatRoom.addUser(user3);

        user1.send("Hello Team");

        assertTrue(user1.getReceivedMessages().isEmpty());
        assertEquals(List.of("Hello Team"), user2.getReceivedMessages());
        assertEquals(List.of("Hello Team"), user3.getReceivedMessages());

        user2.send("Welcome!");

        assertEquals(List.of("Welcome!"), user1.getReceivedMessages());
        assertEquals(List.of("Hello Team"), user2.getReceivedMessages());
        assertEquals(List.of("Hello Team", "Welcome!"), user3.getReceivedMessages());
    }
}
