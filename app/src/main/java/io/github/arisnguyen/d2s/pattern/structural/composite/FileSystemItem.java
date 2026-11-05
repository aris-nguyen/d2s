package io.github.arisnguyen.d2s.pattern.structural.composite;

/**
 * Component interface declaring common operations for both simple (files)
 * and complex (directories) items in the tree hierarchy.
 */
public interface FileSystemItem {
    String getName();
    long getSize();
    void print(StringBuilder out, String indent);
}
