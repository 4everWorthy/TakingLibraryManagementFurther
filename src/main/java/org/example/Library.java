package org.example;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books;
    private List<User> users;
    private Map<User, Map<Book, Long>> lendingRecords;
    private static final long LOAN_PERIOD = 14 * 24 * 60 * 60 * 1000; // 2 weeks in milliseconds
    private static final double LATE_FEE_PER_DAY = 1.0; // Fee per day

    // Constructor
    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        lendingRecords = new HashMap<>();
    }

    // Add a book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Remove a book from the library by title
    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

    // Find all books published in a specific year
    public List<Book> findBooksByYear(int year) {
        return books.stream()
                .filter(book -> book.getPublicationYear() == year)
                .collect(Collectors.toList());
    }

    // Find all books by a specific author
    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    // Find the book with the most pages
    public Optional<Book> findBookWithMostPages() {
        return books.stream()
                .max(Comparator.comparingInt(Book::getPages));
    }

    // Find all books with more than n pages
    public List<Book> findBooksWithMoreThanPages(int pages) {
        return books.stream()
                .filter(book -> book.getPages() > pages)
                .collect(Collectors.toList());
    }

    // Print all book titles in the library, sorted alphabetically
    public void printAllBookTitles() {
        books.stream()
                .map(Book::getTitle)
                .sorted()
                .forEach(System.out::println);
    }

    // Find all books in a specific category
    public List<Book> findBooksByCategory(String category) {
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // Loan out a book
    public boolean loanBook(String title, User user) {
        Optional<Book> bookToLoan = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title) && !book.isOnLoan())
                .findFirst();

        if (bookToLoan.isPresent()) {
            Book book = bookToLoan.get();
            book.setOnLoan(true);
            user.getBooksOnLoan().add(book);

            if (!lendingRecords.containsKey(user)) {
                lendingRecords.put(user, new HashMap<>());
            }
            lendingRecords.get(user).put(book, System.currentTimeMillis());

            return true;
        }
        return false;
    }

    // Return a book
    public boolean returnBook(String title, User user) {
        Optional<Book> bookToReturn = user.getBooksOnLoan().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst();

        if (bookToReturn.isPresent()) {
            Book book = bookToReturn.get();
            book.setOnLoan(false);
            user.getBooksOnLoan().remove(book);

            if (lendingRecords.containsKey(user)) {
                lendingRecords.get(user).remove(book);
            }

            return true;
        }
        return false;
    }

    // Calculate late fees for a user
    public double calculateLateFees(User user) {
        if (!lendingRecords.containsKey(user)) {
            return 0.0;
        }

        double totalLateFees = 0.0;
        long currentTime = System.currentTimeMillis();

        for (Map.Entry<Book, Long> entry : lendingRecords.get(user).entrySet()) {
            long loanTime = entry.getValue();
            long overdueTime = currentTime - loanTime - LOAN_PERIOD;

            if (overdueTime > 0) {
                long overdueDays = overdueTime / (24 * 60 * 60 * 1000);
                totalLateFees += overdueDays * LATE_FEE_PER_DAY;
            }
        }

        return totalLateFees;
    }

    // Register a user
    public void registerUser(User user) {
        users.add(user);
    }
}

