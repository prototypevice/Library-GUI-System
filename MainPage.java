import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel {

    private JButton darkModeButton;
    private JButton logOutButton;
    private JPanel bottomPanel;
    private boolean isDarkMode = false;
    private JPanel backgroundPanel;
    private JPanel mainPanel;
    private JLabel titleLabel;

    public MainPage(MainGUI mainGUI) {
        setLayout(new BorderLayout());

        // Initialize panels and components
        backgroundPanel = new JPanel();
        mainPanel = new JPanel(new GridBagLayout());
        titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        bottomPanel = new JPanel(new BorderLayout());

        // Set up layout for the main panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        mainPanel.add(titleLabel, gbc);

        // Manage User Button
        gbc.gridy++;
        JButton manageUserButton = createStyledButton("Manage User");
        manageUserButton.addActionListener(e -> mainGUI.switchToPage("ManageUserPage"));
        mainPanel.add(manageUserButton, gbc);

        // Manage Books Button
        gbc.gridy++;
        JButton manageBooksButton = createStyledButton("Manage Books");
        manageBooksButton.addActionListener(e -> mainGUI.switchToPage("ManageBookPage"));
        mainPanel.add(manageBooksButton, gbc);

        // View User Profile Button
        gbc.gridy++;
        JButton viewProfileButton = createStyledButton("View User Profile");
        viewProfileButton.addActionListener(e -> mainGUI.switchToPage("ViewUserProfilePage"));
        mainPanel.add(viewProfileButton, gbc);

        // Check Book Availability Button
        gbc.gridy++;
        JButton checkBookButton = createStyledButton("Check Book Availability");
        checkBookButton.addActionListener(e -> {
            // Refresh the data before switching to the CheckBookAvailabilityPage
            CheckBookAvailabilityPage checkBookAvailabilityPage = (CheckBookAvailabilityPage) mainGUI.getPage("CheckBookAvailabilityPage");
            if (checkBookAvailabilityPage != null) {
                checkBookAvailabilityPage.refreshData();  // Refresh the data on that page
            }
            mainGUI.switchToPage("CheckBookAvailabilityPage");  // Switch to the Check Book Availability Page
        });
        mainPanel.add(checkBookButton, gbc);

        // Add the mainPanel to the center of the layout
        add(mainPanel, BorderLayout.CENTER);

        // Dark Mode Toggle Button
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
            mainGUI.dispose();
            SwingUtilities.invokeLater(LogInGUI::new);
        });

        // Bottom panel setup
        bottomPanel.add(darkModeButton, BorderLayout.WEST);
        bottomPanel.add(logOutButton, BorderLayout.EAST);
        bottomPanel.setBackground(Color.white);
        add(bottomPanel, BorderLayout.SOUTH);

        applyLightMode(); // Initial light mode setup
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(150, 40));  // Set to fixed width and height, but shorter length
        return button;
    }

    private void applyLightMode() {
        backgroundPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBackground(new Color(255, 255, 255));
        bottomPanel.setBackground(new Color(255, 255, 255));
        titleLabel.setForeground(Color.BLACK);
        logOutButton.setBackground(Color.BLACK);
        logOutButton.setForeground(Color.WHITE);
        darkModeButton.setText("🌙");
        darkModeButton.setForeground(Color.BLACK);
    }

    private void applyDarkMode() {
        backgroundPanel.setBackground(new Color(45, 45, 45));
        mainPanel.setBackground(new Color(45, 45, 45));
        bottomPanel.setBackground(new Color(45, 45, 45));
        titleLabel.setForeground(Color.WHITE);
        logOutButton.setBackground(new Color(24, 65, 4));
        logOutButton.setForeground(Color.white);
        darkModeButton.setText("🌞");
        darkModeButton.setForeground(Color.WHITE);
    }

    private void toggleDarkMode() {
        if (isDarkMode) {
            applyLightMode();
        } else {
            applyDarkMode();
        }
        isDarkMode = !isDarkMode;
    }
}
