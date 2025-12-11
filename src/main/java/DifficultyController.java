import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the difficulty selection scene.
 * Handles navigation to game with selected difficulty.
 */
public class DifficultyController implements Initializable {
    
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
     * Handles the "Easy" button click
     */
    @FXML
    private void handleEasy() {
        if (sceneManager != null) {
            sceneManager.showGame("easy");
        }
    }
    
    /**
     * Handles the "Medium" button click
     */
    @FXML
    private void handleMedium() {
        if (sceneManager != null) {
            sceneManager.showGame("medium");
        }
    }
    
    /**
     * Handles the "Hard" button click
     */
    @FXML
    private void handleHard() {
        if (sceneManager != null) {
            sceneManager.showGame("hard");
        }
    }
    
    /**
     * Handles the "Back" button click
     */
    @FXML
    private void handleBack() {
        if (sceneManager != null) {
            sceneManager.showMainMenu();
        }
    }
}
