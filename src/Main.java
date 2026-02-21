import main.java.com.airtribe.libraryManagement.entity.*;
import main.java.com.airtribe.libraryManagement.factory.BookFactory;
import main.java.com.airtribe.libraryManagement.repository.*;
import main.java.com.airtribe.libraryManagement.service.*;
import main.java.com.airtribe.libraryManagement.strategy.*;
import main.java.com.airtribe.libraryManagement.validator.InputValidator;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Initialize
        BookRepository bookRepository = new BookRepository();
        PatronRepository patronRepository = new PatronRepository();
        LoanRepository loanRepository = new LoanRepository();
        BranchRepository branchRepository = new BranchRepository();
        BranchInventoryRepository branchInventoryRepository = new BranchInventoryRepository();

        LibraryService libraryService =
                new LibraryService(bookRepository, patronRepository, loanRepository, branchRepository, branchInventoryRepository);

        boolean running = true;

        while (running) {
            printMenu();
            int choice = InputValidator.readInt(scanner, "Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addBook(libraryService);
                    case 2 -> updateBook(libraryService);
                    case 3 -> registerPatron(libraryService);
                    case 4 -> updatePatron(libraryService);
                    case 5 -> searchBooks(libraryService);
                    case 6 -> checkoutBook(libraryService);
                    case 7 -> returnBook(libraryService);
                    case 8 -> listAllBooks(libraryService);
                    case 9 -> viewPatronHistory(libraryService);
                    case 10 -> createBranch(libraryService);
                    case 11 -> addBookToBranch(libraryService);
                    case 12 -> transferBooks(libraryService);
                    case 13 -> addInventoryToBranch(libraryService);
                    case 0 -> {
                        running = false;
                        System.out.println("Exiting Library System...");
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("===== Library Management System =====");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Register Patron");
        System.out.println("4. Update Patron");
        System.out.println("5. Search Books");
        System.out.println("6. Checkout Book");
        System.out.println("7. Return Book");
        System.out.println("8. List All Books");
        System.out.println("9. View Patron History");
        System.out.println("10. Create Branch");
        System.out.println("11. Add Book to Branch");
        System.out.println("12. Transfer Books");
        System.out.println("13. Add Book Inventory to Branch");
        System.out.println("0. Exit");
    }

    private static void addBook(LibraryService service) {
        String isbn = InputValidator.readValidIsbn(scanner, "ISBN: ");
        String title = InputValidator.readNonEmptyString(scanner, "Title: ");
        String author = InputValidator.readNonEmptyString(scanner, "Author: ");
        int year = InputValidator.readValidYear(scanner, "Publication Year: ");
        int copies = InputValidator.readPositiveInt(scanner, "Total Copies: ");

        Book book = BookFactory.createBook(isbn, title, author, year, copies);
        service.addBook(book);

        System.out.println("Book added successfully.");
    }

    private static void updateBook(LibraryService service) {
        String isbn = InputValidator.readValidIsbn(scanner, "Enter ISBN to update: ");
        String title = InputValidator.readNonEmptyString(scanner, "New Title: ");
        String author = InputValidator.readNonEmptyString(scanner, "New Author: ");
        int year = InputValidator.readValidYear(scanner, "New Publication Year: ");
        int copies = InputValidator.readPositiveInt(scanner, "New Total Copies: ");

        service.updateBook(isbn, title, author, year, copies);
        System.out.println("Book updated successfully.");
    }

    private static void registerPatron(LibraryService service) {
        String name = InputValidator.readNonEmptyString(scanner, "Name: ");

        Patron patron = service.registerPatron(name);
        System.out.println("Patron registered with ID: " + patron.getId());
    }

    private static void updatePatron(LibraryService service) {
        String id = InputValidator.readNonEmptyString(scanner, "Enter Patron ID: ");
        String name = InputValidator.readNonEmptyString(scanner, "Enter new name: ");

        service.updatePatron(id, name);
        System.out.println("Patron updated successfully.");
    }

    private static void searchBooks(LibraryService service) {

        System.out.println("Search by:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. ISBN");

        int type = InputValidator.readInt(scanner, "Choice: ");

        if (type < 1 || type > 3) {
            System.out.println("Invalid search type");
            return;
        }

        String keyword = InputValidator.readNonEmptyString(scanner, "Enter keyword: ");

        SearchStrategy strategy = switch (type) {
            case 1 -> new TitleSearchStrategy();
            case 2 -> new AuthorSearchStrategy();
            case 3 -> new IsbnSearchStrategy();
            default -> throw new IllegalStateException("Unexpected value");
        };

        List<Book> results = service.searchBooks(strategy, keyword);

        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book b : results) {
                System.out.println(b.getIsbn() + " | " +
                        b.getTitle() + " | " +
                        b.getAuthor() + " | Available: " +
                        b.getAvailableCopies() + "/" + b.getTotalCopies());
            }
        }
    }

    private static void checkoutBook(LibraryService service) {
        String branchId = InputValidator.readNonEmptyString(scanner, "Branch ID: ");
        String isbn = InputValidator.readValidIsbn(scanner, "ISBN: ");
        String patronId = InputValidator.readNonEmptyString(scanner, "Patron ID: ");

        service.checkoutBook(branchId, isbn, patronId);
        System.out.println("Book checked out.");
    }

    private static void returnBook(LibraryService service) {
        String branchId = InputValidator.readNonEmptyString(scanner, "Branch ID: ");
        String isbn = InputValidator.readValidIsbn(scanner, "ISBN: ");
        String patronId = InputValidator.readNonEmptyString(scanner, "Patron ID: ");

        service.returnBook(branchId, isbn, patronId);
        System.out.println("Book returned.");
    }

    private static void listAllBooks(LibraryService service) {
        List<Book> books = service.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }

        for (Book b : books) {
            System.out.println(b.getIsbn() + " | " +
                    b.getTitle() + " | " +
                    b.getAuthor() + " | Available: " +
                    b.getAvailableCopies() + "/" + b.getTotalCopies());
        }
    }

    private static void viewPatronHistory(LibraryService service) {
        String patronId = InputValidator.readNonEmptyString(scanner, "Enter Patron ID: ");

        List<Loan> loans = service.getPatronLoanHistory(patronId);

        if (loans.isEmpty()) {
            System.out.println("No history found.");
            return;
        }

        for (Loan loan : loans) {
            System.out.println(
                    "LoanID: " + loan.getId() +
                            " | ISBN: " + loan.getIsbn() +
                            " | Issued: " + loan.getIssueDate() +
                            " | Returned: " +
                            (loan.getReturnDate() == null ? "Not yet" : loan.getReturnDate())
            );
        }
    }

    private static void createBranch(LibraryService service) {
        String name = InputValidator.readNonEmptyString(scanner, "Branch name: ");

        Branch branch = service.createBranch(name);
        System.out.println("Branch created with ID: " + branch.getId());
    }

    private static void addBookToBranch(LibraryService service) {
        String branchId = InputValidator.readNonEmptyString(scanner, "Branch ID: ");
        String isbn = InputValidator.readValidIsbn(scanner, "ISBN: ");
        int count = InputValidator.readPositiveInt(scanner, "Copies: ");

        service.addBookToBranch(branchId, isbn, count);
        System.out.println("Add successful.");
    }

    private static void transferBooks(LibraryService service) {
        String from = InputValidator.readNonEmptyString(scanner, "From Branch ID: ");
        String to = InputValidator.readNonEmptyString(scanner, "To Branch ID: ");
        String isbn = InputValidator.readValidIsbn(scanner, "ISBN: ");
        int count = InputValidator.readPositiveInt(scanner, "Copies: ");

        service.transferBook(from, to, isbn, count);
        System.out.println("Transfer successful.");
    }

    private static void addInventoryToBranch(LibraryService service) {
        String branchId = InputValidator.readNonEmptyString(scanner, "Branch ID: ");
        String isbn = InputValidator.readValidIsbn(scanner, "ISBN: ");
        int copies = InputValidator.readPositiveInt(scanner, "Number of copies: ");

        service.addBookToBranch(branchId, isbn, copies);
        System.out.println("Inventory added successfully.");
    }
}
