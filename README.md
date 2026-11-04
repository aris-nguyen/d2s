# d2s - Data Structures from Scratch in Java

`d2s` is a clean, educational, and modular implementation of fundamental Data Structures and Algorithms (DSA) in Java 21. Built from the ground up without relying on Java's built-in collection classes (`java.util.*`), the project demonstrates core memory layouts, abstract data type (ADT) design patterns, and algorithmic mechanics.

---

## Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Implemented Data Structures](#implemented-data-structures)
  - [1. Lists (`dsa.list`)](#1-lists-dsalist)
  - [2. Stacks (`dsa.stack`)](#2-stacks-dsastack)
  - [3. Queues (`dsa.queue`)](#3-queues-dsaqueue)
  - [4. Hash Tables (`dsa.hashtable`)](#4-hash-tables-dsahashtable)
  - [5. Trees (`dsa.tree`)](#5-trees-dsatree)
- [Architecture & Design Principles](#architecture--design-principles)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Building and Running](#building-and-running)
  - [Running Unit Tests](#running-unit-tests)

---

## Overview

Modern software often abstracts away underlying memory layouts and algorithmic nuances behind standard libraries. `d2s` explores and explains these structures by implementing them natively:

- **Self-contained**: Data structures reuse custom-built components rather than Java collections (e.g., `ArrayBasedStack` is backed by `ArrayList`, `LinkedListQueue` is backed by `DoublyLinkedList`, and `BinarySearchTree`'s level-order iterator uses `LinkedListQueue`).
- **ADT-Driven**: Each data structure defines an interface contract specifying behavior, supported operations, and constraints.
- **Fail-Fast & Safe**: Modifiable collections track state updates using modification counters (`modificationCount`) to detect concurrent modifications during iteration.

---

## Implemented Data Structures

### 1. Lists (`dsa.list`)

- **`ListADT<T>`**: Common interface for sequential collections extending `Iterable<T>`.
  - Core operations: `size()`, `isEmpty()`, `clear()`, `addFirst()`, `addLast()`, `add(index, data)`, `get(index)`, `set(index, data)`, `removeFirst()`, `removeLast()`, `removeAt(index)`, `remove(object)`, `indexOf(object)`, `contains(object)`.
- **`ArrayList<T>` (`dsa.list.arraylist`)**:
  - Dynamic array implementation with automatic geometric resizing (`grow()`).
  - Supports random access by index in $O(1)$ time.
  - Linear-time shifting for insertions and deletions at non-tail positions.
- **`SinglyLinkedList<T>` (`dsa.list.linkedlist.singly`)**:
  - Unidirectional linked list with node pointers (`head`, `tail`).
  - Constant-time $O(1)$ head insertion and deletion.
- **`DoublyLinkedList<T>` (`dsa.list.linkedlist.doubly`)**:
  - Bidirectional linked list with `prev` and `next` pointers.
  - Constant-time $O(1)$ insertions and removals at both ends (`head` and `tail`).

### 2. Stacks (`dsa.stack`)

- **`StackADT<T>`**: Standard LIFO (Last-In, First-Out) interface.
  - Methods: `push(element)`, `pop()`, `top()`, `size()`, `isEmpty()`.
- **`ArrayBasedStack<T>`**:
  - Stack implemented on top of the custom `ArrayList<T>`.
  - Amortized $O(1)$ push and pop operations with zero reliance on `java.util.Stack`.

### 3. Queues (`dsa.queue`)

- **`QueueADT<T>`**: Standard FIFO (First-In, First-Out) interface extending `Iterable<T>`.
  - Methods: `enQueue(element)`, `deQueue()`, `peek()`, `size()`, `isEmpty()`.
- **`LinkedListQueue<T>`**:
  - Queue implemented using the custom `DoublyLinkedList<T>`.
  - Guaranteed $O(1)$ enqueue and dequeue operations without memory waste from shifts.

### 4. Hash Tables (`dsa.hashtable`)

- **`HashTableADT<K, V>`**: Key-value associative mapping interface extending `Iterable<K>`.
  - Methods: `insert(key, value)`, `get(key)`, `remove(key)`, `has(key)`, `size()`, `isEmpty()`, `clear()`.
- **Separate Chaining (`dsa.hashtable.separatechaining`)**:
  - `SeparateChainingHashTable<K, V>` resolves collisions using buckets of `DoublyLinkedList<Node<K, V>>`.
  - Automatic table resizing and rehashing when load factor exceeds threshold.
- **Open Addressing (`dsa.hashtable.openaddressing`)**:
  - `OpenAddressingHashTable<K, V>` uses contiguous memory slots with tombstone markers for deleted elements.
  - Configurable collision resolution strategies:
    - **`LinearProbe`**: $h(k, i) = (h(k) + i) \pmod N$
    - **`QuadraticProbe`**: $h(k, i) = (h(k) + c_1 i + c_2 i^2) \pmod N$
    - **`DoubleHashingProbingStrategy`**: $h(k, i) = (h_1(k) + i \cdot h_2(k)) \pmod N$
  - Flexible capacity policies:
    - **`PrimeCapacityPolicy`**: Ensures bucket capacity is always prime.
    - **`PowerOfTwoCapacityPolicy`**: Keeps capacity as powers of 2 for bitwise masking.
    - **`LinearProbingCapacityPolicy`**: Dynamic growth tailored to linear probing constraints.
  - **`OpenAddressingHashTableFactory`**: Fluent helper for building preconfigured hash table variants.

### 5. Trees (`dsa.tree`)

- **`TreeADT<T extends Comparable<T>>`**: Binary Search Tree interface extending `Iterable<T>`.
  - Methods: `add(element)`, `remove(element)`, `contains(element)`, `height()`, `findMin()`, `findMax()`, `size()`, `isEmpty()`, `clear()`, `traverse(order)`.
- **`BinarySearchTree<T>`**:
  - Binary search tree ordering property: left subtree $\le$ node $<$ right subtree.
  - Node deletion handling all three cases: leaf node, single child, and two children (using in-order successor).
  - Four iterative traversal orders (non-recursive, maintaining traversal state on the heap):
    - **`PRE_ORDER`**: Node $\to$ Left $\to$ Right (using `ArrayBasedStack`).
    - **`IN_ORDER`**: Left $\to$ Node $\to$ Right (produces sorted order; default iterator).
    - **`POST_ORDER`**: Left $\to$ Right $\to$ Node (using two stacks).
    - **`LEVEL_ORDER`**: Breadth-First Search level by level (using `LinkedListQueue`).
  - Fail-fast traversal with `ConcurrentModificationException` on structural mutations during iteration.

---

## Architecture & Design Principles

1. **Layered Interfaces**: High-level abstract data types (`*ADT`) decouple consumers from underlying implementations.
2. **Reusability across Modules**: Higher-level data structures reuse lower-level primitives created in this repository:
   - `ArrayBasedStack` $\to$ `ArrayList`
   - `LinkedListQueue` $\to$ `DoublyLinkedList`
   - `SeparateChainingHashTable` $\to$ `DoublyLinkedList`
   - `BinarySearchTree.LevelOrderIterator` $\to$ `LinkedListQueue`
   - `BinarySearchTree.PreOrderIterator` $\to$ `ArrayBasedStack`
3. **Comprehensive Unit Testing**: Every structure has full test coverage under JUnit 5, validating edge cases (empty states, null inputs, capacity expansion, duplicate handling, and concurrent modification checks).

---

## Project Structure

```text
d2s/
├── app/
│   ├── src/
│   │   ├── main/java/io/github/arisnguyen/d2s/
│   │   │   ├── App.java
│   │   │   └── dsa/
│   │   │       ├── hashtable/
│   │   │       │   ├── HashTableADT.java
│   │   │       │   ├── Node.java
│   │   │       │   ├── openaddressing/
│   │   │       │   │   ├── capacity/
│   │   │       │   │   ├── probing/
│   │   │       │   │   ├── OpenAddressingHashTable.java
│   │   │       │   │   └── OpenAddressingHashTableFactory.java
│   │   │       │   └── separatechaining/
│   │   │       │       └── SeparateChainingHashTable.java
│   │   │       ├── list/
│   │   │       │   ├── ListADT.java
│   │   │       │   ├── arraylist/
│   │   │       │   │   ├── ArrayList.java
│   │   │       │   │   └── ArrayListADT.java
│   │   │       │   └── linkedlist/
│   │   │       │       ├── LinkedListADT.java
│   │   │       │       ├── Node.java
│   │   │       │       ├── doubly/
│   │   │       │       └── singly/
│   │   │       ├── queue/
│   │   │       │   ├── LinkedListQueue.java
│   │   │       │   └── QueueADT.java
│   │   │       ├── stack/
│   │   │       │   ├── ArrayBasedStack.java
│   │   │       │   └── StackADT.java
│   │   │       └── tree/
│   │   │           ├── BinarySearchTree.java
│   │   │           ├── Node.java
│   │   │           ├── TreeADT.java
│   │   │           └── TreeTraversalOrder.java
│   │   └── test/java/io/github/arisnguyen/d2s/
│   │       ├── AppTest.java
│   │       └── dsa/
│   │           ├── hashtable/
│   │           ├── list/
│   │           ├── queue/
│   │           ├── stack/
│   │           └── tree/
│   └── build.gradle
├── gradle/
├── gradlew
├── gradlew.bat
├── settings.gradle
└── README.md
```

---

## Getting Started

### Prerequisites

- **Java**: JDK 21 or higher
- **Gradle**: Gradle 9.x (or use the included `./gradlew` wrapper)

### Building and Running

Compile the project:

```bash
./gradlew build
```

Run the application:

```bash
./gradlew run
```

### Running Unit Tests

Execute the comprehensive test suite:

```bash
./gradlew test
```

To force rerun all unit tests and view the output report:

```bash
./gradlew test --rerun
```

Test reports are generated in HTML format at `app/build/reports/tests/test/index.html`.
