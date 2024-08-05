package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class User {
    private String name;
    private int libraryCardNumber;
    private List<Book> booksOnLoan;
    private double lateFees;

    // Constructor
    public User(String name, int libraryCardNumber) {
        this.name = name;
        this.libraryCardNumber = libraryCardNumber;
        this.booksOnLoan = new ArrayList<>();
        this.lateFees = 0;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public void setLibraryCardNumber(int libraryCardNumber) {
        this.libraryCardNumber = libraryCardNumber;
    }

    public List<Book> getBooksOnLoan() {
        return booksOnLoan;
    }

    public double getLateFees() {
        return lateFees;
    }

    // Add a book to the user's loan list
    public void loanBook(Book book) {
        book.setOnLoan(true);
        book.setLoanTimestamp(System.currentTimeMillis());
        booksOnLoan.add(book);
    }

    // Return a book and calculate any late fees
    public void returnBook(Book book) {
        if (booksOnLoan.remove(book)) {
            book.setOnLoan(false);
            long loanDuration = System.currentTimeMillis() - book.getLoanTimestamp();
            long daysLoaned = TimeUnit.MILLISECONDS.toDays(loanDuration);
            if (daysLoaned > 14) {
                lateFees += (daysLoaned - 14) * 0.50; // Assume $0.50 per day after 14 days
            }
        }
    }
}
