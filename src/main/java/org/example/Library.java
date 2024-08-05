package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books;
    private List<User> users;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    // Add a book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Remove a book by title
    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equals(title));
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
                .filter(book -> book.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    // Find the book with the most pages
    public Book findBookWithMostPages() {
        return books.stream()
                .max(Comparator.comparingInt(Book::getPages))
                .orElse(null);
    }

    // Find all books with more than n pages
    public List<Book> findBooksWithMoreThanNPages(int n) {
        return books.stream()
                .filter(book -> book.getPages() > n)
                .collect(Collectors.toList());
    }

    // Print all book titles in the library sorted alphabetically
    public void printAllBookTitles() {
        books.stream()
                .map(Book::getTitle)
                .sorted()
                .forEach(System.out::println);
    }

    // Find all books in a specific category
    public List<Book> findBooksByCategory(String category) {
        return books.stream()
                .filter(book -> book.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    // Loan out a book
    public void loanBook(String title, User user) {
        books.stream()
                .filter(book -> book.getTitle().equals(title) && !book.isOnLoan())
                .findFirst()
                .ifPresent(book -> user.loanBook(book));
    }

    // Return a book
    public void returnBook(String title, User user) {
        books.stream()
                .filter(book -> book.getTitle().equals(title) && book.isOnLoan())
                .findFirst()
                .ifPresent(book -> user.returnBook(book));
    }

    // Add a user to the library
    public void addUser(User user) {
        users.add(user);
    }

    // Find a user by library card number
    public User findUser(int libraryCardNumber) {
        return users.stream()
                .filter(user -> user.getLibraryCardNumber() == libraryCardNumber)
                .findFirst()
                .orElse(null);
    }
}
