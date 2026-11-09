package io.github.arisnguyen.d2s.dsa.hashtable.openaddressing;

public class OpenAddressingHashTableMain {
    public static void main(String[] args) {
        OpenAddressingHashTable<String, Integer> linear = OpenAddressingHashTableFactory.<String, Integer>createLinear(1).build();
        linear.insert("K1", 100);
        linear.insert("K2", 200);
        System.out.println("Linear get K1: " + linear.get("K1"));

        OpenAddressingHashTable<String, Integer> quadratic = OpenAddressingHashTableFactory.<String, Integer>createQuadratic().build();
        quadratic.insert("Q1", 300);
        System.out.println("Quadratic get Q1: " + quadratic.get("Q1"));

        OpenAddressingHashTable<String, Integer> doubleHash = OpenAddressingHashTableFactory.<String, Integer>createDoubleHashing().build();
        doubleHash.insert("D1", 400);
        System.out.println("DoubleHashing get D1: " + doubleHash.get("D1"));
    }
}
