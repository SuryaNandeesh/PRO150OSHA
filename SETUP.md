# Memory Game - Setup Instructions

## Overview
A JavaFX memory card matching game with user login and SQLite database for score storage.

## Requirements
- **Java JDK 11 or higher** (JDK 17 recommended)
- **JavaFX SDK** (included via Maven)
- **Maven** (optional, but recommended)

## Quick Start

### Option 1: Using Maven (Recommended)
```bash
mvn clean compile
mvn javafx:run
```

### Option 2: Using IDE
1. Open project in VS Code, IntelliJ IDEA, or Visual Studio 2022
2. Wait for Maven to sync dependencies (if using Maven)
3. Run `Main.java`

## Features

### User System
- **Login/Register**: Click "Login" button at top right of main menu
- **Auto-save Scores**: Scores automatically saved when logged in
- **No API Required**: Everything runs locally with SQLite database

### Game Features
- Three difficulty levels:
  - Easy: 6x6 grid (Mario theme)
  - Medium: 8x8 grid (Sonic theme)
  - Hard: 10x10 grid (Pokemon theme)
- Score tracking with moves and time
- Leaderboard showing top scores

## How It Works

1. **Start the Game**: Run `Main.java`
2. **Login/Register**: Click "Login" button to create account or login
3. **Play Game**: Choose difficulty and play
4. **Auto-save**: When you complete a game while logged in, your score is automatically saved
5. **View Leaderboard**: See top scores from the main menu

## Database

The game uses SQLite database (`memory_game.db`) created automatically in the project root. It stores:
- User accounts (username, password)
- Scores (username, score, moves, time, difficulty)

## Troubleshooting

### Images Not Loading
- Ensure `images/` folder is in project root
- Check folder names match exactly:
  - `Mario (6x6 Easy)`
  - `Sonic (8x8 Medium)`
  - `Pokemon (10x10 Hard)`

### JavaFX Not Found
- If using Maven: Dependencies download automatically
- If manual: Download JavaFX SDK from https://openjfx.io

### SQLite Driver Not Found
- Maven handles this automatically
- If manual: Add sqlite-jdbc JAR to classpath

## Project Structure
```
PRO150OSHA/
├── src/main/java/          # Java source code
│   ├── Main.java           # Entry point
│   ├── DatabaseService.java # SQLite database
│   ├── UserSession.java     # Login session
│   └── ...
├── src/main/resources/
│   ├── views/              # FXML files
│   └── styles.css
└── images/                 # Card images
```

Enjoy playing!
