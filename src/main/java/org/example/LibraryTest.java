package org.example;

public class LibraryTest {
    public static void main(String[] args) {
        Library library = new Library();

        // Add manga titles to the library
        Book manga1 = new Book("Naruto", "Masashi Kishimoto", 1999, 220, "Shonen");
        Book manga2 = new Book("One Piece", "Eiichiro Oda", 1997, 230, "Shonen");
        Book manga3 = new Book("Attack on Titan", "Hajime Isayama", 2009, 210, "Shonen");

        library.addBook(manga1);
        library.addBook(manga2);
        library.addBook(manga3);

        // Register a user
        User user1 = new User("Alice", "12345");
        library.registerUser(user1);

        // Perform various library operations with manga titles
        System.out.println("Manga by Masashi Kishimoto: " + library.findBooksByAuthor("Masashi Kishimoto"));
        System.out.println("Manga published in 1997: " + library.findBooksByYear(1997));
        System.out.println("Manga with most pages: " + library.findBookWithMostPages().orElse(null));

        System.out.println("Loaning 'Naruto' to Alice: " + library.loanBook("Naruto", user1));
        System.out.println("Loaning 'Naruto' again: " + library.loanBook("Naruto", user1));
        System.out.println("Returning 'Naruto' from Alice: " + library.returnBook("Naruto", user1));
        System.out.println("Late fees for Alice: " + library.calculateLateFees(user1));
    }
}
