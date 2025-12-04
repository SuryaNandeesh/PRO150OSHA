/**
 * Represents a single card in the memory game.
 * Each card has an image path (the pair identifier) and a state (face up or face down).
 */
public class Card {
    private String imagePath; // The image path (two cards with the same path form a pair)
    private boolean isFlipped; // Whether the card is currently face up
    private boolean isMatched; // Whether this card has been matched with its pair
    
    /**
     * Constructor for Card
     * @param imagePath The image path for this card
     */
    public Card(String imagePath) {
        this.imagePath = imagePath;
        this.isFlipped = false;
        this.isMatched = false;
    }
    
    /**
     * Flips the card to show its face
     */
    public void flip() {
        this.isFlipped = true;
    }
    
    /**
     * Flips the card back to hide its face
     */
    public void flipBack() {
        if (!isMatched) {
            this.isFlipped = false;
        }
    }
    
    /**
     * Marks this card as matched (permanently face up)
     */
    public void setMatched() {
        this.isMatched = true;
        this.isFlipped = true;
    }
    
    /**
     * Gets the image path (pair identifier) of this card
     * @return The card's image path
     */
    public String getImagePath() {
        return imagePath;
    }
    
    /**
     * Checks if the card is currently face up
     * @return true if face up, false otherwise
     */
    public boolean isFlipped() {
        return isFlipped;
    }
    
    /**
     * Checks if the card has been matched
     * @return true if matched, false otherwise
     */
    public boolean isMatched() {
        return isMatched;
    }
}
