import javax.swing.*;  // Imports all the Swing components (e.g., JFrame, JButton, JLabel, etc.) for building a graphical user interface (GUI)
import java.awt.*;  // Imports AWT (Abstract Window Toolkit) components for GUI, including layout managers (e.g., BorderLayout, GridBagLayout) and common components (e.g., Color, Insets)
import java.io.BufferedWriter;  // Imports BufferedWriter for writing text to a file with improved performance
import java.io.FileWriter;  // Imports FileWriter to write data to a file (used in combination with BufferedWriter)
import java.io.IOException;  // Imports IOException for handling input/output errors that may occur during file operations
import java.util.ArrayList;  // Imports ArrayList, a dynamic array class for storing a list of objects
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;  // For JTable
import javax.swing.JScrollPane;  // For JScrollPane (to make JTable scrollable)
import java.awt.Color;  // For setting the color of text in JTable cells
import java.awt.Component;  // For working with components in AWT
import javax.swing.table.TableCellRenderer;



public class MainGUI extends JFrame {
    private boolean isDarkMode = false; // Boolean value for using
    private JPanel backgroundPanel;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JLabel titleLabel;
    private JButton logOutButton;
    private JButton darkModeButton;
    private JPanel manageUserPage;
    private JPanel viewUserProfilePage;
    private JPanel addUserPage;
    private JPanel manageBookPage;
    private JPanel addBookPage;
    private JPanel removeBookPage;
    private JPanel updateBookPage;
    private JPanel inventoryBookPage;
    private JPanel checkBookAvailability;
    private JPanel centerPanel;
    private JTable bookTable;
    private JPanel inventoryPage;
    private JPanel checkBookAvailabilityPage;




    public MainGUI() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main background panel using CardLayout for page switching
        backgroundPanel = new JPanel(new CardLayout());
        add(backgroundPanel);

        // Initialize main page panel and set it up
        mainPanel = new JPanel(new GridBagLayout());
        setupMainPanel();

        // Add main panel to backgroundPanel with a label for CardLayout
        backgroundPanel.add(mainPanel, "MainPage");

        // Initialize ManageUserPage and add to backgroundPanel
        setupManageUserPage();
        backgroundPanel.add(manageUserPage, "ManageUserPage");

        // Initialize ViewUserProfilePage and add to backgroundPanel
        setUpViewUserProfilePage();
        backgroundPanel.add(viewUserProfilePage, "ViewUserProfilePage");

        // Initialize AddUserPage and add to backgroundPanel
        setupAddUserPage();
        backgroundPanel.add(addUserPage, "AddUserPage");

        // Initialize manageBookPage and add to backgroundPanel
        setupManageBookPage();
        backgroundPanel.add(manageBookPage, "ManageBookPage");

        // Initialize addBookPage and add to backgroundPanel
        setupAddBookPage();
        backgroundPanel.add(addBookPage, "AddBookPage");

        // Initialize removeBookPage and add to backgroundPanel
        setupRemoveBookPage();
        backgroundPanel.add(removeBookPage, "removeBookPage");

        // Initialize updateBookPage and add to backgroundPanel
        setupUpdateBookPage();
        backgroundPanel.add(updateBookPage, "updateBookPage");

        // Initialize inventoryBookPage and add to backgroundPanel
        setupInventoryBookPage();
        backgroundPanel.add(inventoryBookPage, "inventoryBookPage");

        // Initialize inventoryBookPage and add to backgroundPanel
        setupCheckBookAvailability();
        backgroundPanel.add(checkBookAvailability, "checkBookAvailability");

        removeBookPage = new JPanel();  // Example initialization
        inventoryPage = new JPanel();  // Example initialization
        checkBookAvailabilityPage = new JPanel();  // Example initialization

        // Initialize with light mode
        applyLightMode();

