import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LogInGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel loginPanel;
    private float animationProgress = 0; // Tracks animation progress (0 to 1)
    private Timer animationTimer;

    public LogInGUI() {
        setTitle("Login"); // Sets the title to "Login"
        setSize(1024, 800); // Sets the resolution to 1024x800
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminates the program when you click the x
        setLocationRelativeTo(null);

        // Set the background color
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(17, 94, 47)); // Placeholder gradient-like color
        backgroundPanel.setLayout(new GridBagLayout()); // Center the panel
        add(backgroundPanel); // Adds the background panel

        // Create the main panel for the login box with rounded corners
        loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Semi-transparent white background with rounded corners
                g2.setColor(new Color(255, 255, 255, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };

        loginPanel.setPreferredSize(new Dimension(400, 300));
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add an icon label at the top
        JLabel iconLabel = new JLabel("\uD83D\uDC64", SwingConstants.CENTER); // User icon
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
        iconLabel.setForeground(Color.GRAY); // Set icon color to gray
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(iconLabel, gbc);

        // Username field with icon
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel userIconLabel = new JLabel("\uD83D\uDC64"); // User icon placeholder
        userIconLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        userIconLabel.setForeground(Color.GRAY); // Set icon color to gray
        loginPanel.add(userIconLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        // Password field with icon
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel passwordIconLabel = new JLabel("\uD83D\uDD12"); // Lock icon placeholder
        passwordIconLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        passwordIconLabel.setForeground(Color.GRAY); // Set icon color to gray
        loginPanel.add(passwordIconLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // Customization for the login button
        loginButton = new JButton("Login"); // Creates a new JButton called "Log In"
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.setBackground(Color.BLACK); // Set button background to gray
        loginButton.setForeground(Color.WHITE); // Set button text color to white for contrast
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        // Mouse listener for login button hover effect
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startAnimation(); // Start gradient animation on hover
                loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to pointer
            }

            @Override
            public void mouseExited(MouseEvent e) {
                stopAnimation(); // Stop gradient animation when hover ends
                loginButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Reset cursor
            }
        });

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyLogin();
            }
        });

        backgroundPanel.add(loginPanel);

        setVisible(true);
    }

    // Method to start the hover animation on the login button
    private void startAnimation() {
        animationProgress = 0;
        animationTimer = new Timer(15, e -> {
            animationProgress += 0.05; // Increment progress
            if (animationProgress >= 1) {
                animationProgress = 1;
                animationTimer.stop();
            }
            loginButton.repaint(); // Repaint the button for gradient effect
        });
        animationTimer.start();
    }

    // Method to stop the hover animation
    private void stopAnimation() {
        animationProgress = 0;
        loginButton.repaint();
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    private void verifyLogin() {
        String username = usernameField.getText(); // Gets the username from the username field box.
        String password = new String(passwordField.getPassword()); // Gets the password from the password field box.

        if ("admin".equals(username) && "admin123".equals(password)) { // If the condition is satisfied, then the program will close and another GUI Application will open.
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose(); // Close the login window

            // Launch the main GUI after successful login
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MainGUI(); // Launch the MainGUI
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogInGUI::new);
    }
}
