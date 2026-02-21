package main.java.com.airtribe.libraryManagement.repository;

import main.java.com.airtribe.libraryManagement.entity.Branch;

import java.util.*;

public class BranchRepository {

    private final Map<String, Branch> branches = new HashMap<>();

    public void save(Branch branch) {
        branches.put(branch.getId(), branch);
    }

    public Branch findById(String id) {
        return branches.get(id);
    }
}