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
    private ApiClientService apiClient;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
        apiClient = ApiClientService.getInstance();
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
        
        if (!apiClient.checkApiHealth()) {
            showAlert("Error", "API server is not running. Please start the API server first.");
            return;
        }
        
        ApiClientService.AuthResult result = apiClient.login(username, password);
        if (result.isSuccess()) {
            UserSession.getInstance().login(result.getUsername());
            showAlert("Success", "Logged in as " + result.getUsername());
            sceneManager.showMainMenu();
        } else {
            showAlert("Error", result.getMessage());
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

        // Simple username rules (same as API, but checked early for user friendliness):
        // - 4 to 20 characters (including spaces)
        // - No leading/trailing spaces
        // - Only single spaces between words
        if (username.length() < 4 || username.length() > 20) {
            showAlert("Error", "Username must be between 4 and 20 characters.");
            return;
        }
        if (username.startsWith(" ") || username.endsWith(" ") || username.contains("  ")) {
            showAlert("Error", "Username can only have single spaces between words.");
            return;
        }

        // Simple password rules (same as API):
        // - 8 to 50 characters
        // - No spaces
        // - At least one capital letter, one number, and one special character
        if (password.length() < 8 || password.length() > 50) {
            showAlert("Error", "Password must be between 8 and 50 characters.");
            return;
        }
        if (password.contains(" ")) {
            showAlert("Error", "Password cannot contain spaces.");
            return;
        }
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        if (!hasUpper || !hasDigit || !hasSpecial) {
            showAlert("Error", "Password needs at least one capital letter, one number, and one special character.");
            return;
        }
        
        if (!apiClient.checkApiHealth()) {
            showAlert("Error", "API server is not running. Please start the API server first.");
            return;
        }
        
        ApiClientService.AuthResult result = apiClient.register(username, password);
        if (result.isSuccess()) {
            UserSession.getInstance().login(result.getUsername());
            showAlert("Success", "Account created! Logged in as " + result.getUsername());
            sceneManager.showMainMenu();
        } else {
            showAlert("Error", result.getMessage());
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

