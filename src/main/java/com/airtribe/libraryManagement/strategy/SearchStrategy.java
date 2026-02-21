package main.java.com.airtribe.libraryManagement.strategy;

import main.java.com.airtribe.libraryManagement.entity.Book;
import java.util.List;

public interface SearchStrategy {
    List<Book> search(List<Book> books, String keyword);
}
