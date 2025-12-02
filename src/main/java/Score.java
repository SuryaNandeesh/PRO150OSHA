import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A simple model representing a leaderboard entry.
 * Contains player name, score, moves, and time information.
 * Implements JavaFX properties for table binding.
 */
public class Score {
    private String playerName;
    private int score;
    private int moves;
    private long timeInSeconds;
    
    /**
     * Constructor for Score
     * @param playerName The name of the player
     * @param score The final score
     * @param moves Number of moves taken
     * @param timeInSeconds Time taken in seconds
     */
    public Score(String playerName, int score, int moves, long timeInSeconds) {
        this.playerName = playerName;
        this.score = score;
        this.moves = moves;
        this.timeInSeconds = timeInSeconds;
    }
    
    /**
     * Gets the player name
     * @return Player name
     */
    public String getPlayerName() {
        return playerName;
    }
    
    /**
     * Gets the score
     * @return Score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Gets the number of moves
     * @return Number of moves
     */
    public int getMoves() {
        return moves;
    }
    
    /**
     * Gets the time in seconds
     * @return Time in seconds
     */
    public long getTimeInSeconds() {
        return timeInSeconds;
    }
    
    /**
     * Formats time as MM:SS
     * @return Formatted time string
     */
    public String getFormattedTime() {
        long minutes = timeInSeconds / 60;
        long seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    
    /**
     * Gets formatted time as a property for JavaFX table binding
     * @return StringProperty containing formatted time
     */
    public StringProperty formattedTimeProperty() {
        return new SimpleStringProperty(getFormattedTime());
    }
}

