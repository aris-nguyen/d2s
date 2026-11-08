package io.github.arisnguyen.d2s.pattern.creational.prototype;

import java.util.ArrayList;
import java.util.List;

public class PrototypeMain {
    public static void main(String[] args) {
        EmailTemplate prototype = new EmailTemplate(
                "Welcome",
                "Hi {{name}}",
                "Team",
                "Welcome to our service!",
                new ArrayList<>(List.of("onboarding"))
        );

        TemplateRegistry registry = new TemplateRegistry();
        registry.register("welcome", prototype);

        EmailTemplate clone = registry.get("welcome");
        clone.addTag("promo");

        System.out.println("Original tags: " + prototype.getTags());
        System.out.println("Clone tags: " + clone.getTags());
    }
}
