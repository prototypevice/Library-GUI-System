import java.io.*;
import java.util.*;

public class BookManager {
    private static final String BOOKS_FILE = "books.txt";

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

        // User-friendly format for debugging or display
        public String toFormattedString() {
            return "Title: " + title + "\n" +
                    "Publisher: " + publisher + "\n" +
                    "ISBN: " + isbn + "\n" +
                    "Author: " + author + "\n" +
                    "Quantity: " + quantity + "\n" +
                    "Genre: " + genre;
        }
    }

    // Save all books to the file (overwrite entire file)
    public static boolean saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // Logs the full stack trace for debugging
            System.err.println("Error saving books: " + e.getMessage());
            return false;
        }
    }

    // Load all books from the file
    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
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
            e.printStackTrace(); // Logs the full stack trace for debugging
            System.err.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    // Remove book based on Title
    public static boolean removeBookByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            System.err.println("Error: Title cannot be null or empty.");
            return false;
        }

        List<Book> books = loadBooks();
        boolean isRemoved = books.removeIf(book -> book.title.equalsIgnoreCase(title.trim())); // Safely remove by title

        if (isRemoved) {
            return saveBooks(books);  // Save the updated list back to the file
        }

        System.err.println("Book with title \"" + title + "\" not found.");
        return false;
    }

    // Update the details of an existing book based on ISBN
    public static boolean updateBookDetails(String isbn, String newAuthor, int newQuantity) {
        if (isbn == null || isbn.trim().isEmpty()) {
            System.err.println("Error: ISBN cannot be null or empty.");
            return false;
        }

        List<Book> books = loadBooks();
        for (Book book : books) {
            if (book.isbn.equalsIgnoreCase(isbn)) {
                book.author = newAuthor;
                book.quantity = newQuantity;

                // After updating, save the entire list back to the file
                return saveBooks(books);
            }
        }
        System.err.println("Book with ISBN " + isbn + " not found.");
        return false;
    }

    // Check if a book with the same ISBN already exists
    public static boolean bookExists(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }

        List<Book> books = loadBooks();
        for (Book book : books) {
            if (book.isbn.equalsIgnoreCase(isbn)) {
                return true;  // ISBN already exists
            }
        }
        return false;
    }

    // Validates if the given genre exists in the predefined set of valid genres
    public static boolean isValidGenre(String genre) {
        // Set of valid genres
        Set<String> validGenres = Set.of(
                "Adventure", "Contemporary Fiction", "Fantasy", "Historical Fiction", "Horror",
                "Literary Fiction", "Mystery", "Romance", "Science Fiction", "Thriller",
                "Young Adult (YA)", "Children’s Fiction", "Dystopian", "Fairy Tale", "Paranormal",
                "Action", "Urban Fantasy", "Western", "Biography", "Autobiography", "Memoir",
                "Self-Help", "Health & Wellness", "Cookbook", "Travel", "Philosophy",
                "Psychology", "True Crime", "Politics", "Economics", "Science", "History",
                "Religion & Spirituality", "Art & Photography", "Business", "Education",
                "Historical Romance", "Romantic Suspense", "Fantasy Romance",
                "Science Fiction Romance", "Crime Fiction", "Fantasy Mystery", "Cyberpunk",
                "Steampunk", "Sword and Sorcery", "Space Opera", "Magical Realism",
                "Action & Adventure", "Alternate History", "Contemporary Romance", "Cozy Mystery",
                "Dark Fantasy", "Epic Fantasy", "Fairy Tale Retellings", "High Fantasy",
                "Historical Mystery", "Historical Thriller", "Military Science Fiction",
                "Paranormal Romance", "Political Thriller", "Post-Apocalyptic", "Psychological Thriller",
                "Religious Fiction", "Satire", "Space Western", "Sports Fiction", "Superhero",
                "Time Travel", "Urban Horror", "Supernatural Thriller", "True Story",
                "Teen Fiction", "Metafiction", "Magical Fantasy", "Gothic Fiction", "Philosophical Fiction",
                "Urban Lit", "Speculative Fiction", "Young Adult Fantasy", "Contemporary Poetry",
                "Coming-of-Age", "Christian Fiction", "LGBTQ+ Fiction", "New Adult", "LGBTQ+ Romance",
                "Women's Fiction", "Teen Romance", "Alternate Reality", "Graphic Novel", "Comic Book",
                "Cyberpunk Romance", "Paranormal Mystery", "True Crime Memoir", "Historical Fantasy",
                "Historical Thriller Fiction", "Fantasy Adventure", "Young Adult Thriller",
                "Animal Fiction", "Food Fiction", "Techno-Thriller", "Space Opera Fantasy", "Ghost Stories",
                "Eco-Fiction", "Business Thriller", "Dark Romance", "High-tech Fiction", "Historical Biography",
                "Science Fantasy", "Family Saga", "Spy Thriller", "Military Thriller", "Historical Horror",
                "Military Fiction", "Alternate Fantasy", "Western Romance", "Cult Fiction", "Zombie Fiction",
                "Cultural Fiction", "Espionage Fiction", "Experimental Fiction", "Dystopian Romance",
                "Steampunk Fantasy", "Space Exploration", "Teen Horror", "Apocalyptic Fiction",
                "Horror Comedy", "Psychological Horror", "Romantic Comedy", "Historical Family Saga",
                "Historical Romance Fiction", "Young Adult Historical Fiction", "Historical Epic",
                "Rural Fiction", "Victorian Fiction", "Cyberpunk Thriller", "Futuristic Fiction",
                "Comedy Fiction", "Magical Realism Romance", "Modern Gothic", "Mythological Fiction",
                "Epic Adventure", "Fantasy Thriller", "Utopian Fiction", "Political Fiction"
        );

        // Convert both the input genre and the valid genres to lowercase for case-insensitive comparison
        return validGenres.stream()
                .anyMatch(validGenre -> validGenre.equalsIgnoreCase(genre.trim()));
    }
}
