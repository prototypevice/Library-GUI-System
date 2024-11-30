import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainGUI extends JFrame {

    private JPanel backgroundPanel; // The main container for pages
    private Map<String, JPanel> pageMap; // A map to store pages by name

    public MainGUI() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the background panel with CardLayout
        backgroundPanel = new JPanel(new CardLayout());
        add(backgroundPanel);

        // Initialize page map
        pageMap = new HashMap<>();

        // Add pages to CardLayout
        addPage(new MainPage(this), "MainPage");
        addPage(new ManageBookPage(this), "ManageBookPage");
        addPage(new AddBookPage(this), "AddBookPage");
        addPage(new RemoveBookPage(this), "RemoveBookPage");
        addPage(new UpdateBookPage(this), "UpdateBookPage");
        addPage(new InventoryBookPage(this), "InventoryBookPage");
        addPage(new CheckBookAvailabilityPage(this), "CheckBookAvailabilityPage");

        // Start on the Main Page
        switchToPage("MainPage");

        setVisible(true);
    }

    // Helper method to add a page to the CardLayout and page map
    private void addPage(JPanel page, String name) {
        backgroundPanel.add(page, name);
        pageMap.put(name, page); // Store pages by name
    }

    // Method to switch between pages using CardLayout
    public void switchToPage(String pageName) {
        CardLayout cl = (CardLayout) backgroundPanel.getLayout();
        cl.show(backgroundPanel, pageName);
    }

    // Method to show the main page
    public void showMainPage() {
        switchToPage("MainPage");
    }

    // Method to get a specific page by name
    public JPanel getPage(String pageName) {
        return pageMap.get(pageName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
