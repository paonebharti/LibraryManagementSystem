package main.java.com.airtribe.libraryManagement.repository;

import main.java.com.airtribe.libraryManagement.entity.Loan;

import java.util.*;

public class LoanRepository {

    private final Map<String, Loan> loans = new HashMap<>();

    public void createLoan(Loan loan) {
        loans.put(loan.getId(), loan);
    }

    public List<Loan> getLoansByPatron(String patronId) {
        List<Loan> result = new ArrayList<>();

        for (Loan loan : loans.values()) {
            if (loan.getPatronId().equals(patronId)) {
                result.add(loan);
            }
        }

        return result;
    }

    public Loan findActiveLoan(String isbn, String patronId, String branchId) {
        for (Loan loan : loans.values()) {
            if (loan.getIsbn().equals(isbn)
                    && loan.getPatronId().equals(patronId)
                    && loan.getBranchId().equals(branchId)
                    && loan.getReturnDate() == null) {
                return loan;
            }
        }
        return null;
    }
}