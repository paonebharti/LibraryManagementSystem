package main.java.com.airtribe.libraryManagement.repository;

import main.java.com.airtribe.libraryManagement.entity.Patron;

import java.util.HashMap;
import java.util.Map;

public class PatronRepository {

    private final Map<String, Patron> patrons = new HashMap<>();
    private int idCounter = 1;

    public Patron createPatron(String name) {
        String id = "P" + idCounter++;
        Patron patron = new Patron(id, name);
        patrons.put(id, patron);
        return patron;
    }

    public Patron getById(String id) {
        return patrons.get(id.toUpperCase());
    }

    public void updatePatron(Patron patron) {
        if (!patrons.containsKey(patron.getId())) {
            throw new IllegalArgumentException("Patron not found");
        }
        patrons.put(patron.getId(), patron);
    }
}
