import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A helper class to load FXML files and switch scenes.
 * Manages navigation between different views in the application.
 */
public class SceneManager {
    private Stage primaryStage;
    private static SceneManager instance;
    
    /**
     * Constructor for SceneManager
     * @param primaryStage The main stage of the JavaFX application
     */
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        instance = this;
    }
    
    /**
     * Gets the singleton instance of SceneManager
     * @return The SceneManager instance
     */
    public static SceneManager getInstance() {
        return instance;
    }
    
    /**
     * Loads and displays the main menu scene
     */
    public void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main_menu.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            
            // Set SceneManager in the controller if it's a MainMenuController
            Object controller = loader.getController();
            if (controller instanceof MainMenuController) {
                ((MainMenuController) controller).setSceneManager(this);
            }
            
            primaryStage.setTitle("Memory Game - Main Menu");
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads and displays the difficulty selection scene
     */
    public void showDifficultySelection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/difficulty_selection.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            
            // Set SceneManager in the controller
            Object controller = loader.getController();
            if (controller instanceof DifficultyController) {
                ((DifficultyController) controller).setSceneManager(this);
            }
            
            primaryStage.setTitle("Memory Game - Choose Difficulty");
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads and displays the game scene with specified difficulty
     * @param difficulty The difficulty level ("easy", "medium", or "hard")
     */
    public void showGame(String difficulty) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/game.fxml"));
            
            // Set window size based on difficulty
            int width = 800;
            int height = 600;
            if (difficulty.equals("hard")) {
                width = 1000;
                height = 800;
            } else if (difficulty.equals("medium")) {
                width = 900;
                height = 700;
            }
            
            Scene scene = new Scene(loader.load(), width, height);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            
            // Set SceneManager and difficulty in the controller
            Object controller = loader.getController();
            if (controller instanceof GameController) {
                ((GameController) controller).setSceneManager(this);
                ((GameController) controller).setDifficulty(difficulty);
            }
            
            primaryStage.setTitle("Memory Game - " + difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1));
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads and displays the leaderboard scene
     */
    public void showLeaderboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/leaderboard.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            
            // Set SceneManager in the controller
            Object controller = loader.getController();
            if (controller instanceof LeaderboardController) {
                ((LeaderboardController) controller).setSceneManager(this);
            }
            
            primaryStage.setTitle("Memory Game - Leaderboard");
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
