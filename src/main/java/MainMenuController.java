import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main menu scene.
 */
public class MainMenuController implements Initializable {
    
    @FXML
    private Label userLabel;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label loginWarningLabel;
    
    private SceneManager sceneManager;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
        updateLoginStatus();
    }
    
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        updateLoginStatus();
    }
    
    private void updateLoginStatus() {
        if (UserSession.getInstance().isLoggedIn()) {
            userLabel.setText("Logged in as: " + UserSession.getInstance().getUsername());
            loginButton.setText("Logout");
            if (loginWarningLabel != null) {
                loginWarningLabel.setVisible(false);
            }
        } else {
            userLabel.setText("Not logged in");
            loginButton.setText("Login");
            if (loginWarningLabel != null) {
                loginWarningLabel.setVisible(true);
                loginWarningLabel.setText("⚠️ Login to save your scores to the leaderboard!");
            }
        }
    }
    
    @FXML
    private void handleLogin() {
        if (UserSession.getInstance().isLoggedIn()) {
            UserSession.getInstance().logout();
            updateLoginStatus();
        } else {
            sceneManager.showLogin();
        }
    }
    
    @FXML
    private void handleStartGame() {
        sceneManager.showDifficultySelection();
    }
    
    @FXML
    private void handleLeaderboard() {
        sceneManager.showLeaderboard();
    }
    
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}

