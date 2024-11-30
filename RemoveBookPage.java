import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class RemoveBookPage extends JPanel {

    private MainGUI mainGUI; // Reference to MainGUI

    public RemoveBookPage(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        initializeUI();
    }

    private void initializeUI() {
        removeAll(); // Clear the current UI components
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Top Panel for Icon and Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        ImageIcon bookIcon = new ImageIcon("src/book.png");
        Image scaledImage = bookIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        bookIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(bookIcon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(iconLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Remove Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel for displaying the list of books
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        List<BookManager.Book> books = BookManager.loadBooks();
        if (!books.isEmpty()) {
            String[] columns = {"Title", "Author", "Publisher", "ISBN", "Quantity", "Genre", "Remove"};
            Object[][] data = new Object[books.size()][7];

            for (int i = 0; i < books.size(); i++) {
                BookManager.Book book = books.get(i);
                data[i][0] = book.getTitle();
                data[i][1] = book.getAuthor();
                data[i][2] = book.getPublisher();
                data[i][3] = book.getIsbn();
                data[i][4] = book.getQuantity();
                data[i][5] = book.getGenre();
                data[i][6] = "Remove"; // The button label
            }

            JTable table = new JTable(data, columns);
            table.setRowHeight(30);
            table.getColumn("Remove").setCellRenderer(new ButtonRenderer());
            table.getColumn("Remove").setCellEditor(new ButtonEditor(new JCheckBox(), books, this));

            JScrollPane scrollPane = new JScrollPane(table);
            centerPanel.add(scrollPane, BorderLayout.CENTER);
        } else {
            JLabel noBooksLabel = new JLabel("No books available to remove.");
            noBooksLabel.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(noBooksLabel, BorderLayout.CENTER);
        }

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(200, 50));
        backButton.addActionListener(e -> mainGUI.switchToPage("ManageBookPage"));
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void reloadPage() {
        initializeUI();
    }

    private void refreshInventoryTable() {
        InventoryBookPage inventoryPage = (InventoryBookPage) mainGUI.getPage("InventoryBookPage");
        if (inventoryPage != null) {
            inventoryPage.refreshInventoryTable();
        }
    }

    private void refreshAvailabilityTable() {
        CheckBookAvailabilityPage availabilityPage = (CheckBookAvailabilityPage) mainGUI.getPage("CheckBookAvailabilityPage");
        if (availabilityPage != null) {
            availabilityPage.loadBooksIntoTable();
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private int row;
        private List<BookManager.Book> books;
        private JPanel parentPanel;

        public ButtonEditor(JCheckBox checkBox, List<BookManager.Book> books, JPanel parentPanel) {
            super(checkBox);
            this.books = books;
            this.parentPanel = parentPanel;
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(e -> {
                JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, button);
                if (table != null) {
                    row = table.getSelectedRow();
                    String bookTitle = (String) table.getValueAt(row, 0);

                    BookManager.Book selectedBook = books.stream()
                            .filter(book -> book.getTitle().equalsIgnoreCase(bookTitle))
                            .findFirst().orElse(null);

                    if (selectedBook != null) {
                        int confirmOption = JOptionPane.showConfirmDialog(
                                parentPanel,
                                "Are you sure you want to delete the book titled \"" + selectedBook.getTitle() + "\"?",
                                "Confirm Deletion",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (confirmOption == JOptionPane.YES_OPTION) {
                            boolean removed = BookManager.removeBookByTitle(selectedBook.getTitle());
                            if (removed) {
                                JOptionPane.showMessageDialog(parentPanel, "Book removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                books.remove(selectedBook);
                                refreshInventoryTable();
                                refreshAvailabilityTable();
                                reloadPage(); // Reload the page to refresh the table
                            } else {
                                JOptionPane.showMessageDialog(parentPanel, "Failed to remove the book.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        public Object getCellEditorValue() {
            return label;
        }
    }
}
