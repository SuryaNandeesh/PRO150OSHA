import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main menu scene.
 * Handles navigation to game and leaderboard.
 */
public class MainMenuController implements Initializable {
    
    private SceneManager sceneManager;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
    }
    
    /**
     * Sets the SceneManager instance (called by SceneManager after loading)
     * @param sceneManager The SceneManager instance
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    /**
     * Handles the "Start Game" button click
     */
    @FXML
    private void handleStartGame() {
        if (sceneManager != null) {
            sceneManager.showGame();
        }
    }
    
    /**
     * Handles the "Leaderboard" button click
     */
    @FXML
    private void handleLeaderboard() {
        if (sceneManager != null) {
            sceneManager.showLeaderboard();
        }
    }
    
    /**
     * Handles the "Exit" button click
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}