        setVisible(true);
    }

    private void setupMainPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;
        JButton manageUserButton = createStyledButton("Manage User");
        manageUserButton.addActionListener(e -> switchToManageUserPage());
        mainPanel.add(manageUserButton, gbc);

        gbc.gridy++;
        JButton manageBooksButton = createStyledButton("Manage Books");
        manageBooksButton.addActionListener(e -> switchToManageBookPage());
        mainPanel.add(manageBooksButton, gbc);

        gbc.gridy++;
        JButton viewProfileButton = createStyledButton("View User Profile");
        viewProfileButton.addActionListener(e -> switchToViewUserProfilePage());
        mainPanel.add(viewProfileButton, gbc);

        gbc.gridy++;
        JButton checkBookButton = createStyledButton("Check Book Availability");
        checkBookButton.addActionListener(e -> switchToCheckViewAvailability());
        mainPanel.add(checkBookButton, gbc);

        // Add Dark Mode Toggle Icon
        darkModeButton = new JButton("🌙");
        darkModeButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        darkModeButton.setContentAreaFilled(false);
        darkModeButton.setFocusPainted(false);
        darkModeButton.addActionListener(e -> toggleDarkMode());

        // Log Out Button
        logOutButton = new JButton("Log Out");
        logOutButton.setPreferredSize(new Dimension(120, 40));
        logOutButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        logOutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged Out Successfully!");
            dispose();
            SwingUtilities.invokeLater(LogInGUI::new);
        });

        // Bottom panel setup
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(darkModeButton, BorderLayout.WEST);
        bottomPanel.add(logOutButton, BorderLayout.EAST);
        bottomPanel.setBackground(Color.white);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupManageUserPage() {
        manageUserPage = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add User Button
        JButton addUserButton = createStyledButton("Add User");
        addUserButton.addActionListener(e -> switchToAddUserPage());
        buttonPanel.add(addUserButton, gbc);

        gbc.gridy++;
        // Remove User Button
        JButton removeUserButton = createStyledButton("Remove User");
        buttonPanel.add(removeUserButton, gbc);

        gbc.gridy++;
        // Update User Button
        JButton updateUserButton = createStyledButton("Update User");
        buttonPanel.add(updateUserButton, gbc);

        manageUserPage.add(buttonPanel, BorderLayout.CENTER);

        // Return to Home Button
        JButton returnButton = new JButton("Return to Home");
        returnButton.setPreferredSize(new Dimension(150, 40));
        returnButton.addActionListener(e -> switchToMainPage());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(returnButton);
        manageUserPage.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupAddUserPage() {

        addUserPage = new JPanel(new BorderLayout());
        addUserPage.setBackground(Color.white);

        // Icon and Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.white);

        // User Icon that is scaled and sized properly
        ImageIcon userIconImage = new ImageIcon("src/user_icon.png");
        Image scaledIconImage = userIconImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel userIcon = new JLabel(new ImageIcon(scaledIconImage), SwingConstants.CENTER);

        // Create a container with padding for the user icon
        JPanel userIconContainer = new JPanel(new BorderLayout());
        userIconContainer.setBackground(Color.white);
        userIconContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Add 20px padding at the top
        userIconContainer.add(userIcon, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Add Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBackground(Color.white);

        // Adds all the icon and title to the top panel
        topPanel.add(userIconContainer, BorderLayout.CENTER);
        topPanel.add(titleLabel, BorderLayout.SOUTH);
        addUserPage.add(topPanel, BorderLayout.NORTH);

        // Creates new text fields for the creation of an account.
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        centerPanel.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Adding labels and text fields with fixed size
        JTextField firstNameField = createFixedSizeTextField();
        JTextField lastNameField = createFixedSizeTextField();
        JTextField contactNumberField = createFixedSizeTextField();
        JTextField usernameField = createFixedSizeTextField();
        JPasswordField passwordField = createFixedSizePasswordField();
        JPasswordField confirmPasswordField = createFixedSizePasswordField();

        centerPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(contactNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        centerPanel.add(confirmPasswordField, gbc);

        addUserPage.add(centerPanel, BorderLayout.CENTER);

        addUserPage.add(centerPanel, BorderLayout.CENTER);

        // Creates the buttons like the back button and create account button.
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.white);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> {
            switchToManageUserPage();
            logOutButton.setVisible(false); // Hide log out button when switching back to the previous page
        });

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setPreferredSize(new Dimension(150, 40));
        createAccountButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String contactNumber = contactNumberField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (firstName.isEmpty() || lastName.isEmpty() || contactNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User user = new User(firstName, lastName, contactNumber, username, password);
                boolean success = AddUser.saveUser(user);

                if (success) {
                    JOptionPane.showMessageDialog(this, "User successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    firstNameField.setText("");
                    lastNameField.setText("");
                    contactNumberField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add user. Please try again." , "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        bottomPanel.add(backButton);
        bottomPanel.add(createAccountButton);

        // Place buttons in the bottom panel
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(createAccountButton, BorderLayout.EAST);

        addUserPage.add(bottomPanel, BorderLayout.SOUTH);

        backgroundPanel.add(addUserPage, "AddUserPage");
    }

    private void setUpViewUserProfilePage() {
        viewUserProfilePage = new JPanel(new BorderLayout());

        // Creates the title "Library Members" and places it at the top left of the panel.
        JLabel titleLabel = new JLabel("Library Members", SwingConstants.LEFT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        viewUserProfilePage.add(titleLabel, BorderLayout.NORTH);

        // Creates the search panel with icon and text field used for searching up members.
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchIcon = new JLabel(new ImageIcon("book_icon.png"));
        JTextField searchField = new JTextField("Search", 20);
        searchPanel.add(searchIcon);
        searchPanel.add(searchField);
        viewUserProfilePage.add(searchPanel, BorderLayout.CENTER);

        // Members list panel
        JPanel membersPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        membersPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // This for loop creates the stacks of member row.
        for (int i = 0; i < 4; i++) {
            JPanel memberRow = new JPanel(new BorderLayout());
            JLabel memberLabel = new JLabel("Name of Member", new ImageIcon("user_icon.png"), SwingConstants.LEFT);
            JButton moreInfoButton = createStyledButton("More Info");
            moreInfoButton.setPreferredSize(new Dimension(100, 30));
            memberRow.add(memberLabel, BorderLayout.CENTER);
            memberRow.add(moreInfoButton, BorderLayout.EAST);
            memberRow.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            membersPanel.add(memberRow);
        }

        viewUserProfilePage.add(membersPanel, BorderLayout.SOUTH);

        // Creates the back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> switchToMainPage());
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);
        viewUserProfilePage.add(backButtonPanel, BorderLayout.SOUTH);
    }

    private void setupManageBookPage() {
        manageBookPage = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Book Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 45));
        titleLabel.setForeground(Color.BLACK);
        manageBookPage.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add Book Button
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton addBookButton = createStyledButton("Add Book");
        addBookButton.addActionListener(e -> switchToAddBookPage());
        buttonPanel.add(addBookButton, gbc);

        // Remove Book Button
        gbc.gridx = 1;
        gbc.gridy = 0;
        JButton removeBookButton = createStyledButton("Remove Book");
        removeBookButton.addActionListener(e -> switchToRemoveBookPage());
        buttonPanel.add(removeBookButton, gbc);


        // Book Update Button
        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton updateBookButton = createStyledButton("Book Update");
        updateBookButton.addActionListener(e -> switchToUpdateBookPage());
        buttonPanel.add(updateBookButton, gbc);

        // Book Inventory Button
        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton bookInventoryButton = createStyledButton("Book Inventory");
        bookInventoryButton.addActionListener(e -> switchToInventoryBookPage());
        buttonPanel.add(bookInventoryButton, gbc);

        // Back Button
        JButton returnButton = new JButton("Back");
        returnButton.addActionListener(e -> switchToMainPage());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(returnButton);

        manageBookPage.add(buttonPanel, BorderLayout.CENTER);
        manageBookPage.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupAddBookPage(){
        addBookPage = new JPanel(new BorderLayout());
        addBookPage.setBackground(Color.white);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.white);

        // Book Icon that is scaled and sized properly
        ImageIcon bookIconImage = new ImageIcon("src/book.png");
        Image scaledIconImage = bookIconImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel bookIcon = new JLabel(new ImageIcon(scaledIconImage), SwingConstants.CENTER);

        // Create a container with padding for the book icon
        JPanel bookIconContainer = new JPanel(new BorderLayout());
        bookIconContainer.setBackground(Color.white);
        bookIconContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        bookIconContainer.add(bookIcon, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Add Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBackground(Color.white);

        // Adds all the icon and title to the top panel
        topPanel.add(bookIconContainer, BorderLayout.CENTER);
        topPanel.add(titleLabel, BorderLayout.SOUTH);
        addBookPage.add(topPanel, BorderLayout.NORTH);

        // Creates new text fields for adding book information
        JPanel centerPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        centerPanel.setBackground(Color.white);

        // Creating the text fields for the book details
        JTextField titleField = createFixedSizeTextField();
        JTextField publisherField = createFixedSizeTextField();
        JTextField isbnField = createFixedSizeTextField();
        JTextField authorField = createFixedSizeTextField();
        JTextField quantityField = createFixedSizeTextField();
        JTextField genreField = createFixedSizeTextField();

        // Add labels and text fields to the center panel
        centerPanel.add(new JLabel("Title:"));
        centerPanel.add(titleField);
        centerPanel.add(new JLabel("Publisher:"));
        centerPanel.add(publisherField);
        centerPanel.add(new JLabel("ISBN (13 digits):"));
        centerPanel.add(isbnField);
        centerPanel.add(new JLabel("Author:"));
        centerPanel.add(authorField);
        centerPanel.add(new JLabel("Quantity:"));
        centerPanel.add(quantityField);
        centerPanel.add(new JLabel("Genre:"));
        centerPanel.add(genreField);

        addBookPage.add(centerPanel, BorderLayout.CENTER);

        // Create the bottom panel with buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.white);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> {
            switchToManageBookPage();
            resetFields(titleField, publisherField, isbnField, authorField, quantityField, genreField);
        });

        // Add Book Button
        JButton addBookButton = new JButton("Add Book");
        addBookButton.setPreferredSize(new Dimension(150, 40));
        addBookButton.addActionListener(e -> {
            String title = titleField.getText();
            String publisher = publisherField.getText();
            String isbn = isbnField.getText();
            String author = authorField.getText();
            String quantityStr = quantityField.getText();
            String genre = genreField.getText().trim();  // Keep the original case for the input

            // Basic validation
            if (title.isEmpty() || publisher.isEmpty() || isbn.isEmpty() || author.isEmpty() || quantityStr.isEmpty() || genre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (isbn.length() != 13 || !isbn.matches("\\d+")) { // Check for valid ISBN (13 digits)
                JOptionPane.showMessageDialog(this, "ISBN must be 13 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!BookManager.isValidGenre(genre)) {
                JOptionPane.showMessageDialog(this, "Invalid genre. Please choose from the list of valid genres.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    BookManager.Book book = new BookManager.Book(title, publisher, isbn, author, quantity, genre);

                    // Get the current list of books, add the new book and save the updated list
                    List<BookManager.Book> books = BookManager.loadBooks();
                    books.add(book);
                    boolean success = BookManager.saveBooks(books);

                    if (success) {
                        JOptionPane.showMessageDialog(this, "Book successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh the table to reflect the added book
                        refreshInventoryTable();

                        // Also, update the CheckBookAvailability page with the new book
                        loadBooksIntoTable(checkBookAvailability);  // Update the check availability table

                        // Show confirmation dialog to add another book or go back to Manage Book page
                        int option = JOptionPane.showConfirmDialog(this,
                                "Do you want to add another book?", "Add Another Book",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (option == JOptionPane.YES_OPTION) {
                            resetFields(titleField, publisherField, isbnField, authorField, quantityField, genreField);
                        } else {
                            switchToManageBookPage();
                            resetFields(titleField, publisherField, isbnField, authorField, quantityField, genreField);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add book. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Quantity must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add buttons to the bottom panel
        bottomPanel.add(backButton, BorderLayout.WEST);
        bottomPanel.add(addBookButton, BorderLayout.EAST);

        addBookPage.add(bottomPanel, BorderLayout.SOUTH);
        backgroundPanel.add(addBookPage, "AddBookPage");
    }

    private void resetFields(JTextField titleField, JTextField publisherField, JTextField isbnField, JTextField authorField,
                             JTextField quantityField, JTextField genreField) {
        titleField.setText("");
        publisherField.setText("");
        isbnField.setText("");
        authorField.setText("");
        quantityField.setText("");
        genreField.setText("");
    }

    private void setupRemoveBookPage() {
        removeBookPage = new JPanel(new BorderLayout());
        removeBookPage.setBackground(Color.WHITE);

        // Top Panel for Icon and Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Icon (Smaller Book Icon)
        ImageIcon bookIcon = new ImageIcon("src/book.png");
        Image scaledImage = bookIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        bookIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(bookIcon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(iconLabel, BorderLayout.NORTH);

        // Title at the top
        JLabel titleLabel = new JLabel("Remove Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        removeBookPage.add(topPanel, BorderLayout.NORTH);

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

        removeBookPage.add(centerPanel, BorderLayout.CENTER);

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
                if (book.title.equalsIgnoreCase(title)) {
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
                        label.setText(book.title + " by " + book.author);
                        return label;
                    }
                });

                JScrollPane scrollPane = new JScrollPane(bookList);

                // Show the list in a dialog for user selection
                int option = JOptionPane.showConfirmDialog(this, scrollPane, "Select a book to delete",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    BookManager.Book selectedBook = bookList.getSelectedValue();
                    if (selectedBook != null) {
                        // Display the details of the selected book for confirmation
                        String bookDetails = "Title: " + selectedBook.title + "\n" +
                                "Author: " + selectedBook.author + "\n" +
                                "Publisher: " + selectedBook.publisher + "\n" +
                                "ISBN: " + selectedBook.isbn + "\n" +
                                "Quantity: " + selectedBook.quantity + "\n" +
                                "Genre: " + selectedBook.genre;

                        int confirm = JOptionPane.showConfirmDialog(this,
                                "Are you sure you want to delete the following book?\n\n" + bookDetails,
                                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Remove the selected book from the list
                            books.removeIf(book -> book.isbn.equals(selectedBook.isbn)); // Use ISBN for uniqueness

                            // Write updated list to the file
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                                for (BookManager.Book book : books) {
                                    writer.write(book.toString());
                                    writer.newLine();
                                }
                                JOptionPane.showMessageDialog(this, "Book removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                                // Refresh the inventory table
                                SwingUtilities.invokeLater(this::refreshInventoryTable);

                                // Ask if the user wants to remove more books
                                int removeMore = JOptionPane.showConfirmDialog(this, "Do you want to remove another book?",
                                        "Remove More?", JOptionPane.YES_NO_OPTION);
                                if (removeMore == JOptionPane.NO_OPTION) {
                                    titleField.setText("");
                                    // Switch to inventory page
                                    refreshInventoryTable(); // Ensure inventory is updated before switching
                                    switchToManageBookPage();
                                } else {
                                    titleField.setText(""); // Clear for next removal
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(this, "Failed to remove the book.", "Error", JOptionPane.ERROR_MESSAGE);
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
        backButton.addActionListener(e -> {
            titleField.setText("");  // Clear text field
            refreshInventoryTable(); // Ensure inventory is refreshed before going back
            switchToManageBookPage(); // Switch to manage page
        });
        bottomPanel.add(backButton);

        removeBookPage.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupUpdateBookPage() {
        updateBookPage = new JPanel(new BorderLayout());
        updateBookPage.setBackground(Color.WHITE);

        // Top Panel for Icon and Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Icon
        ImageIcon bookIcon = new ImageIcon("src/book.png");
        Image scaledImage = bookIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        bookIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(bookIcon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(iconLabel, BorderLayout.NORTH);

        // Title at the top
        JLabel titleLabel = new JLabel("Update Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        updateBookPage.add(topPanel, BorderLayout.NORTH);

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

        // Title Input Field (Bigger size)
        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField titleField = new JTextField(20);
        titleField.setPreferredSize(new Dimension(titleField.getPreferredSize().width, 40));
        centerPanel.add(titleField, gbc);

        // Add the panel to the updateBookPage
        updateBookPage.add(centerPanel, BorderLayout.CENTER);

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
                if (book.title.equalsIgnoreCase(title)) {
                    booksWithSameTitle.add(book);
                }
            }

            if (!booksWithSameTitle.isEmpty()) {
                // Display a list of books with the same title (only showing title and author)
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
                        label.setText(book.title + " by " + book.author);
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
                        JTextField titleEditField = new JTextField(selectedBook.title);
                        JTextField authorEditField = new JTextField(selectedBook.author);
                        JTextField publisherEditField = new JTextField(selectedBook.publisher);
                        JTextField isbnEditField = new JTextField(selectedBook.isbn);
                        JTextField quantityEditField = new JTextField(String.valueOf(selectedBook.quantity));

                        Object[] message = {
                                "Title:", titleEditField,
                                "Author:", authorEditField,
                                "Publisher:", publisherEditField,
                                "ISBN:", isbnEditField,
                                "Quantity:", quantityEditField
                        };

                        int optionEdit = JOptionPane.showConfirmDialog(this, message, "Edit Book", JOptionPane.OK_CANCEL_OPTION);
                        if (optionEdit == JOptionPane.OK_OPTION) {
                            // Update the book details
                            selectedBook.title = titleEditField.getText();
                            selectedBook.author = authorEditField.getText();
                            selectedBook.publisher = publisherEditField.getText();
                            selectedBook.isbn = isbnEditField.getText();
                            selectedBook.quantity = Integer.parseInt(quantityEditField.getText());

                            // Update the books list
                            books.removeIf(book -> book.title.equalsIgnoreCase(selectedBook.title) && book.isbn.equals(selectedBook.isbn));
                            books.add(selectedBook);

                            // Write updated list to the file
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
                                for (BookManager.Book book : books) {
                                    writer.write(book.toString());
                                    writer.newLine();
                                }
                                JOptionPane.showMessageDialog(this, "Book updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                                // After updating, refresh the inventory page
                                refreshInventoryTable();
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
        backButton.addActionListener(e -> {
            // Clear the text field when going back
            titleField.setText("");
            switchToManageBookPage();
        });
        bottomPanel.add(backButton);

        updateBookPage.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupInventoryBookPage() {
        inventoryBookPage = new JPanel(new BorderLayout());
        inventoryBookPage.setBackground(Color.WHITE);

        // Create a panel for the icon and title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout());

        // Load and resize the book icon to a smaller size
        ImageIcon bookIcon = new ImageIcon("src/book.png");  // Your icon path
        Image scaledImage = bookIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create label for the small icon
        JLabel iconLabel = new JLabel(scaledIcon);

        // Title at the top
        JLabel titleLabel = new JLabel("Book Inventory", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);

        // Add the icon and title to the topPanel
        topPanel.add(iconLabel, BorderLayout.NORTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Add the top panel to the inventory book page
        inventoryBookPage.add(topPanel, BorderLayout.NORTH);

        // Initialize and add the center panel
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        inventoryBookPage.add(centerPanel, BorderLayout.CENTER);

        // Load and display books in the inventory
        refreshInventoryTable();

        // Bottom panel for the back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> {
            // When going back, refresh the inventory
            refreshInventoryTable();
            switchToManageBookPage();  // Switch to the previous page
        });

        bottomPanel.add(backButton);
        inventoryBookPage.add(bottomPanel, BorderLayout.SOUTH);

        backgroundPanel.add(inventoryBookPage, "InventoryBookPage");
        inventoryBookPage.revalidate();
        inventoryBookPage.repaint();
    }

    private void refreshInventoryTable() {
        // Load the updated list of books from the file
        List<BookManager.Book> books = BookManager.loadBooks();

        // Define the column names for the inventory table
        String[] columnNames = {"Title", "Publisher", "ISBN", "Author", "Quantity", "Genre"};

        // Create a new DefaultTableModel with the column names
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Add rows for each book in the list
        for (BookManager.Book book : books) {
            model.addRow(new Object[]{
                    book.title,
                    book.publisher,
                    book.isbn,
                    book.author,
                    book.quantity,
                    book.genre
            });
        }

        // Check if the bookTable already exists
        if (bookTable == null) {
            // Create the table if it doesn't exist
            bookTable = new JTable(model);
            bookTable.setFillsViewportHeight(true);
            bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            bookTable.setRowHeight(25);
            bookTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
            bookTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

            // Create a JScrollPane to wrap the table
            JScrollPane scrollPane = new JScrollPane(bookTable);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

            // Add the scrollPane to the center panel
            centerPanel.removeAll(); // Clear any existing components
            centerPanel.add(scrollPane, BorderLayout.CENTER);
        } else {
            // Update the model of the existing table (rebuild it to reflect changes)
            bookTable.setModel(model);
            bookTable.repaint(); // Force a repaint to ensure the table updates
        }

        // Revalidate and repaint the panel to ensure the updates are reflected
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void setupCheckBookAvailability() {
        checkBookAvailability = new JPanel(new BorderLayout());
        checkBookAvailability.setBackground(Color.WHITE);

        // Create a panel for the icon and title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout());

        // Load and resize the book icon to a smaller size
        ImageIcon bookIcon = new ImageIcon("src/book.png");  // Your icon path
        Image scaledImage = bookIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create label for the small icon
        JLabel iconLabel = new JLabel(scaledIcon);

        // Title at the top
        JLabel titleLabel = new JLabel("Check Book Availability", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);

        // Add the icon and title to the topPanel
        topPanel.add(iconLabel, BorderLayout.NORTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Add the top panel to the check book availability page and center panel
        checkBookAvailability.add(topPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Add a method call to reload the books each time the availability check page is set up
        loadBooksIntoTable(centerPanel);

        // Add center panel to the check book availability page
        checkBookAvailability.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for the back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        // Back Button (Ensure only one listener)
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> {
            switchToMainPage();  // Switch to the main panel when the back button is clicked
            logOutButton.setVisible(false);  // Hide log out button when switching back to the previous page
        });

        bottomPanel.add(backButton);
        checkBookAvailability.add(bottomPanel, BorderLayout.SOUTH);

        backgroundPanel.add(checkBookAvailability, "CheckBookAvailabilityPage");

        // Revalidate the page to ensure changes are reflected
        checkBookAvailability.revalidate();
        checkBookAvailability.repaint();
    }

    private void loadBooksIntoTable(JPanel centerPanel) {
        centerPanel.removeAll();

        // Load books from the file
        List<BookManager.Book> books = BookManager.loadBooks();

        // Create column headers for the table
        String[] columnNames = {"Title", "Author", "Quantity", "Availability"};

        // Create data array for the table
        Object[][] data = new Object[books.size()][columnNames.length];
        for (int i = 0; i < books.size(); i++) {
            BookManager.Book book = books.get(i);
            data[i][0] = book.title;
            data[i][1] = book.author;
            data[i][2] = book.quantity;
            data[i][3] = book.quantity > 0 ? "Available" : "Not Available";
        }

        // Create the table
        JTable bookTable = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (column == 3) { // Availability column
                    String availability = (String) getValueAt(row, column);
                    if ("Available".equals(availability)) {
                        c.setForeground(Color.GREEN);
                    } else if ("Not Available".equals(availability)) {
                        c.setForeground(Color.RED);
                    }
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        };

        bookTable.setFillsViewportHeight(true);
        bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        bookTable.setRowHeight(25);
        bookTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bookTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 40));
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setFocusPainted(false);
        return button;
    }

    private JTextField createFixedSizeTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 30));
        return textField;
    }

    private JPasswordField createFixedSizePasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 30));
        return passwordField;
    }

    private void toggleDarkMode() {
        if (isDarkMode) {
            applyLightMode();
        } else {
            applyDarkMode();
        }
        isDarkMode = !isDarkMode;
    }

    private void applyLightMode() {
        backgroundPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBackground(new Color(255, 255, 255));
        bottomPanel.setBackground(new Color(255, 255, 255));
        titleLabel.setForeground(Color.BLACK);
        logOutButton.setBackground(Color.BLACK);
        logOutButton.setForeground(Color.WHITE);
    }

    private void applyDarkMode() {
        backgroundPanel.setBackground(new Color(45, 45, 45));
        mainPanel.setBackground(new Color(45, 45, 45));
        bottomPanel.setBackground(new Color(45, 45, 45));
        titleLabel.setForeground(Color.WHITE);
        logOutButton.setBackground(new Color(24, 65, 4));
        logOutButton.setForeground(Color.white);
    }

    public void switchToMainPage() {
        logOutButton.setVisible(true);
        darkModeButton.setVisible(true);
        CardLayout cl = (CardLayout) backgroundPanel.getLayout();
        cl.show(backgroundPanel, "MainPage");
    }

    private void switchToManageUserPage() {
        darkModeButton.setVisible(false);
        logOutButton.setVisible(false);
        CardLayout cl = (CardLayout) backgroundPanel.getLayout();
        cl.show(backgroundPanel, "ManageUserPage");
    }

    private void switchToAddUserPage() {
        darkModeButton.setVisible(false);
        logOutButton.setVisible(false);
        CardLayout cl = (CardLayout) backgroundPanel.getLayout();
        cl.show(backgroundPanel, "AddUserPage");
    }

    private void switchToViewUserProfilePage() {
        logOutButton.setVisible(false);
        darkModeButton.setVisible(false);
        CardLayout cl = (CardLayout) backgroundPanel.getLayout();
        cl.show(backgroundPanel, "ViewUserProfilePage");
    }

    private void switchToManageBookPage() {
        darkModeButton.setVisible(false);
        logOutButton.setVisible(false);
        CardLayout cl = (CardLayout) backgroundPanel.getLayout();
        cl.show(backgroundPanel, "ManageBookPage");
    }

    private void switchToAddBookPage() {
        darkModeButton.setVisible(false);
        logOutButton.setVisible(false);
        CardLayout cl = (CardLayout) backgroundPanel.getLayout();
        cl.show(backgroundPanel, "AddBookPage");
    }

    private void switchToRemoveBookPage() {
        darkModeButton.setVisible(false);
        logOutButton.setVisible(false);
        CardLayout cardLayout = (CardLayout) backgroundPanel.getLayout();
        cardLayout.show(backgroundPanel, "removeBookPage"); // Ensure "removeBookPage" matches the CardLayout name
    }

    private void switchToUpdateBookPage() {
        darkModeButton.setVisible(false);
        logOutButton.setVisible(false);
        CardLayout cardLayout = (CardLayout) backgroundPanel.getLayout();
        cardLayout.show(backgroundPanel, "updateBookPage"); // Show the updateBookPage
    }

    private void switchToInventoryBookPage() {
        refreshInventoryTable();
        darkModeButton.setVisible(false);
        logOutButton.setVisible(false);
        CardLayout cardLayout = (CardLayout) backgroundPanel.getLayout();
        cardLayout.show(backgroundPanel, "inventoryBookPage"); // Show the inventoryBookPage
    }

    private void switchToCheckViewAvailability() {
        darkModeButton.setVisible(false);
        logOutButton.setVisible(false);
        CardLayout cardLayout = (CardLayout) backgroundPanel.getLayout();
        cardLayout.show(backgroundPanel, "checkBookAvailability");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}