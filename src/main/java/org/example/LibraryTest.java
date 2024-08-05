package org.example;

import org.junit.Test;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class LibraryTest {
    @Test
    public void testAddBook() {
        Library library = new Library();
        Book book = new Book("Title", "Author", 2020, 300, "Fiction");
        library.addBook(book);
        assertEquals(1, library.findBooksByYear(2020).size());
    }

    @Test
    public void testRemoveBook() {
        Library library = new Library();
        Book book = new Book("Title", "Author", 2020, 300, "Fiction");
        library.addBook(book);
        library.removeBook("Title");
        assertEquals(0, library.findBooksByYear(2020).size());
    }

    @Test
    public void testFindBooksByYear() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 2020, 300, "Fiction");
        Book book2 = new Book("Title2", "Author2", 2021, 400, "Non-Fiction");
        library.addBook(book1);
        library.addBook(book2);
        assertEquals(1, library.findBooksByYear(2020).size());
        assertEquals(1, library.findBooksByYear(2021).size());
    }

    @Test
    public void testFindBooksByAuthor() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 2020, 300, "Fiction");
        Book book2 = new Book("Title2", "Author2", 2021, 400, "Non-Fiction");
        library.addBook(book1);
        library.addBook(book2);
        assertEquals(1, library.findBooksByAuthor("Author1").size());
        assertEquals(1, library.findBooksByAuthor("Author2").size());
    }

    @Test
    public void testFindBookWithMostPages() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 2020, 300, "Fiction");
        Book book2 = new Book("Title2", "Author2", 2021, 400, "Non-Fiction");
        library.addBook(book1);
        library.addBook(book2);
        assertEquals("Title2", library.findBookWithMostPages().getTitle());
    }

    @Test
    public void testFindBooksWithMoreThanNPages() {
        Library library = new Library();
        Book book1 = new Book("Title1", "Author1", 2020, 300, "Fiction");
        Book book2 = new Book("Title2", "Author2", 2021, 400, "Non-Fiction");
        library.addBook(book1);
        library.addBook(book2);
        assertEquals(1, library.findBooksWithMoreThanNPages(350).size());
    }

    @Test
    public void testLoanAndReturnBook() {
        Library library = new Library();
        User user = new User("John Doe", 12345);
        library.addUser(user);
        Book book = new Book("Title", "Author", 2020, 300, "Fiction");
        library.addBook(book);
        library.loanBook("Title", user);
        assertTrue(book.isOnLoan());
        library.returnBook("Title", user);
        assertFalse(book.isOnLoan());
    }

    @Test
    public void testLateFees() {
        Library library = new Library();
        User user = new User("John Doe", 12345);
        library.addUser(user);
        Book book = new Book("Title", "Author", 2020, 300, "Fiction");
        library.addBook(book);
        user.loanBook(book);

        // Simulate loan duration of 20 days
        book.setLoanTimestamp(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(20));
        user.returnBook(book);

        assertEquals(3.0, user.getLateFees(), 0.01); // 6 days late * $0.50 = $3.00
    }
}
