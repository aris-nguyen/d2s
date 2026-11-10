package io.github.arisnguyen.d2s.pattern.behavioral.state;

public class StateMain {
    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();
        System.out.println("Initial status: " + player.getStatus());

        player.pressPlay();
        System.out.println("After pressPlay: " + player.getStatus());

        player.pressPlay();
        System.out.println("After pressPlay (pause): " + player.getStatus());

        player.pressPlay();
        System.out.println("After pressPlay (resume): " + player.getStatus());

        player.pressStop();
        System.out.println("After pressStop: " + player.getStatus());
    }
}
