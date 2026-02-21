package main.java.com.airtribe.libraryManagement.factory;

import main.java.com.airtribe.libraryManagement.entity.Book;

public class BookFactory {

    private BookFactory() {
        // Prevent instantiation
    }

    public static Book createBook(String isbn,
                                  String title,
                                  String author,
                                  int publicationYear,
                                  int totalCopies) {

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }

        return new Book(isbn, title, author, publicationYear, totalCopies);
    }
}
