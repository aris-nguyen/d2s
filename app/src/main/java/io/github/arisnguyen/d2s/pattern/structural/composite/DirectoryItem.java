package io.github.arisnguyen.d2s.pattern.structural.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Composite element representing a directory containing files or nested directories.
 */
public class DirectoryItem implements FileSystemItem {
    private final String name;
    private final List<FileSystemItem> children = new ArrayList<>();

    public DirectoryItem(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Directory name must not be null or blank");
        }
        this.name = name.trim();
    }

    public DirectoryItem add(FileSystemItem item) {
        children.add(Objects.requireNonNull(item, "item must not be null"));
        return this;
    }

    public boolean remove(FileSystemItem item) {
        return children.remove(item);
    }

    public List<FileSystemItem> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        long total = 0;
        for (FileSystemItem child : children) {
            total += child.getSize();
        }
        return total;
    }

    @Override
    public void print(StringBuilder out, String indent) {
        out.append(indent).append("+ ").append(name).append("/ (").append(getSize()).append(" bytes)\n");
        for (FileSystemItem child : children) {
            child.print(out, indent + "  ");
        }
    }
}
