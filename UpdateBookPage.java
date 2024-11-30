import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class UpdateBookPage extends JPanel {

    public UpdateBookPage(MainGUI mainGUI) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Top Panel for Icon and Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Icon
        try {
            ImageIcon bookIcon = new ImageIcon("src/book.png"); // Ensure the path to the icon is correct
            Image scaledImage = bookIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            bookIcon = new ImageIcon(scaledImage);
            JLabel iconLabel = new JLabel(bookIcon);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(iconLabel, BorderLayout.NORTH);
        } catch (Exception e) {
            System.out.println("Icon not found!");
        }

        // Title at the top
        JLabel titleLabel = new JLabel("Update Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Title input
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Title:"), gbc);

        // Title Input Field
        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField titleField = new JTextField(20);
        titleField.setPreferredSize(new Dimension(titleField.getPreferredSize().width, 40));
        centerPanel.add(titleField, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel for Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        // Search Book Button
        JButton searchButton = new JButton("Search Book");
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        searchButton.setPreferredSize(new Dimension(200, 50));
        searchButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a title.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Find books with the same title
            List<BookManager.Book> books = BookManager.loadBooks();
            List<BookManager.Book> booksWithSameTitle = new ArrayList<>();
            for (BookManager.Book book : books) {
                if (book.getTitle().equalsIgnoreCase(title)) {
                    booksWithSameTitle.add(book);
                }
            }

            if (!booksWithSameTitle.isEmpty()) {
                // Display a list of books with the same title
                DefaultListModel<BookManager.Book> model = new DefaultListModel<>();
                booksWithSameTitle.forEach(model::addElement);
                JList<BookManager.Book> bookList = new JList<>(model);
                bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                bookList.setVisibleRowCount(5);
                bookList.setCellRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        BookManager.Book book = (BookManager.Book) value;
                        label.setText(book.getTitle() + " by " + book.getAuthor());
                        return label;
                    }
                });

                JScrollPane scrollPane = new JScrollPane(bookList);

                // Show the list in a dialog for user selection
                int option = JOptionPane.showConfirmDialog(this, scrollPane, "Select a book to update",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    BookManager.Book selectedBook = bookList.getSelectedValue();
                    if (selectedBook != null) {
                        // Display the details of the selected book for editing
                        JTextField titleEditField = new JTextField(selectedBook.getTitle());
                        JTextField authorEditField = new JTextField(selectedBook.getAuthor());
                        JTextField publisherEditField = new JTextField(selectedBook.getPublisher());
                        JTextField isbnEditField = new JTextField(selectedBook.getIsbn());
                        JTextField quantityEditField = new JTextField(String.valueOf(selectedBook.getQuantity()));

                        Object[] message = {
                                "Title:", titleEditField,
                                "Author:", authorEditField,
                                "Publisher:", publisherEditField,
                                "ISBN:", isbnEditField,
                                "Quantity:", quantityEditField
                        };

                        int optionEdit = JOptionPane.showConfirmDialog(this, message, "Edit Book", JOptionPane.OK_CANCEL_OPTION);
                        if (optionEdit == JOptionPane.OK_OPTION) {
                            // Update the book details using setters
                            selectedBook.setTitle(titleEditField.getText());
                            selectedBook.setAuthor(authorEditField.getText());
                            selectedBook.setPublisher(publisherEditField.getText());
                            selectedBook.setIsbn(isbnEditField.getText());
                            selectedBook.setQuantity(Integer.parseInt(quantityEditField.getText()));

                            // Update the books list
                            books.removeIf(book -> book.getTitle().equalsIgnoreCase(selectedBook.getTitle()) && book.getIsbn().equals(selectedBook.getIsbn()));
                            books.add(selectedBook);

                            // Write updated list to the file
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                                for (BookManager.Book book : books) {
                                    writer.write(book.toString());
                                    writer.newLine();
                                }
                                JOptionPane.showMessageDialog(this, "Book updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                                // Get the InventoryBookPage and refresh it
                                InventoryBookPage inventoryPage = (InventoryBookPage) mainGUI.getPage("InventoryBookPage");
                                if (inventoryPage != null) {
                                    inventoryPage.refreshInventoryTable(); // Refresh the inventory table
                                }

                                // Ask user if they want to edit another book
                                int editAnother = JOptionPane.showConfirmDialog(this,
                                        "Do you want to edit another book?", "Edit Another",
                                        JOptionPane.YES_NO_OPTION);

                                if (editAnother == JOptionPane.YES_OPTION) {
                                    titleField.setText(""); // Clear fields for new entry
                                } else {
                                    mainGUI.switchToPage("ManageBookPage");
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(this, "Failed to update the book.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No book selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No books found with the given title.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        bottomPanel.add(searchButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.addActionListener(e -> mainGUI.switchToPage("ManageBookPage"));
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
