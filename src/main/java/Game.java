import java.util.ArrayList;
import java.util.List;

/**
 * Main game logic class that manages game state, score, and match detection.
 * Tracks moves, time, and handles the core gameplay mechanics.
 */
public class Game {
    private GameBoard board;
    private int score;
    private int moves;
    private long startTime;
    private List<Card> flippedCards; // Tracks currently flipped cards (max 2)
    private boolean isGameOver;
    
    /**
     * Constructor for Game
     * @param rows Number of rows for the game board
     * @param cols Number of columns for the game board
     */
    public Game(int rows, int cols) {
        this.board = new GameBoard(rows, cols);
        this.score = 0;
        this.moves = 0;
        this.flippedCards = new ArrayList<>();
        this.isGameOver = false;
        this.startTime = System.currentTimeMillis();
    }
    
    /**
     * Handles a card click/flip action
     * @param cardIndex The index of the card that was clicked
     * @return true if the card was successfully flipped, false otherwise
     */
    public boolean flipCard(int cardIndex) {
        if (isGameOver) {
            return false;
        }
        
        Card card = board.getCard(cardIndex);
        
        // Can't flip if card is already flipped or matched
        if (card == null || card.isFlipped() || card.isMatched()) {
            return false;
        }
        
        // Can't flip more than 2 cards at once
        if (flippedCards.size() >= 2) {
            return false;
        }
        
        // Flip the card
        card.flip();
        flippedCards.add(card);
        
        // If 2 cards are flipped, increment moves (match checking happens after delay)
        if (flippedCards.size() == 2) {
            moves++;
        }
        
        return true;
    }
    
    /**
     * Checks if the two flipped cards form a match.
     * If they match, marks them as matched and updates score.
     * This should be called after a delay to let the player see both cards.
     * @return true if the cards matched, false otherwise
     */
    public boolean checkMatch() {
        if (flippedCards.size() != 2) {
            return false;
        }
        
        Card card1 = flippedCards.get(0);
        Card card2 = flippedCards.get(1);
        
        if (card1.getValue() == card2.getValue()) {
            // Match found!
            card1.setMatched();
            card2.setMatched();
            score += 100; // Bonus points for a match
            flippedCards.clear();
            
            // Check if game is over
            if (board.allCardsMatched()) {
                isGameOver = true;
                // Calculate final score based on time and moves
                long elapsedTime = System.currentTimeMillis() - startTime;
                int timeBonus = Math.max(0, 1000 - (int)(elapsedTime / 1000)); // Time bonus decreases over time
                int movesBonus = Math.max(0, 500 - (moves * 10)); // Fewer moves = more bonus
                score += timeBonus + movesBonus;
            }
            return true;
        } else {
            // No match - cards will be flipped back by the controller after a delay
            return false;
        }
    }
    
    /**
     * Flips back the currently flipped cards (called when no match is found)
     */
    public void flipBackCards() {
        for (Card card : flippedCards) {
            card.flipBack();
        }
        flippedCards.clear();
    }
    
    /**
     * Gets the current score
     * @return Current score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Gets the number of moves made
     * @return Number of moves
     */
    public int getMoves() {
        return moves;
    }
    
    /**
     * Gets the elapsed time in seconds
     * @return Elapsed time in seconds
     */
    public long getElapsedTime() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }
    
    /**
     * Gets the game board
     * @return The GameBoard instance
     */
    public GameBoard getBoard() {
        return board;
    }
    
    /**
     * Checks if the game is over
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver() {
        return isGameOver;
    }
    
    /**
     * Gets the number of currently flipped cards
     * @return Number of flipped cards (0, 1, or 2)
     */
    public int getFlippedCardCount() {
        return flippedCards.size();
    }
}
