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

