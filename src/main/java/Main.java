import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entry point for the Memory Game application.
 * This initializes JavaFX and loads the first scene (main menu).
 */
public class Main extends Application {

    private SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) {
        sceneManager = new SceneManager(primaryStage);
        // Show the main menu when the application starts
        sceneManager.showMainMenu();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}