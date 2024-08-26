import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class readerMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bookshelf Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);

        Shelf shelf = new Shelf();
        String file = "bookshelf.csv";

        JTextArea shelfDisplay = new JTextArea();
        shelfDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        shelfDisplay.setEditable(false);
        shelfDisplay.setMargin(new Insets(10, 250, 10, 100));  // Adds padding around the text

        shelf.updateShelfDisplay(shelfDisplay);

        JButton addButton = new JButton("Add Book");
        JButton removeButton = new JButton("Remove Book");
        JButton searchButton = new JButton("Search Book");
        JButton exitButton = new JButton("Exit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));  // 1 row, 4 columns, with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Adds padding around the buttons
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(exitButton);

        frame.setLayout(new BorderLayout());
        frame.add(shelfDisplay, BorderLayout.CENTER);  // Center the shelf display
        frame.add(buttonPanel, BorderLayout.SOUTH);  // Move buttons to the bottom

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idInput = JOptionPane.showInputDialog("Enter book ID/NAME:");
                char id = idInput.charAt(0);
                int row = Integer.parseInt(JOptionPane.showInputDialog("Enter row (0,5):"));
                int column = Integer.parseInt(JOptionPane.showInputDialog("Enter column (0,5):"));
                shelf.addBook(row, column, id, shelfDisplay);
                shelf.saveToFile(file);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] removeOptions = {"By Position", "By Book ID/NAME"};
                int removeChoice = JOptionPane.showOptionDialog(null, "Remove by position or book ID/NAME?", "Remove Book",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, removeOptions, removeOptions[0]);
                if (removeChoice == 0) {
                    int posRow = Integer.parseInt(JOptionPane.showInputDialog("Enter row (0,5):"));
                    int posColumn = Integer.parseInt(JOptionPane.showInputDialog("Enter column (0,5):"));
                    shelf.removeBook(posRow, posColumn, shelfDisplay);
                    shelf.saveToFile(file);
                } else if (removeChoice == 1) {
                    char bookId = JOptionPane.showInputDialog("Enter book ID/NAME:").charAt(0);
                    shelf.removeBookID(bookId, shelfDisplay);
                    shelf.saveToFile(file);
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] searchOptions = {"By Position", "By Book ID/NAME"};
                int searchChoice = JOptionPane.showOptionDialog(null, "Find book by position or ID/NAME?", "Search Book",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, searchOptions, searchOptions[0]);
                if (searchChoice == 0) {
                    int posRow = Integer.parseInt(JOptionPane.showInputDialog("Enter row (0,5):"));
                    int posColumn = Integer.parseInt(JOptionPane.showInputDialog("Enter column (0,5):"));
                    char bookId = shelf.getBook(posRow, posColumn);
                    if (bookId == '?') {
                        JOptionPane.showMessageDialog(null, "Book not found.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book " + bookId + " found.");
                    }
                } else if (searchChoice == 1) {
                    char findBookId = JOptionPane.showInputDialog("Enter book ID/NAME:").charAt(0);
                    String position = shelf.getBookId(findBookId);
                    if (position != null) {
                        JOptionPane.showMessageDialog(null, "Book " + findBookId + " found at position " + position);
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found.");
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "See you next time!");
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}