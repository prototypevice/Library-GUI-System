import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryBookPage extends JPanel {
    private JPanel centerPanel;
    private JTable bookTable;

    public InventoryBookPage(MainGUI mainGUI) {
        setLayout(new BorderLayout());

        // Create the top panel to hold the icon and title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(new BorderLayout());

        // Load and resize the book icon to a smaller size
        ImageIcon bookIcon = new ImageIcon("src/book.png");
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
        add(topPanel, BorderLayout.NORTH);

        // Initialize and add the center panel
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);

        // Load and display books in the inventory
        refreshInventoryTable();

        // Bottom panel for the back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> mainGUI.switchToPage("ManageBookPage"));

        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void refreshInventoryTable() {
        List<BookManager.Book> books = BookManager.loadBooks();
        String[] columnNames = {"Title", "Publisher", "ISBN", "Author", "Quantity", "Genre"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (BookManager.Book book : books) {
            model.addRow(new Object[]{
                    book.getTitle(),
                    book.getPublisher(),
                    book.getIsbn(),
                    book.getAuthor(),
                    book.getQuantity(),
                    book.getGenre()
            });
        }

        if (bookTable == null) {
            bookTable = new JTable(model);
            bookTable.setFillsViewportHeight(true);
            bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            bookTable.setRowHeight(25);
            bookTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
            bookTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

            JScrollPane scrollPane = new JScrollPane(bookTable);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
            centerPanel.removeAll();
            centerPanel.add(scrollPane, BorderLayout.CENTER);
        } else {
            bookTable.setModel(model);
            bookTable.repaint();
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }
}
