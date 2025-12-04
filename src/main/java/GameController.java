import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
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
    private String difficulty;
    private int rows;
    private int cols;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
        // Game will be initialized when difficulty is set
    }
    
    /**
     * Sets the difficulty and initializes the game
     * @param difficulty The difficulty level ("easy", "medium", or "hard")
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        System.out.println("Setting difficulty to: " + difficulty);
        
        // Set board size based on difficulty
        if (difficulty.equals("easy")) {
            rows = 6;
            cols = 6;
        } else if (difficulty.equals("medium")) {
            rows = 8;
            cols = 8;
        } else if (difficulty.equals("hard")) {
            rows = 10;
            cols = 10;
        } else {
            // Default fallback
            rows = 4;
            cols = 4;
        }
        
        System.out.println("Board size: " + rows + "x" + cols);
        
        // Initialize the game with the selected difficulty
        game = new Game(rows, cols, difficulty);
        
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
        cardGrid.setHgap(5);
        cardGrid.setVgap(5);
        cardGrid.setPadding(new Insets(20));
        
        // Calculate button size based on board size
        int buttonSize = 80;
        if (rows == 6) {
            buttonSize = 80;
        } else if (rows == 8) {
            buttonSize = 70;
        } else if (rows == 10) {
            buttonSize = 60;
        }
        
        for (int i = 0; i < totalCards; i++) {
            final int index = i;
            Button cardButton = new Button();
            cardButton.setPrefSize(buttonSize, buttonSize);
            cardButton.setText("?");
            cardButton.setStyle("-fx-font-size: 20px;");
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
            // Card is matched - show image and disable
            setCardImage(button, card.getImagePath());
            button.setDisable(true);
            button.setStyle("-fx-background-color: #90EE90;");
        } else if (card.isFlipped()) {
            // Card is flipped - show image
            setCardImage(button, card.getImagePath());
            button.setStyle("-fx-background-color: #FFE4B5;");
        } else {
            // Card is face down - show question mark
            button.setText("?");
            button.setGraphic(null);
            button.setStyle("-fx-background-color: #D3D3D3; -fx-font-size: 20px;");
        }
    }
    
    /**
     * Sets an image on a button
     * @param button The button to set the image on
     * @param imagePath The path to the image file
     */
    private void setCardImage(Button button, String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Image image = new Image(imageFile.toURI().toString());
                ImageView imageView = new ImageView(image);
                
                // Set image size based on button size
                int imageSize = 60;
                if (rows == 6) {
                    imageSize = 60;
                } else if (rows == 8) {
                    imageSize = 50;
                } else if (rows == 10) {
                    imageSize = 40;
                }
                
                imageView.setFitWidth(imageSize);
                imageView.setFitHeight(imageSize);
                imageView.setPreserveRatio(true);
                
                button.setGraphic(imageView);
                button.setText("");
            } else {
                // Fallback if image not found
                System.out.println("Image not found: " + imagePath);
                button.setText("?");
                button.setGraphic(null);
            }
        } catch (Exception e) {
            // Fallback if image loading fails
            System.out.println("Error loading image: " + imagePath + " - " + e.getMessage());
            button.setText("?");
            button.setGraphic(null);
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
