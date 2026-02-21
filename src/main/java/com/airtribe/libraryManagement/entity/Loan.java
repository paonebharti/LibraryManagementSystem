package main.java.com.airtribe.libraryManagement.entity;

import java.time.LocalDate;

public class Loan {

    private static int counter = 1;

    private final String id;
    private final String isbn;
    private final String patronId;
    private final String branchId;
    private final LocalDate issueDate;
    private LocalDate returnDate;

    public Loan(String isbn, String patronId, String branchId) {
        this.id = "LN" + counter++;
        this.isbn = isbn;
        this.patronId = patronId;
        this.branchId = branchId;
        this.issueDate = LocalDate.now();
        this.returnDate = null;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPatronId() {
        return patronId;
    }

    public String getBranchId() {
        return branchId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}