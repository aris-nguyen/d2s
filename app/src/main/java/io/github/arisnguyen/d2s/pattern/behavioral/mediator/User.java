package io.github.arisnguyen.d2s.pattern.behavioral.mediator;

public abstract class User {
    protected final ChatMediator mediator;
    protected final String name;

    protected User(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public abstract void send(String message);
    public abstract void receive(String message);

    public String getName() {
        return name;
    }
}
