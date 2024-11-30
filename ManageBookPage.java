import javax.swing.*;
import java.awt.*;

public class ManageBookPage extends JPanel {

    public ManageBookPage(MainGUI mainGUI) {
        setLayout(new BorderLayout());  // Use BorderLayout to manage the layout

        // Title Label at the top
        JLabel titleLabel = new JLabel("Manage Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 45)); // Set larger font size
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);  // Add title to the top of the panel

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());  // Use GridBagLayout for the button panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add spacing between buttons

        // Add Book Button
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton addBookButton = createStyledButton("Add Book");
        addBookButton.addActionListener(e -> mainGUI.switchToPage("AddBookPage"));
        buttonPanel.add(addBookButton, gbc);

        // Remove Book Button
        gbc.gridx = 1;
        gbc.gridy = 0;
        JButton removeBookButton = createStyledButton("Remove Book");
        removeBookButton.addActionListener(e -> {
            // Perform the remove operation and refresh the inventory page
            mainGUI.switchToPage("RemoveBookPage"); // Switch to RemoveBookPage first

            // Refresh inventory after book is removed
            InventoryBookPage inventoryPage = (InventoryBookPage) mainGUI.getPage("InventoryBookPage");
            if (inventoryPage != null) {
                inventoryPage.refreshInventoryTable();  // Refresh the table data
            }
        });
        buttonPanel.add(removeBookButton, gbc);

        // Update Book Button
        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton updateBookButton = createStyledButton("Update Book");
        updateBookButton.addActionListener(e -> mainGUI.switchToPage("UpdateBookPage")); // Directly switch to UpdateBookPage
        buttonPanel.add(updateBookButton, gbc);

        // Book Inventory Button
        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton bookInventoryButton = createStyledButton("Book Inventory");
        bookInventoryButton.addActionListener(e -> {
            // Refresh the inventory before switching to the page
            InventoryBookPage inventoryPage = (InventoryBookPage) mainGUI.getPage("InventoryBookPage");
            if (inventoryPage != null) {
                inventoryPage.refreshInventoryTable();  // Refresh the table data
            }
            mainGUI.switchToPage("InventoryBookPage");  // Switch to Inventory Book Page
        });
        buttonPanel.add(bookInventoryButton, gbc);

        // Add button panel to the center
        add(buttonPanel, BorderLayout.CENTER);

        // Back Button at the bottom
        JButton returnButton = new JButton("Back");
        returnButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        returnButton.setPreferredSize(new Dimension(150, 40));
        returnButton.addActionListener(e -> mainGUI.switchToPage("MainPage"));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Use FlowLayout to align buttons horizontally
        bottomPanel.add(returnButton);  // Add the back button to the bottom panel

        // Add bottom panel to the bottom of the frame
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Method to create a styled button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(200, 50));  // Adjust button size
        return button;
    }
}
