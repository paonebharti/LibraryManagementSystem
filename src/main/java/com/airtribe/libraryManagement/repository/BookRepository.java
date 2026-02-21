package main.java.com.airtribe.libraryManagement.repository;

import main.java.com.airtribe.libraryManagement.entity.Book;

import java.util.*;

public class BookRepository {

    private final Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public Book getByIsbn(String isbn) {
        return books.get(isbn);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public boolean exists(String isbn) {
        return books.containsKey(isbn);
    }

    public void updateBook(Book book) {
        if (!books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Book not found");
        }
        books.put(book.getIsbn(), book);
    }
}
