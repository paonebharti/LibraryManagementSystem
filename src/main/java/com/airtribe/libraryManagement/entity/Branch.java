package main.java.com.airtribe.libraryManagement.entity;

import java.util.HashMap;
import java.util.Map;

public class Branch {

    private static int counter = 1;

    private final String id;
    private final String name;
    private final Map<String, Integer> inventory = new HashMap<>();

    public Branch(String name) {
        this.id = "BR" + counter++;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public int getAvailableCopies(String isbn) {
        return inventory.getOrDefault(isbn, 0);
    }

    public void addCopies(String isbn, int count) {
        inventory.put(isbn, getAvailableCopies(isbn) + count);
    }
}