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
        setTitle("Login");
        setSize(1024, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(17, 94, 47));
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel);

        loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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

        JLabel iconLabel = new JLabel("\uD83D\uDC64", SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
        iconLabel.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(iconLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel userIconLabel = new JLabel("\uD83D\uDC64");
        userIconLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        userIconLabel.setForeground(Color.GRAY);
        loginPanel.add(userIconLabel, gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel passwordIconLabel = new JLabel("\uD83D\uDD12");
        passwordIconLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        passwordIconLabel.setForeground(Color.GRAY);
        loginPanel.add(passwordIconLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startAnimation();
                loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                stopAnimation();
                loginButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyLogin();
            }
        });

        backgroundPanel.add(loginPanel);
        setVisible(true);
    }

    private void startAnimation() {
        animationProgress = 0;
        animationTimer = new Timer(15, e -> {
            animationProgress += 0.05;
            if (animationProgress >= 1) {
                animationProgress = 1;
                animationTimer.stop();
            }
            loginButton.repaint();
        });
        animationTimer.start();
    }

    private void stopAnimation() {
        animationProgress = 0;
        loginButton.repaint();
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    private void verifyLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if ("admin".equals(username) && "admin123".equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose();

            SwingUtilities.invokeLater(() -> new MainGUI());
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogInGUI::new);
    }
}
