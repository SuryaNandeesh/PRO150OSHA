# Memory Game

A JavaFX memory card matching game with user login and SQLite database for score storage.

## Features

- 🎮 **Three Difficulty Levels**
  - Easy: 6x6 grid (Mario theme)
  - Medium: 8x8 grid (Sonic theme)
  - Hard: 10x10 grid (Pokemon theme)

- 👤 **User System**
  - Login/Register from main menu
  - Automatic score saving when logged in
  - No API required - everything runs locally

- 🏆 **Leaderboard**
  - View top scores
  - Scores stored in SQLite database

## Quick Start

### Using Maven
```bash
mvn javafx:run
```

### Using IDE
1. Open project in VS Code, IntelliJ IDEA, or Visual Studio 2022
2. Run `Main.java`

## How to Play

1. Start the application
2. Click "Login" (top right) to create account or login
3. Click "Start Game" and choose difficulty
4. Match pairs of cards
5. Your score is automatically saved when you complete a game (if logged in)
6. View leaderboard from main menu

## Requirements

- Java JDK 11+
- JavaFX (included via Maven)
- SQLite JDBC (included via Maven)

## Project Structure

```
PRO150OSHA/
├── src/main/java/          # Java source code
├── src/main/resources/     # FXML views and CSS
├── images/                 # Card images
└── memory_game.db          # SQLite database (created automatically)
```

## Technologies

- JavaFX for UI
- SQLite for database
- Maven for dependencies

See [SETUP.md](SETUP.md) for detailed setup instructions.
