package main.java.com.airtribe.libraryManagement.repository;

import main.java.com.airtribe.libraryManagement.entity.BranchInventory;

import java.util.ArrayList;
import java.util.List;

public class BranchInventoryRepository {

    private final List<BranchInventory> inventoryList = new ArrayList<>();

    public void save(BranchInventory inventory) {
        inventoryList.add(inventory);
    }

    public BranchInventory find(String branchId, String isbn) {
        return inventoryList.stream()
                .filter(i -> i.getBranchId().equals(branchId)
                        && i.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }
}
