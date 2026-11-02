package io.github.arisnguyen.d2s.dsa.tree;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import io.github.arisnguyen.d2s.dsa.queue.LinkedListQueue;
import io.github.arisnguyen.d2s.dsa.queue.QueueADT;

public class BinarySearchTree<T extends Comparable<T>> implements TreeADT<T> {

    private TreeNode<T> root;
    private int size;
    private int modificationCount;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
        this.modificationCount = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
        modificationCount++;
    }

    @Override
    public int height() {
        return calculateHeight(root);
    }

    private int calculateHeight(TreeNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }

    @Override
    public boolean contains(T element) {
        if (element == null || isEmpty()) {
            return false;
        }
        return search(root, element);
    }

    private boolean search(TreeNode<T> node, T element) {
        if (node == null) {
            return false;
        }
        int cmp = element.compareTo(node.data);
        if (cmp < 0) {
            return search(node.left, element);
        } else if (cmp > 0) {
            return search(node.right, element);
        }
        return true;
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add null element to binary search tree");
        }
        int prevSize = size;
        root = insert(root, element);
        if (size > prevSize) {
            modificationCount++;
            return true;
        }
        return false;
    }

    private TreeNode<T> insert(TreeNode<T> node, T element) {
        if (node == null) {
            size++;
            return new TreeNode<>(element);
        }
        int cmp = element.compareTo(node.data);
        if (cmp < 0) {
            node.left = insert(node.left, element);
        } else if (cmp > 0) {
            node.right = insert(node.right, element);
        }
        return node;
    }

    @Override
    public boolean remove(T element) {
        if (element == null || isEmpty()) {
            return false;
        }
        int prevSize = size;
        root = delete(root, element);
        if (size < prevSize) {
            modificationCount++;
            return true;
        }
        return false;
    }

    private TreeNode<T> delete(TreeNode<T> node, T element) {
        if (node == null) {
            return null;
        }
        int cmp = element.compareTo(node.data);
        if (cmp < 0) {
            node.left = delete(node.left, element);
        } else if (cmp > 0) {
            node.right = delete(node.right, element);
        } else {
            if (node.left == null) {
                size--;
                return node.right;
            } else if (node.right == null) {
                size--;
                return node.left;
            } else {
                TreeNode<T> successor = minNode(node.right);
                node.data = successor.data;
                node.right = delete(node.right, successor.data);
            }
        }
        return node;
    }

    private TreeNode<T> minNode(TreeNode<T> node) {
        TreeNode<T> curr = node;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    private TreeNode<T> maxNode(TreeNode<T> node) {
        TreeNode<T> curr = node;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr;
    }

    public T findMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty");
        }
        return minNode(root).data;
    }

    public T findMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty");
        }
        return maxNode(root).data;
    }

    @Override
    public Iterator<T> traverse(TreeTraversalOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Traversal order cannot be null");
        }
        return switch (order) {
            case PRE_ORDER -> new PreOrderIterator();
            case IN_ORDER -> new InOrderIterator();
            case POST_ORDER -> new PostOrderIterator();
            case LEVEL_ORDER -> new LevelOrderIterator();
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private void checkConcurrentModification(int expectedModCount) {
        if (modificationCount != expectedModCount) {
            throw new ConcurrentModificationException("Tree was modified during iteration");
        }
    }

    private class PreOrderIterator implements Iterator<T> {
        private final int expectedModCount;
        private final Deque<TreeNode<T>> stack;

        public PreOrderIterator() {
            this.expectedModCount = modificationCount;
            this.stack = new ArrayDeque<>();
            if (root != null) {
                stack.push(root);
            }
        }

        @Override
        public boolean hasNext() {
            checkConcurrentModification(expectedModCount);
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in traversal");
            }
            TreeNode<T> curr = stack.pop();
            if (curr.right != null) {
                stack.push(curr.right);
            }
            if (curr.left != null) {
                stack.push(curr.left);
            }
            return curr.data;
        }
    }

    private class InOrderIterator implements Iterator<T> {
        private final int expectedModCount;
        private final Deque<TreeNode<T>> stack;
        private TreeNode<T> curr;

        public InOrderIterator() {
            this.expectedModCount = modificationCount;
            this.stack = new ArrayDeque<>();
            this.curr = root;
        }

        @Override
        public boolean hasNext() {
            checkConcurrentModification(expectedModCount);
            return curr != null || !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in traversal");
            }
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            TreeNode<T> node = stack.pop();
            curr = node.right;
            return node.data;
        }
    }

    private class PostOrderIterator implements Iterator<T> {
        private final int expectedModCount;
        private final Deque<TreeNode<T>> stack1;
        private final Deque<TreeNode<T>> stack2;

        public PostOrderIterator() {
            this.expectedModCount = modificationCount;
            this.stack1 = new ArrayDeque<>();
            this.stack2 = new ArrayDeque<>();
            if (root != null) {
                stack1.push(root);
                while (!stack1.isEmpty()) {
                    TreeNode<T> node = stack1.pop();
                    stack2.push(node);
                    if (node.left != null) {
                        stack1.push(node.left);
                    }
                    if (node.right != null) {
                        stack1.push(node.right);
                    }
                }
            }
        }

        @Override
        public boolean hasNext() {
            checkConcurrentModification(expectedModCount);
            return !stack2.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in traversal");
            }
            return stack2.pop().data;
        }
    }

    private class LevelOrderIterator implements Iterator<T> {
        private final int expectedModCount;
        private final QueueADT<TreeNode<T>> queue;

        public LevelOrderIterator() {
            this.expectedModCount = modificationCount;
            this.queue = new LinkedListQueue<>();
            if (root != null) {
                queue.enQueue(root);
            }
        }

        @Override
        public boolean hasNext() {
            checkConcurrentModification(expectedModCount);
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in traversal");
            }
            TreeNode<T> curr = queue.deQueue();
            if (curr.left != null) {
                queue.enQueue(curr.left);
            }
            if (curr.right != null) {
                queue.enQueue(curr.right);
            }
            return curr.data;
        }
    }
}
