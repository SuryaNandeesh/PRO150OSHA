import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the game board - a grid of cards arranged in pairs.
 * Handles card shuffling and board initialization.
 */
public class GameBoard {
    private List<Card> cards;
    private int rows;
    private int cols;
    private String difficulty;
    
    /**
     * Constructor for GameBoard
     * @param rows Number of rows in the grid
     * @param cols Number of columns in the grid
     * @param difficulty The difficulty level ("easy", "medium", or "hard")
     */
    public GameBoard(int rows, int cols, String difficulty) {
        this.rows = rows;
        this.cols = cols;
        this.difficulty = difficulty;
        this.cards = new ArrayList<>();
        initializeBoard();
    }
    
    /**
     * Initializes the board with pairs of cards and shuffles them.
     * Creates (rows * cols / 2) pairs of cards using images from the appropriate folder.
     */
    private void initializeBoard() {
        int totalCards = rows * cols;
        int pairs = totalCards / 2;
        
        // Get the image folder path based on difficulty
        String folderName = "";
        if (difficulty.equals("easy")) {
            folderName = "Mario (6x6 Easy)";
        } else if (difficulty.equals("medium")) {
            folderName = "Sonic (8x8 Medium)";
        } else if (difficulty.equals("hard")) {
            folderName = "Pokemon (10x10 Hard)";
        }
        
        // Try to find the images folder - check multiple possible locations
        File imagesDir = null;
        File currentDir = new File(System.getProperty("user.dir"));
        
        // Strategy: Find the project root by looking for the "src" or ".vscode" folder
        File projectRoot = null;
        File searchDir = currentDir;
        
        // Go up the directory tree until we find a folder with "src" or ".vscode"
        while (searchDir != null) {
            File srcDir = new File(searchDir, "src");
            File vscodeDir = new File(searchDir, ".vscode");
            if ((srcDir.exists() && srcDir.isDirectory()) || 
                (vscodeDir.exists() && vscodeDir.isDirectory())) {
                projectRoot = searchDir;
                break;
            }
            searchDir = searchDir.getParentFile();
        }
        
        // If we found the project root, use it; otherwise try current directory
        if (projectRoot != null) {
            File testDir = new File(projectRoot, "images/" + folderName);
            if (testDir.exists() && testDir.isDirectory()) {
                imagesDir = testDir;
            }
        }
        
        // Fallback: try relative to current directory
        if (imagesDir == null) {
            File testDir = new File(currentDir, "images/" + folderName);
            if (testDir.exists() && testDir.isDirectory()) {
                imagesDir = testDir;
            }
        }
        
        // Another fallback: try going up from current directory
        if (imagesDir == null && currentDir.getParent() != null) {
            File testDir = new File(currentDir.getParent(), "images/" + folderName);
            if (testDir.exists() && testDir.isDirectory()) {
                imagesDir = testDir;
            }
        }
        
        File[] imageFiles = null;
        String basePath = "";
        if (imagesDir != null && imagesDir.exists()) {
            imageFiles = imagesDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
            if (imageFiles != null) {
                basePath = imagesDir.getAbsolutePath() + File.separator;
                System.out.println("Found images folder: " + basePath);
                System.out.println("Found " + imageFiles.length + " images, need " + pairs + " pairs");
            }
        } else {
            System.out.println("Images folder not found! Current dir: " + currentDir.getAbsolutePath());
            System.out.println("Looking for: images/" + folderName);
        }
        
        if (imageFiles == null || imageFiles.length < pairs) {
            // Fallback: create cards with numbers if images not found
            System.out.println("Using fallback: creating cards with numbers");
            for (int i = 0; i < pairs; i++) {
                cards.add(new Card("" + i));
                cards.add(new Card("" + i));
            }
        } else {
            // Create pairs of cards with absolute image paths
            for (int i = 0; i < pairs; i++) {
                String imagePath = basePath + imageFiles[i].getName();
                cards.add(new Card(imagePath));
                cards.add(new Card(imagePath));
            }
        }
        
        // Shuffle the cards randomly
        Collections.shuffle(cards);
    }
    
    /**
     * Gets a card at a specific index
     * @param index The position in the cards list
     * @return The card at that index
     */
    public Card getCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.get(index);
        }
        return null;
    }
    
    /**
     * Gets the total number of cards on the board
     * @return Total number of cards
     */
    public int getTotalCards() {
        return cards.size();
    }
    
    /**
     * Gets the number of rows
     * @return Number of rows
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Gets the number of columns
     * @return Number of columns
     */
    public int getCols() {
        return cols;
    }
    
    /**
     * Checks if all cards have been matched (win condition)
     * @return true if all cards are matched, false otherwise
     */
    public boolean allCardsMatched() {
        for (Card card : cards) {
            if (!card.isMatched()) {
                return false;
            }
        }
        return true;
    }
}
