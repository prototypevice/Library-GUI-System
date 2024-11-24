import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {

    // Path to the book database file (books.txt)
    private static final String BOOKS_FILE = "books.txt";

    // Book class to represent a book object
    public static class Book {
        String title;
        String publisher;
        String isbn;
        String author;
        int quantity;
        String genre;

        public Book(String title, String publisher, String isbn, String author, int quantity, String genre) {
            this.title = title;
            this.publisher = publisher;
            this.isbn = isbn;
            this.author = author;
            this.quantity = quantity;
            this.genre = genre;
        }

        @Override
        public String toString() {
            return title + "," + publisher + "," + isbn + "," + author + "," + quantity + "," + genre;
        }
    }

    // Method to save a book to the books.txt file
    public static boolean saveBook(Book book) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE, true))) {
            writer.write(book.toString());
            writer.newLine();  // Add a new line after each book entry
            return true;  // Successfully added
        } catch (IOException e) {
            e.printStackTrace();
            return false;  // Failed to add book
        }
    }

    // Method to load all books from books.txt
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookDetails = line.split(",");
                if (bookDetails.length == 6) {
                    String title = bookDetails[0];
                    String publisher = bookDetails[1];
                    String isbn = bookDetails[2];
                    String author = bookDetails[3];
                    int quantity = Integer.parseInt(bookDetails[4]);
                    String genre = bookDetails[5];

                    Book book = new Book(title, publisher, isbn, author, quantity, genre);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Method to check if a book already exists by ISBN
    public static boolean bookExists(String isbn) {
        List<Book> books = loadBooks();
        for (Book book : books) {
            if (book.isbn.equals(isbn)) {
                return true;  // Book with the same ISBN exists
            }
        }
        return false;  // No book found with this ISBN
    }
}
