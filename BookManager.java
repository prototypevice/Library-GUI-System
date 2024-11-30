import java.io.*;
import java.util.*;

public class BookManager {

    // Define the Book class inside the BookManager class
    public static class Book {
        private String title;
        private String publisher;
        private String isbn;
        private String author;
        private int quantity;
        private String genre;

        // Constructor for Book class
        public Book(String title, String publisher, String isbn, String author, int quantity, String genre) {
            this.title = title;
            this.publisher = publisher;
            this.isbn = isbn;
            this.author = author;
            this.quantity = quantity;
            this.genre = genre;
        }

        // Getter and setter methods for Book class
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        // toString method for saving to the file
        @Override
        public String toString() {
            return title + "," + publisher + "," + isbn + "," + author + "," + quantity + "," + genre;
        }
    }

    // Method to load books from the file
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File("books.txt");
        if (!file.exists()) {
            try {
                file.createNewFile(); // Create file if it doesn't exist
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookDetails = line.split(",");
                if (bookDetails.length == 6) {
                    try {
                        Book book = new Book(
                                bookDetails[0],
                                bookDetails[1],
                                bookDetails[2],
                                bookDetails[3],
                                Integer.parseInt(bookDetails[4]),
                                bookDetails[5]
                        );
                        books.add(book);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing book data: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    // Method to save books to the file
    public static boolean saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving books: " + e.getMessage());
            return false;
        }
    }

    // Method to remove a book by title (returning true if successfully removed)
    public static boolean removeBookByTitle(String title) {
        List<Book> books = loadBooks();
        boolean removed = books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
        if (removed) {
            saveBooks(books);
        }
        return removed;
    }

}
