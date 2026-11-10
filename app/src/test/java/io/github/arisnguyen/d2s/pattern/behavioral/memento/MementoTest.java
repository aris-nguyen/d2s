package io.github.arisnguyen.d2s.pattern.behavioral.memento;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MementoTest {

    @Test
    void testSaveAndRestoreState() {
        Editor editor = new Editor();
        History history = new History();

        editor.setContent("Draft 1");
        history.push(editor.save());

        editor.setContent("Draft 2");
        history.push(editor.save());

        editor.setContent("Draft 3");
        assertEquals("Draft 3", editor.getContent());

        editor.restore(history.pop());
        assertEquals("Draft 2", editor.getContent());

        editor.restore(history.pop());
        assertEquals("Draft 1", editor.getContent());

        assertNull(history.pop());
    }
}
