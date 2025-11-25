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
    
    /**
     * Constructor for GameBoard
     * @param rows Number of rows in the grid
     * @param cols Number of columns in the grid
     */
    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cards = new ArrayList<>();
        initializeBoard();
    }
    
    /**
     * Initializes the board with pairs of cards and shuffles them.
     * Creates (rows * cols / 2) pairs of cards.
     */
    private void initializeBoard() {
        int totalCards = rows * cols;
        int pairs = totalCards / 2;
        
        // Create pairs of cards
        for (int i = 0; i < pairs; i++) {
            cards.add(new Card(i));
            cards.add(new Card(i));
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
