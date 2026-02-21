package main.java.com.airtribe.libraryManagement.entity;

public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final int publicationYear;
    private int totalCopies;
    private int availableCopies;

    public Book(String isbn, String title, String author, int year, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = year;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
}
