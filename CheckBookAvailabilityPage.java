import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class CheckBookAvailabilityPage extends JPanel {

    private JTable bookTable; // Table to display book availability
    private DefaultTableModel tableModel; // Model for the table

    public CheckBookAvailabilityPage(MainGUI mainGUI) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Top Panel for Icon and Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Load and resize the book icon
        ImageIcon bookIcon = new ImageIcon("src/book.png"); // Replace with your icon path
        Image scaledImage = bookIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Title at the top
        JLabel titleLabel = new JLabel("Check Book Availability", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);

        topPanel.add(iconLabel, BorderLayout.NORTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        String[] columnNames = {"Title", "Author", "Quantity", "Availability"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Initialize the table
        bookTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c != null && column == 3) { // Apply custom rendering to "Availability" column
                    Object value = getValueAt(row, column);
                    if (value instanceof String) {
                        String availability = (String) value;
                        if ("Available".equals(availability)) {
                            c.setForeground(Color.GREEN);
                        } else if ("Not Available".equals(availability)) {
                            c.setForeground(Color.RED);
                        } else {
                            c.setForeground(Color.BLACK);
                        }
                    }
                } else {
                    c.setForeground(Color.BLACK); // Default color for other columns
                }
                return c;
            }
        };

        bookTable.setFillsViewportHeight(true);
        bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        bookTable.setRowHeight(25);
        bookTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bookTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel for Back Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> mainGUI.switchToPage("MainPage"));
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Initially load books into the table
        loadBooksIntoTable();
    }

    // Method to load books into the table
    public void loadBooksIntoTable() {
        tableModel.setRowCount(0); // Clear existing rows

        List<BookManager.Book> books = BookManager.loadBooks(); // Load books from the manager
        for (BookManager.Book book : books) {
            String availability = book.getQuantity() > 0 ? "Available" : "Not Available";
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getQuantity(), availability});
        }
    }

    // Method to refresh the data in the table
    public void refreshData() {
        loadBooksIntoTable(); // Re-load the books into the table
    }
}
