package main.java.com.airtribe.libraryManagement.entity;

public class BranchInventory {

    private final String branchId;
    private final String isbn;
    private int availableCopies;

    public BranchInventory(String branchId, String isbn, int availableCopies) {
        this.branchId = branchId;
        this.isbn = isbn;
        this.availableCopies = availableCopies;
    }

    public String getBranchId() {
        return branchId;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void addCopies(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Copies must be positive.");
        }
        availableCopies += count;
    }

    public void removeCopies(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Copies must be positive.");
        }
        if (availableCopies < count) {
            throw new IllegalStateException("Not enough copies available.");
        }
        availableCopies -= count;
    }

    public void borrowCopy() {
        removeCopies(1);
    }

    public void returnCopy() {
        addCopies(1);
    }
}
