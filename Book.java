public class Book {
    private String title;
    private String publisher;
    private String isbn;
    private String author;
    private int quantity;
    private String genre;

    // Constructor
    public Book(String title, String publisher, String isbn, String author, int quantity, String genre) {
        this.title = title;
        this.publisher = publisher;
        this.isbn = isbn;
        this.author = author;
        this.quantity = quantity;
        this.genre = genre;
    }


    // Override toString method to store book details in file
    @Override
    public String toString() {
        return title + "," + publisher + "," + isbn + "," + author + "," + quantity + "," + genre;
    }
}
