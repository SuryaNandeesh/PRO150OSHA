import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the login/register page.
 */
public class LoginController implements Initializable {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private Button backButton;
    
    private SceneManager sceneManager;
    private DatabaseService db;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
        db = DatabaseService.getInstance();
    }
    
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }
        
        if (db.loginUser(username, password)) {
            UserSession.getInstance().login(username);
            showAlert("Success", "Logged in as " + username);
            sceneManager.showMainMenu();
        } else {
            showAlert("Error", "Invalid username or password.");
        }
    }
    
    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }
        
        if (db.registerUser(username, password)) {
            UserSession.getInstance().login(username);
            showAlert("Success", "Account created! Logged in as " + username);
            sceneManager.showMainMenu();
        } else {
            showAlert("Error", "Username already exists. Please choose another.");
        }
    }
    
    @FXML
    private void handleBack() {
        sceneManager.showMainMenu();
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

