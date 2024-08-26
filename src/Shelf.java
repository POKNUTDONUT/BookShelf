import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Shelf {
    private final HashMap<String, Character> shelf;
    private final int rows = 6;
    private final int columns = 6;

    public Shelf() {
        shelf = new HashMap<>();
    }

    public void addBook(int row, int column, char bookId, JTextArea shelfDisplay) {
        if (isValidPosition(row, column)) {
            shelf.put(getKey(row, column), bookId);
            JOptionPane.showMessageDialog(null, bookId + " has been added to the shelf!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid position");
        }
        updateShelfDisplay(shelfDisplay);
    }

    public char getBook(int row, int column) {
        char emptySlot = '?';
        if (isValidPosition(row, column)) {
            return shelf.getOrDefault(getKey(row, column), emptySlot);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid position");
            return emptySlot;
        }
    }

    public String getBookId(char bookId) {
        for (Map.Entry<String, Character> entry : shelf.entrySet()) {
            if (entry.getValue() == bookId) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void removeBook(int row, int column, JTextArea shelfDisplay) {
        if (isValidPosition(row, column)) {
            shelf.remove(getKey(row, column));
            JOptionPane.showMessageDialog(null, "Book removed from the shelf at position " + getKey(row, column));
        } else {
            JOptionPane.showMessageDialog(null, "Invalid position");
        }
        updateShelfDisplay(shelfDisplay);
    }

    public void removeBookID(char bookId, JTextArea shelfDisplay) {
        String keyToRemove = null;
        for (Map.Entry<String, Character> entry : shelf.entrySet()) {
            if (entry.getValue() == bookId) {
                keyToRemove = entry.getKey();
            }
        }

        if (keyToRemove != null) {
            shelf.remove(keyToRemove);
            JOptionPane.showMessageDialog(null, "Book " + bookId + " removed from position " + keyToRemove);
        } else {
            JOptionPane.showMessageDialog(null, "Book " + bookId + " not found");
        }
        updateShelfDisplay(shelfDisplay);
    }

    public void updateShelfDisplay(JTextArea shelfDisplay) {
        StringBuilder shelfRepresentation = new StringBuilder();
        shelfRepresentation.append("+ ").append(" - ".repeat(columns - 2)).append("+\n");  // Adjust for added spaces
        for (int i = 0; i < rows; i++) {
            shelfRepresentation.append("| ");
            for (int j = 0; j < columns; j++) {
                shelfRepresentation.append(getBook(i, j)).append(" ");  // Add space between each character
            }
            shelfRepresentation.append("|\n");
        }
        shelfRepresentation.append("+ ").append(" - ".repeat(columns - 2)).append("+\n");  // Adjust for added spaces
        shelfDisplay.setText(shelfRepresentation.toString());
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, Character> entry : shelf.entrySet()) {
                writer.write(entry.getKey() + " , " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving to file: " + e.getMessage());
        }
    }

    private String getKey(int row, int column) {
        return row + " , " + column;
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }
}