package main.java.com.airtribe.libraryManagement.entity;

public class Patron {
    private final String id;
    private String name;

    public Patron(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }

    public void setName(String name) {
        this.name = name;
    }
}
