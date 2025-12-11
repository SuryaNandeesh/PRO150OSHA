import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entry point for the Memory Game application.
 * This initializes JavaFX and loads the first scene (main menu).
 * fix main for all
 */
public class Main extends Application {

    private SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Starting Memory Game...");
            sceneManager = new SceneManager(primaryStage);
            System.out.println("SceneManager created");
            // Show the main menu when the application starts
            sceneManager.showMainMenu();
            System.out.println("Main menu shown");
            primaryStage.show();
            System.out.println("Stage shown - application ready!");
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Main method called");
        try {
            launch(args);
        } catch (Exception e) {
            System.err.println("Error launching application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
