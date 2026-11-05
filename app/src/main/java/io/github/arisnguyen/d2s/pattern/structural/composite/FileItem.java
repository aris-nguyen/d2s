package io.github.arisnguyen.d2s.pattern.structural.composite;

/**
 * Leaf element representing an individual file with no children.
 */
public class FileItem implements FileSystemItem {
    private final String name;
    private final long size;

    public FileItem(String name, long size) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("File name must not be null or blank");
        }
        if (size < 0) {
            throw new IllegalArgumentException("File size cannot be negative");
        }
        this.name = name.trim();
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void print(StringBuilder out, String indent) {
        out.append(indent).append("- ").append(name).append(" (").append(size).append(" bytes)\n");
    }
}
