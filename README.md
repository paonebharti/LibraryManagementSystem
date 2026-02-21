
# Class Diagram

## Overview
This diagram represents the core structure of the **Library Management System** and shows the relationships between major components.

```
+------------------+
|      Book        |
+------------------+
| isbn             |
| title            |
| author           |
| year             |
+------------------+

+------------------+
|     Patron       |
+------------------+
| id               |
| name             |
+------------------+

+------------------+
|      Loan        |
+------------------+
| loanId           |
| isbn             |
| patronId         |
| branchId         |
| issueDate        |
| returnDate       |
+------------------+

+------------------+
|     Branch       |
+------------------+
| branchId         |
| name             |
+------------------+

+---------------------------+
|    BranchInventory        |
+---------------------------+
| branchId                  |
| isbn                      |
| totalCopies               |
| availableCopies           |
+---------------------------+


Relationships
-------------

Branch 1 ----- * BranchInventory
Book   1 ----- * BranchInventory

(Represents Many-to-Many between Branch and Book)

Patron 1 ----- * Loan
Book   1 ----- * Loan
Branch 1 ----- * Loan

Service Layer
-------------

LibraryService
    |
    |-- BookRepository
    |-- PatronRepository
    |-- LoanRepository
    |-- BranchRepository
    |-- BranchInventoryRepository


Search Strategy (Strategy Pattern)
----------------------------------

SearchStrategy (interface)
    |
    |-- TitleSearchStrategy
    |-- AuthorSearchStrategy
    |-- IsbnSearchStrategy
```

## Key Design Notes

- **Many-to-Many**: Books and Branches via `BranchInventory`
- **Branch-specific lending**
- **Autonomous ID generation** for Patron, Branch, and Loan
- **Repository Pattern** for in-memory storage
- **Service Layer** for business logic
- **Strategy Pattern** for flexible search
