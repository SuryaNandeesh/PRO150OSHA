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
     * Loads and displays the game scene
     */
    public void showGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/game.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            
            // Set SceneManager in the controller
            Object controller = loader.getController();
            if (controller instanceof GameController) {
                ((GameController) controller).setSceneManager(this);
            }
            
            primaryStage.setTitle("Memory Game");
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
