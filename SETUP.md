# Memory Game - Setup Instructions

## Requirements
- Java JDK 11 or higher
- JavaFX SDK (for JavaFX libraries)

## Project Structure
- `src/main/java/` - All Java source files
- `src/main/resources/views/` - FXML files for UI layouts
- `src/main/resources/styles.css` - CSS styling

## Running the Application

### Option 1: Using an IDE (Recommended)
1. Open the project in your IDE (IntelliJ IDEA, Eclipse, NetBeans, etc.)
2. Make sure JavaFX is added to your project's classpath/module path
3. Set `Main.java` as the main class
4. Run the application

### Option 2: Command Line
1. Compile all Java files:
   ```
   javac -cp "path/to/javafx/lib/*" src/main/java/*.java
   ```

2. Run the application:
   ```
   java --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml -cp "src/main/java:src/main/resources" Main

### Option 3: VS Code (pre-configured)

This repo includes a basic `.vscode` configuration that makes running from VS Code easier. The project assumes JavaFX SDK is installed on your machine and the SDK `lib` folder is available.

1. If you don't have JavaFX SDK installed, download it from https://openjfx.io and unpack it.
2. Open `.vscode/settings.json` and/or `.vscode/launch.json` and update the absolute path(s) to your JavaFX SDK `lib` folder (example: `C:\\Program Files\\Java\\javafx-sdk-21.0.9\\lib`).
3. Use the Run view (or the debug dropdown) and select **Launch Main (JavaFX)** to run the app with VM args that load JavaFX modules.
4. Alternatively use the Tasks view to run the **java: run Main (JavaFX)** task which compiles and launches the app using the configured JavaFX path.
   ```

## Features
- **Memory Game**: 4x4 grid (8 pairs of cards)
- **Score System**: Points for matches, time bonus, moves bonus
- **Leaderboard**: Displays high scores (API integration pending)
- **Timer**: Tracks game time
- **Move Counter**: Tracks number of moves

## API Integration (Future)
The `HttpClientService` class is set up as a skeleton for future API integration. When ready:
1. Update the `API_BASE_URL` constant in `HttpClientService.java`
2. Implement the `getLeaderboard()` method to fetch scores from your API
3. Implement the `submitScore()` method to post scores to your API

## Notes
- The game currently uses placeholder data for the leaderboard
- Score submission is logged to console but not sent to API yet
- All game logic is fully functional and ready to play!

