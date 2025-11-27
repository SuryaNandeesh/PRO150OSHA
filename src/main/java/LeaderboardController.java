import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the leaderboard scene.
 * Displays high scores retrieved from the API (when implemented).
 */
public class LeaderboardController implements Initializable {
    
    @FXML
    private TableView<Score> leaderboardTable;
    
    @FXML
    private TableColumn<Score, String> nameColumn;
    
    @FXML
    private TableColumn<Score, Integer> scoreColumn;
    
    @FXML
    private TableColumn<Score, Integer> movesColumn;
    
    @FXML
    private TableColumn<Score, String> timeColumn;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private Label statusLabel;
    
    private SceneManager sceneManager;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
        
        // Set up table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        movesColumn.setCellValueFactory(new PropertyValueFactory<>("moves"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedTime"));
        
        // Load leaderboard data
        loadLeaderboard();
    }
    
    /**
     * Sets the SceneManager instance (called by SceneManager after loading)
     * @param sceneManager The SceneManager instance
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    /**
     * Loads leaderboard data from the API (or shows placeholder data)
     */
    private void loadLeaderboard() {
        // TODO: Replace with actual API call when HttpClientService is implemented
        // List<Score> scores = HttpClientService.getInstance().getLeaderboard();
        
        // For now, show placeholder message
        statusLabel.setText("Leaderboard data will be loaded from API when implemented.");
        
        // Example placeholder data (remove when API is implemented)
        List<Score> placeholderScores = new ArrayList<>();
        placeholderScores.add(new Score("Player 1", 1500, 25, 120));
        placeholderScores.add(new Score("Player 2", 1200, 30, 150));
        placeholderScores.add(new Score("Player 3", 1000, 35, 180));
        
        leaderboardTable.getItems().setAll(placeholderScores);
    }
    
    /**
     * Handles the refresh button click - reloads leaderboard data
     */
    @FXML
    private void handleRefresh() {
        loadLeaderboard();
    }
    
    /**
     * Handles the back button click - returns to main menu
     */
    @FXML
    private void handleBack() {
        if (sceneManager != null) {
            sceneManager.showMainMenu();
        }
    }
}

