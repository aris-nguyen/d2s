package io.github.arisnguyen.d2s.pattern.structural.composite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Composite Pattern Tests - Hierarchical File System")
class FileSystemCompositeTest {

    @Test
    @DisplayName("Should correctly calculate file size and render leaf node")
    void testFileItem() {
        FileSystemItem file = new FileItem("readme.txt", 1024);

        assertEquals("readme.txt", file.getName());
        assertEquals(1024, file.getSize());

        StringBuilder sb = new StringBuilder();
        file.print(sb, "");
        assertEquals("- readme.txt (1024 bytes)\n", sb.toString());
    }

    @Test
    @DisplayName("Should return 0 size for empty directory")
    void testEmptyDirectory() {
        DirectoryItem dir = new DirectoryItem("empty");

        assertEquals("empty", dir.getName());
        assertEquals(0, dir.getSize());
        assertTrue(dir.getChildren().isEmpty());
    }

    @Test
    @DisplayName("Should recursively calculate size of nested directory structure")
    void testNestedCompositeHierarchy() {
        DirectoryItem root = new DirectoryItem("project");
        FileSystemItem rootReadme = new FileItem("README.md", 500);

        DirectoryItem src = new DirectoryItem("src");
        FileSystemItem mainCode = new FileItem("Main.java", 2000);
        FileSystemItem utilCode = new FileItem("Utils.java", 1500);
        src.add(mainCode).add(utilCode);

        DirectoryItem tests = new DirectoryItem("tests");
        FileSystemItem testCode = new FileItem("MainTest.java", 1200);
        tests.add(testCode);

        root.add(rootReadme).add(src).add(tests);

        assertEquals(500 + 2000 + 1500 + 1200, root.getSize());
        assertEquals(3, root.getChildren().size());

        StringBuilder sb = new StringBuilder();
        root.print(sb, "");
        String output = sb.toString();

        assertTrue(output.contains("+ project/ (5200 bytes)"));
        assertTrue(output.contains("  - README.md (500 bytes)"));
        assertTrue(output.contains("  + src/ (3500 bytes)"));
        assertTrue(output.contains("    - Main.java (2000 bytes)"));
        assertTrue(output.contains("  + tests/ (1200 bytes)"));
    }

    @Test
    @DisplayName("Should support removing items from composite directory")
    void testRemoveItem() {
        DirectoryItem dir = new DirectoryItem("temp");
        FileSystemItem file1 = new FileItem("a.tmp", 100);
        FileSystemItem file2 = new FileItem("b.tmp", 200);

        dir.add(file1).add(file2);
        assertEquals(300, dir.getSize());

        assertTrue(dir.remove(file1));
        assertEquals(200, dir.getSize());
        assertFalse(dir.remove(file1));
    }

    @Test
    @DisplayName("Should throw exception for invalid inputs")
    void testInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new FileItem(null, 100));
        assertThrows(IllegalArgumentException.class, () -> new FileItem("", 100));
        assertThrows(IllegalArgumentException.class, () -> new FileItem("test.txt", -5));
        assertThrows(IllegalArgumentException.class, () -> new DirectoryItem("   "));
        DirectoryItem dir = new DirectoryItem("valid");
        assertThrows(NullPointerException.class, () -> dir.add(null));
    }
}
