package io.github.arisnguyen.d2s.dsa.hashtable.separatechaining;

public class SeparateChainingHashTableMain {
    public static void main(String[] args) {
        SeparateChainingHashTable<String, Integer> table = new SeparateChainingHashTable<>();
        table.insert("Alice", 90);
        table.insert("Bob", 85);
        table.insert("Charlie", 95);

        System.out.println("Alice score: " + table.get("Alice"));
        table.remove("Bob");
        System.out.println("Has Bob: " + table.has("Bob"));
        System.out.println("Size: " + table.size());
    }
}
