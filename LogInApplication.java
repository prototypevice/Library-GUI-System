import javax.swing.SwingUtilities;

public class LogInApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LogInGUI()); // Starts the login window
    }
}
