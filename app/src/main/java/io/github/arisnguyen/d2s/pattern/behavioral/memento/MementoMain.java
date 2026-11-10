package io.github.arisnguyen.d2s.pattern.behavioral.memento;

public class MementoMain {
    public static void main(String[] args) {
        Editor editor = new Editor();
        History history = new History();

        editor.setContent("Version 1");
        history.push(editor.save());
        System.out.println("Current: " + editor.getContent());

        editor.setContent("Version 2");
        history.push(editor.save());
        System.out.println("Current: " + editor.getContent());

        editor.setContent("Version 3");
        System.out.println("Current: " + editor.getContent());

        editor.restore(history.pop());
        System.out.println("Restored: " + editor.getContent());

        editor.restore(history.pop());
        System.out.println("Restored: " + editor.getContent());
    }
}
