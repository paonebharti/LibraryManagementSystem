package main.java.com.airtribe.libraryManagement.service;

import main.java.com.airtribe.libraryManagement.entity.*;
import main.java.com.airtribe.libraryManagement.repository.*;
import main.java.com.airtribe.libraryManagement.strategy.SearchStrategy;

import java.time.LocalDate;
import java.util.List;

public class LibraryService {

    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final LoanRepository loanRepository;
    private final BranchRepository branchRepository;
    private final BranchInventoryRepository branchInventoryRepository;

    public LibraryService(BookRepository bookRepository,
                          PatronRepository patronRepository,
                          LoanRepository loanRepository,
                          BranchRepository branchRepository, BranchInventoryRepository branchInventoryRepository) {

        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.loanRepository = loanRepository;
        this.branchRepository = branchRepository;
        this.branchInventoryRepository = branchInventoryRepository;
    }

    public void addBook(Book book) {
        if (bookRepository.exists(book.getIsbn())) {
            throw new IllegalArgumentException("Book already exists");
        }
        bookRepository.addBook(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public List<Book> searchBooks(SearchStrategy strategy, String keyword) {
        return strategy.search(bookRepository.getAllBooks(), keyword);
    }

    public void updateBook(String isbn, String title, String author, int year, int copies) {
        Book existing = bookRepository.getByIsbn(isbn);

        if (existing == null) {
            throw new IllegalArgumentException("Book not found");
        }

        Book updated = new Book(isbn, title, author, year, copies);

        bookRepository.updateBook(updated);
    }

    public Patron registerPatron(String name) {
        return patronRepository.createPatron(name);
    }

    public void updatePatron(String patronId, String newName) {
        Patron patron = patronRepository.getById(patronId);

        if (patron == null) {
            throw new IllegalArgumentException("Patron not found");
        }

        patron.setName(newName);
        patronRepository.updatePatron(patron);
    }

    public void checkoutBook(String branchId, String isbn, String patronId) {

        Branch branch = branchRepository.findById(branchId.toUpperCase());
        if (branch == null) throw new RuntimeException("Branch not found");

        Book book = bookRepository.getByIsbn(isbn);
        if (book == null) throw new RuntimeException("Book not found");

        Patron patron = patronRepository.getById(patronId);
        if (patron == null) throw new RuntimeException("Patron not found");

        if (loanRepository.findActiveLoan(isbn, patronId, branchId) != null) {
            throw new RuntimeException("Already borrowed.");
        }

        BranchInventory inventory =
                branchInventoryRepository.find(branchId, isbn);

        if (inventory == null || inventory.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available in this branch.");
        }

        inventory.borrowCopy();

        Loan loan = new Loan(isbn, patronId, branchId);
        loanRepository.createLoan(loan);
    }

    public void returnBook(String branchId, String isbn, String patronId) {

        Branch branch = branchRepository.findById(branchId.toUpperCase());
        if (branch == null) {
            throw new RuntimeException("Branch not found");
        }

        Loan loan = loanRepository.findActiveLoan(isbn, patronId, branchId);
        if (loan == null) {
            throw new RuntimeException("Active loan not found");
        }

        BranchInventory inventory =
                branchInventoryRepository.find(branchId, isbn);

        if (inventory == null) {
            throw new RuntimeException("Inventory record not found");
        }

        inventory.returnCopy();
        loan.setReturnDate(LocalDate.now());
    }
    public List<Loan> getPatronLoanHistory(String patronId) {
        Patron patron = patronRepository.getById(patronId);

        if (patron == null) {
            throw new IllegalArgumentException("Patron not found");
        }

        return loanRepository.getLoansByPatron(patronId);
    }
    public Branch createBranch(String name) {
        Branch branch = new Branch(name);
        branchRepository.save(branch);
        return branch;
    }

    public void addBookToBranch(String branchId, String isbn, int copies) {
        Branch branch = branchRepository.findById(branchId);

        if (branch == null) {
            throw new RuntimeException("Branch not found.");
        }

        if (bookRepository.getByIsbn(isbn) == null) {
            throw new RuntimeException("Book not found.");
        }

        branch.addCopies(isbn, copies);
    }

    public void transferBook(String fromBranchId, String toBranchId, String isbn, int count) {
        BranchInventory fromInventory =
                branchInventoryRepository.find(fromBranchId, isbn);

        if (fromInventory == null || fromInventory.getAvailableCopies() < count) {
            throw new RuntimeException("Not enough copies in source branch");
        }

        BranchInventory toInventory =
                branchInventoryRepository.find(toBranchId, isbn);

        if (toInventory == null) {
            toInventory = new BranchInventory(toBranchId, isbn, 0);
            branchInventoryRepository.save(toInventory);
        }

        fromInventory.removeCopies(count);
        toInventory.addCopies(count);
    }
}
