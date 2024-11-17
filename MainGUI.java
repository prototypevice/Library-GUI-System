import javax.swing.*;
import java.awt.*;

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
        mainPanel.add(manageBooksButton, gbc);

        gbc.gridy++;
        JButton viewProfileButton = createStyledButton("View User Profile");
        viewProfileButton.addActionListener(e -> switchToViewUserProfilePage());
        mainPanel.add(viewProfileButton, gbc);

        gbc.gridy++;
        JButton checkBookButton = createStyledButton("Check Book Availability");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
