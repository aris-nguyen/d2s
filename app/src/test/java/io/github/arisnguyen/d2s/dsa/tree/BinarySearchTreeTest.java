package io.github.arisnguyen.d2s.dsa.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest {

    private BinarySearchTree<Integer> bst;

    @BeforeEach
    public void setUp() {
        bst = new BinarySearchTree<>();
    }

    @Test
    public void emptyTreeInitialState() {
        assertEquals(0, bst.size());
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.height());
        assertFalse(bst.contains(10));
        assertFalse(bst.contains(null));
        assertThrows(NoSuchElementException.class, () -> bst.findMin());
        assertThrows(NoSuchElementException.class, () -> bst.findMax());
        assertEquals("[]", bst.toString());
    }

    @Test
    public void addNullThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> bst.add(null));
    }

    @Test
    public void addElementsAndCheckSizeAndContains() {
        assertTrue(bst.add(50));
        assertEquals(1, bst.size());
        assertFalse(bst.isEmpty());
        assertTrue(bst.contains(50));

        assertTrue(bst.add(30));
        assertTrue(bst.add(70));
        assertEquals(3, bst.size());
        assertTrue(bst.contains(30));
        assertTrue(bst.contains(70));
        assertFalse(bst.contains(100));
    }

    @Test
    public void addDuplicateRejectsAndReturnsFalse() {
        assertTrue(bst.add(42));
        assertFalse(bst.add(42));
        assertEquals(1, bst.size());
    }

    @Test
    public void findMinAndFindMax() {
        bst.add(50);
        bst.add(20);
        bst.add(80);
        bst.add(10);
        bst.add(35);
        bst.add(60);
        bst.add(90);

        assertEquals(10, bst.findMin());
        assertEquals(90, bst.findMax());
    }

    @Test
    public void heightOnBalancedAndSkewedTrees() {
        assertEquals(0, bst.height());

        bst.add(10);
        assertEquals(1, bst.height());

        bst.add(5);
        bst.add(15);
        assertEquals(2, bst.height());

        BinarySearchTree<Integer> leftSkewed = new BinarySearchTree<>();
        leftSkewed.add(5);
        leftSkewed.add(4);
        leftSkewed.add(3);
        leftSkewed.add(2);
        leftSkewed.add(1);
        assertEquals(5, leftSkewed.height());

        BinarySearchTree<Integer> rightSkewed = new BinarySearchTree<>();
        rightSkewed.add(1);
        rightSkewed.add(2);
        rightSkewed.add(3);
        rightSkewed.add(4);
        rightSkewed.add(5);
        assertEquals(5, rightSkewed.height());
    }

    @Test
    public void removeNullOrNonExistentReturnsFalse() {
        assertFalse(bst.remove(null));
        assertFalse(bst.remove(100));

        bst.add(50);
        assertFalse(bst.remove(null));
        assertFalse(bst.remove(100));
        assertEquals(1, bst.size());
    }

    @Test
    public void removeLeafNode() {
        bst.add(50);
        bst.add(30);
        bst.add(70);

        assertTrue(bst.remove(30));
        assertEquals(2, bst.size());
        assertFalse(bst.contains(30));
        assertTrue(bst.contains(50));
        assertTrue(bst.contains(70));
    }

    @Test
    public void removeNodeWithOneLeftChild() {
        bst.add(50);
        bst.add(30);
        bst.add(20);

        assertTrue(bst.remove(30));
        assertEquals(2, bst.size());
        assertFalse(bst.contains(30));
        assertTrue(bst.contains(20));
        assertTrue(bst.contains(50));
    }

    @Test
    public void removeNodeWithOneRightChild() {
        bst.add(50);
        bst.add(30);
        bst.add(40);

        assertTrue(bst.remove(30));
        assertEquals(2, bst.size());
        assertFalse(bst.contains(30));
        assertTrue(bst.contains(40));
        assertTrue(bst.contains(50));
    }

    @Test
    public void removeNodeWithTwoChildrenUsingSuccessor() {
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        bst.add(65);

        assertTrue(bst.remove(70));
        assertEquals(7, bst.size());
        assertFalse(bst.contains(70));
        assertTrue(bst.contains(60));
        assertTrue(bst.contains(65));
        assertTrue(bst.contains(80));

        List<Integer> inOrder = new ArrayList<>();
        for (int val : bst) {
            inOrder.add(val);
        }
        assertEquals(List.of(20, 30, 40, 50, 60, 65, 80), inOrder);
    }

    @Test
    public void removeRootNodeUntilEmpty() {
        bst.add(50);
        bst.add(30);
        bst.add(70);

        assertTrue(bst.remove(50));
        assertEquals(2, bst.size());
        assertFalse(bst.contains(50));
        assertEquals(30, bst.findMin());
        assertEquals(70, bst.findMax());

        assertTrue(bst.remove(70));
        assertEquals(1, bst.size());

        assertTrue(bst.remove(30));
        assertEquals(0, bst.size());
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.height());
    }

    @Test
    public void traversalsOrderCheck() {
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);

        List<Integer> inOrder = toList(bst.traverse(TreeTraversalOrder.IN_ORDER));
        assertEquals(List.of(20, 30, 40, 50, 60, 70, 80), inOrder);

        List<Integer> preOrder = toList(bst.traverse(TreeTraversalOrder.PRE_ORDER));
        assertEquals(List.of(50, 30, 20, 40, 70, 60, 80), preOrder);

        List<Integer> postOrder = toList(bst.traverse(TreeTraversalOrder.POST_ORDER));
        assertEquals(List.of(20, 40, 30, 60, 80, 70, 50), postOrder);

        List<Integer> levelOrder = toList(bst.traverse(TreeTraversalOrder.LEVEL_ORDER));
        assertEquals(List.of(50, 30, 70, 20, 40, 60, 80), levelOrder);
    }

    @Test
    public void traverseWithNullOrderThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> bst.traverse(null));
    }

    @Test
    public void iteratorNoSuchElementExceptionWhenExhausted() {
        bst.add(1);
        Iterator<Integer> it = bst.iterator();
        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);

        for (TreeTraversalOrder order : TreeTraversalOrder.values()) {
            Iterator<Integer> emptyIt = new BinarySearchTree<Integer>().traverse(order);
            assertFalse(emptyIt.hasNext());
            assertThrows(NoSuchElementException.class, emptyIt::next);
        }
    }

    @Test
    public void iteratorConcurrentModificationExceptionOnAdd() {
        bst.add(10);
        bst.add(20);

        for (TreeTraversalOrder order : TreeTraversalOrder.values()) {
            Iterator<Integer> it = bst.traverse(order);
            assertTrue(it.hasNext());
            bst.add(30);
            assertThrows(ConcurrentModificationException.class, it::hasNext);
            assertThrows(ConcurrentModificationException.class, it::next);
            bst.remove(30);
        }
    }

    @Test
    public void iteratorConcurrentModificationExceptionOnRemove() {
        bst.add(10);
        bst.add(20);
        bst.add(30);

        for (TreeTraversalOrder order : TreeTraversalOrder.values()) {
            Iterator<Integer> it = bst.traverse(order);
            assertTrue(it.hasNext());
            bst.remove(30);
            assertThrows(ConcurrentModificationException.class, it::hasNext);
            assertThrows(ConcurrentModificationException.class, it::next);
            bst.add(30);
        }
    }

    @Test
    public void iteratorConcurrentModificationExceptionOnClear() {
        bst.add(10);
        bst.add(20);

        for (TreeTraversalOrder order : TreeTraversalOrder.values()) {
            Iterator<Integer> it = bst.traverse(order);
            assertTrue(it.hasNext());
            bst.clear();
            assertThrows(ConcurrentModificationException.class, it::hasNext);
            assertThrows(ConcurrentModificationException.class, it::next);
            bst.add(10);
            bst.add(20);
        }
    }

    @Test
    public void clearEmptiesTree() {
        bst.add(10);
        bst.add(20);
        bst.add(5);
        assertEquals(3, bst.size());

        bst.clear();
        assertEquals(0, bst.size());
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.height());
        assertFalse(bst.contains(10));
        assertEquals("[]", bst.toString());
    }

    @Test
    public void toStringFormatting() {
        assertEquals("[]", bst.toString());

        bst.add(50);
        assertEquals("[50]", bst.toString());

        bst.add(20);
        bst.add(80);
        assertEquals("[20, 50, 80]", bst.toString());
    }

    private <T> List<T> toList(Iterator<T> iterator) {
        List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}
