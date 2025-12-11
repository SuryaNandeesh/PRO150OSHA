# Memory Game

JavaFX desktop memory game with login, leaderboard, and a local Spring Boot API.  
Match cards, save scores, and view the leaderboard (both in-app and in the browser).

## Features

- **Three difficulty levels**
  - Easy: 6x6 grid (Mario)
  - Medium: 8x8 grid (Sonic)
  - Hard: 10x10 grid (Pokemon)
- **Login & registration**
  - Accounts stored in SQLite via the API
  - Username rules: 4–20 chars, single spaces only
  - Password rules: 8–50 chars, no spaces, must include a capital, number, and special char
- **Leaderboard**
  - Scores saved when logged in and you choose to submit
  - View leaderboard in-app
  - Open API JSON view in browser from the leaderboard screen
- **Deck generation via API**
  - Card order comes from the API
  - Images are still local files

## Quick Start

### 1. Requirements

- Java JDK **17+** (21 recommended)
- JavaFX SDK 21 (or update paths in `run-game.bat` / `.vscode/launch.json`)
- **EITHER** Maven installed on your system  
  **OR** the VS Code extension **“Maven for Java”** (from the *Extension Pack for Java*).

### 2. Start the API (Spring Boot)

From the `api` folder:

```bash
cd api
mvn spring-boot:run
```

If you don’t have Maven globally, you can:
- Install the **“Maven for Java”** extension in VS Code, open the `api` folder as a project, and run `MemoryGameApiApplication` from the Maven/Run view.

The API will run on `http://localhost:8080` and use `api/memory_game.db`.

### 3. Run the Desktop Game

**Option A – Windows batch file**
```bash
run-game.bat
```

**Option B – From IDE (VS Code / IntelliJ / VS 2022)**
1. Open the project root in your IDE.
2. Make sure JavaFX SDK path is set correctly (or use Maven JavaFX plugin).
3. Run `Main.java` (or use the provided VS Code launch config).

## How to Play

1. Start the API (`api` folder) and then start the desktop app.
2. On the main menu:
   - Login/register (top-right) if you want scores saved.
   - Note: if you don’t log in, scores **won’t** be saved.
3. Click **“Start Game”** and choose a difficulty.
4. Match all pairs to win.
5. When you win:
   - If logged in: you’re asked if you want to submit your score to the leaderboard.
   - If not logged in: you’re reminded that logging in lets you save scores.
6. View the leaderboard:
   - In-app (Leaderboard button).
   - Or click the hyperlink to open the web/API view in your browser.

## Project Structure

```text
PRO150OSHA/
├── api/                     # Spring Boot REST API (auth, leaderboard, deck)
│   ├── pom.xml
│   └── src/main/java/com/memorygame/api/...
├── src/main/java/           # JavaFX desktop app
│   ├── Main.java
│   ├── GameController.java
│   ├── LoginController.java
│   ├── LeaderboardController.java
│   ├── GameBoard.java
│   └── ApiClientService.java
├── src/main/resources/      # FXML views & CSS
│   └── views/*.fxml
├── images/                  # Local card images (Mario / Sonic / Pokemon)
├── lib/sqlite-jdbc.jar      # SQLite driver for desktop app
├── run-game.bat             # Helper script to compile & run game (Windows)
└── api/memory_game.db       # SQLite database used by the API (auto-created)
```

## Tech Stack

- **Desktop UI**: JavaFX + FXML
- **Backend API**: Spring Boot (REST), Spring Data JPA
- **Database**: SQLite
- **Build/Deps**: Maven (or VS Code “Maven for Java” extension)

For deeper setup details and API description, see `API_SETUP.md` in the repo.
