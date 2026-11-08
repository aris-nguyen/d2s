package io.github.arisnguyen.d2s.pattern.structural.composite;

public class CompositeMain {
    public static void main(String[] args) {
        DirectoryItem root = new DirectoryItem("root");
        root.add(new FileItem("app.log", 500));

        DirectoryItem src = new DirectoryItem("src");
        src.add(new FileItem("Main.java", 1200));
        root.add(src);

        StringBuilder output = new StringBuilder();
        root.print(output, "");
        System.out.print(output);
        System.out.println("Total size: " + root.getSize() + " bytes");
    }
}

