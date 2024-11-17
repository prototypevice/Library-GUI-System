import javax.swing.*;
import java.awt.*;

public class ManageUserPanel extends JPanel {
    private MainGUI mainGUI;

    public ManageUserPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add Book Button
        JButton addBookButton = createStyledButton("Add User");
        buttonPanel.add(addBookButton, gbc);

        gbc.gridy++;
        // Remove Book Button
        JButton removeBookButton = createStyledButton("Remove User");
        buttonPanel.add(removeBookButton, gbc);

        gbc.gridy++;
        // Update Book Button
        JButton updateBookButton = createStyledButton("Update User");
        buttonPanel.add(updateBookButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        // Return Button
        JButton returnButton = new JButton("Return to Home");
        returnButton.setPreferredSize(new Dimension(150, 40));
        returnButton.addActionListener(e -> mainGUI.switchToMainPage());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(returnButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 40));
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setFocusPainted(false);
        return button;
    }

    //COMMENT
}
