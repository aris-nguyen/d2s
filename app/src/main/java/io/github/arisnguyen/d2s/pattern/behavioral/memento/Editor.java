package io.github.arisnguyen.d2s.pattern.behavioral.memento;

public class Editor {
    private String content = "";

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public EditorMemento save() {
        return new EditorMemento(content);
    }

    public void restore(EditorMemento memento) {
        if (memento != null) {
            this.content = memento.getContent();
        }
    }
}
