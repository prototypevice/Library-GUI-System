import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddBookPage extends JPanel {
    private MainGUI mainGUI; // Reference to MainGUI

    public AddBookPage(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout());

        // Create the top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Add an icon (Ensure the image is accessible in the resources folder)
        ImageIcon bookIconImage = new ImageIcon(getClass().getResource("/book.png")); // Use ClassLoader for resource loading
        Image scaledIconImage = bookIconImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale the icon
        JLabel bookIcon = new JLabel(new ImageIcon(scaledIconImage), SwingConstants.CENTER);
        topPanel.add(bookIcon, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Add Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        topPanel.add(titleLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Form panel for entering book details
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);

        JTextField titleField = new JTextField();
        JTextField publisherField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField genreField = new JTextField();

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Publisher:"));
        formPanel.add(publisherField);
        formPanel.add(new JLabel("ISBN:"));
        formPanel.add(isbnField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Genre:"));
        formPanel.add(genreField);

        add(formPanel, BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("Add Book");
        JButton backButton = new JButton("Back");

        addButton.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                String publisher = publisherField.getText().trim();
                String isbn = isbnField.getText().trim();
                String author = authorField.getText().trim();
                String quantityStr = quantityField.getText().trim();
                String genre = genreField.getText().trim();

                if (title.isEmpty() || publisher.isEmpty() || isbn.isEmpty() || author.isEmpty() || genre.isEmpty() || quantityStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int quantity = Integer.parseInt(quantityStr);

                // Add book to the system
                BookManager.Book book = new BookManager.Book(title, publisher, isbn, author, quantity, genre);
                List<BookManager.Book> books = BookManager.loadBooks();
                books.add(book);
                boolean success = BookManager.saveBooks(books);

                if (success) {
                    // Refresh tables
                    refreshInventoryTable();
                    refreshAvailabilityTable();

                    // Ask user if they want to add another book
                    int option = JOptionPane.showConfirmDialog(
                            this,
                            "Book added successfully! Do you want to add another book?",
                            "Book Added",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (option == JOptionPane.YES_OPTION) {
                        // Reset fields for new input
                        titleField.setText("");
                        publisherField.setText("");
                        isbnField.setText("");
                        authorField.setText("");
                        quantityField.setText("");
                        genreField.setText("");
                    } else {
                        // Go back to the Manage Book page
                        mainGUI.switchToPage("ManageBookPage");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainGUI.switchToPage("ManageBookPage"));

        bottomPanel.add(addButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshInventoryTable() {
        InventoryBookPage inventoryPage = (InventoryBookPage) mainGUI.getPage("InventoryBookPage");
        if (inventoryPage != null) {
            inventoryPage.refreshInventoryTable();
        }
    }

    private void refreshAvailabilityTable() {
        CheckBookAvailabilityPage availabilityPage = (CheckBookAvailabilityPage) mainGUI.getPage("CheckBookAvailabilityPage");
        if (availabilityPage != null) {
            availabilityPage.loadBooksIntoTable();
        }
    }
}
