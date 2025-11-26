import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the game scene (the memory board).
 * Handles:
 * - Tile clicks
 * - Flipping logic
 * - Checking matches
 * - Score calculation
 * - Detecting win condition
 * - Triggering POST to backend (when implemented)
 */
public class GameController implements Initializable {
    
    @FXML
    private GridPane cardGrid;
    
    @FXML
    private Label scoreLabel;
    
    @FXML
    private Label movesLabel;
    
    @FXML
    private Label timeLabel;
    
    @FXML
    private Button backButton;
    
    private Game game;
    private SceneManager sceneManager;
    private Button[] cardButtons;
    private PauseTransition pauseTransition;
    private Thread timeThread;
    private boolean timeThreadRunning;
    
    // Default board size (4x4 = 16 cards = 8 pairs)
    private static final int ROWS = 4;
    private static final int COLS = 4;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the game with default board size
        game = new Game(ROWS, COLS);
        sceneManager = SceneManager.getInstance();
        
        // Create card buttons
        setupCardGrid();
        
        // Start the timer
        startTimer();
        
        // Update initial display
        updateDisplay();
    }
    
    /**
     * Sets the SceneManager instance (called by SceneManager after loading)
     * @param sceneManager The SceneManager instance
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    /**
     * Sets up the grid of card buttons based on the game board
     */
    private void setupCardGrid() {
        GameBoard board = game.getBoard();
        int totalCards = board.getTotalCards();
        cardButtons = new Button[totalCards];
        
        cardGrid.getChildren().clear();
        cardGrid.setHgap(10);
        cardGrid.setVgap(10);
        cardGrid.setPadding(new Insets(20));
        
        for (int i = 0; i < totalCards; i++) {
            final int index = i;
            Button cardButton = new Button();
            cardButton.setPrefSize(100, 100);
            cardButton.setStyle("-fx-font-size: 24px;");
            cardButton.setText("?");
            cardButton.setOnAction(e -> handleCardClick(index));
            
            cardButtons[index] = cardButton;
            
            // Calculate grid position
            int row = index / board.getCols();
            int col = index % board.getCols();
            cardGrid.add(cardButton, col, row);
        }
    }
    
    /**
     * Handles when a card button is clicked
     * @param cardIndex The index of the clicked card
     */
    private void handleCardClick(int cardIndex) {
        if (game.isGameOver()) {
            return;
        }
        
        // Try to flip the card
        boolean flipped = game.flipCard(cardIndex);
        
        if (flipped) {
            updateCardDisplay(cardIndex);
            updateDisplay();
            
            // If 2 cards are now flipped, check for match after a delay
            if (game.getFlippedCardCount() == 2) {
                // Wait a moment so player can see both cards
                pauseTransition = new PauseTransition(Duration.seconds(1));
                pauseTransition.setOnFinished(e -> {
                    checkMatchAndUpdate();
                });
                pauseTransition.play();
            }
        }
    }
    
    /**
     * Checks if the flipped cards match and updates the display accordingly
     */
    private void checkMatchAndUpdate() {
        // Check if the two flipped cards match
        boolean matched = game.checkMatch();
        
        if (!matched) {
            // No match - flip them back
            game.flipBackCards();
        }
        
        // Update all card displays
        GameBoard board = game.getBoard();
        int totalCards = board.getTotalCards();
        for (int i = 0; i < totalCards; i++) {
            updateCardDisplay(i);
        }
        
        updateDisplay();
        
        // Check if game is over
        if (game.isGameOver()) {
            handleGameOver();
        }
    }
    
    /**
     * Updates the visual display of a single card
     * @param cardIndex The index of the card to update
     */
    private void updateCardDisplay(int cardIndex) {
        Card card = game.getBoard().getCard(cardIndex);
        Button button = cardButtons[cardIndex];
        
        if (card.isMatched()) {
            // Card is matched - show value and disable
            button.setText(String.valueOf(card.getValue()));
            button.setDisable(true);
            button.setStyle("-fx-background-color: #90EE90; -fx-font-size: 24px;");
        } else if (card.isFlipped()) {
            // Card is flipped - show value
            button.setText(String.valueOf(card.getValue()));
            button.setStyle("-fx-background-color: #FFE4B5; -fx-font-size: 24px;");
        } else {
            // Card is face down - show question mark
            button.setText("?");
            button.setStyle("-fx-background-color: #D3D3D3; -fx-font-size: 24px;");
        }
    }
    
    /**
     * Updates the score, moves, and time labels
     */
    private void updateDisplay() {
        scoreLabel.setText("Score: " + game.getScore());
        movesLabel.setText("Moves: " + game.getMoves());
        timeLabel.setText("Time: " + formatTime(game.getElapsedTime()));
    }
    
    /**
     * Formats time in seconds to MM:SS format
     * @param seconds Time in seconds
     * @return Formatted time string
     */
    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
    
    /**
     * Starts a background thread to update the timer every second
     */
    private void startTimer() {
        timeThreadRunning = true;
        timeThread = new Thread(() -> {
            while (timeThreadRunning && !game.isGameOver()) {
                try {
                    Thread.sleep(1000);
                    javafx.application.Platform.runLater(() -> {
                        if (!game.isGameOver()) {
                            updateDisplay();
                        }
                    });
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        timeThread.setDaemon(true);
        timeThread.start();
    }
    
    /**
     * Handles the game over state - shows dialog and prompts for player name
     */
    private void handleGameOver() {
        timeThreadRunning = false;
        
        // Show game over alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over!");
        alert.setHeaderText("Congratulations! You've completed the memory game!");
        alert.setContentText("Score: " + game.getScore() + "\n" +
                           "Moves: " + game.getMoves() + "\n" +
                           "Time: " + formatTime(game.getElapsedTime()));
        alert.showAndWait();
        
        // Prompt for player name
        TextInputDialog dialog = new TextInputDialog("Player");
        dialog.setTitle("Save Score");
        dialog.setHeaderText("Enter your name to save your score:");
        dialog.setContentText("Name:");
        
        dialog.showAndWait().ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                // Create score object
                Score score = new Score(name.trim(), game.getScore(), 
                                      game.getMoves(), game.getElapsedTime());
                
                // TODO: Send score to API when HttpClientService is implemented
                // HttpClientService.getInstance().submitScore(score);
                
                // For now, just show a message
                Alert savedAlert = new Alert(Alert.AlertType.INFORMATION);
                savedAlert.setTitle("Score Saved");
                savedAlert.setHeaderText("Your score has been saved!");
                savedAlert.setContentText("Name: " + score.getPlayerName() + "\n" +
                                        "Score: " + score.getScore());
                savedAlert.showAndWait();
            }
        });
    }
    
    /**
     * Handles the back button click - returns to main menu
     */
    @FXML
    private void handleBackButton() {
        timeThreadRunning = false;
        sceneManager.showMainMenu();
    }
}
