# Quick Start Guide

## Prerequisites Check
- [ ] Java JDK 11+ installed (`java -version`)
- [ ] Node.js installed (`node -v`)
- [ ] npm installed (`npm -v`)

## Step-by-Step Setup

### 1. Install API Dependencies (One-time setup)
```bash
npm install
```

### 2. Start the API Server
**Windows:**
```bash
start-api.bat
```

**Mac/Linux:**
```bash
./start-api.sh
```

**Or manually:**
```bash
npm start
```

You should see:
```
Memory Game API server running on http://localhost:3000
Connected to SQLite database
Database table initialized
```

**Keep this terminal open!** The server must be running.

### 3. Run the Java Application

#### Option A: Using Maven (Easiest)
```bash
mvn javafx:run
```

#### Option B: Using Visual Studio Code
1. Install "Extension Pack for Java"
2. Open the project folder
3. Run `Main.java` (F5 or click Run)

#### Option C: Using IntelliJ IDEA
1. Open project
2. Wait for Maven to sync dependencies
3. Run `Main.java`

#### Option D: Using Visual Studio 2022
1. Open project folder
2. Configure JavaFX SDK path if needed
3. Set `Main.java` as startup class
4. Run

## Verify Everything Works

1. **API Health Check**: Open browser to http://localhost:3000/api/health
   - Should return: `{"status":"ok","message":"Memory Game API is running"}`

2. **Run the Game**: Start the Java application
   - Main menu should appear
   - Click "Start Game"
   - Choose a difficulty
   - Cards should display with images

3. **Test Score Submission**:
   - Complete a game
   - Enter your name
   - Check console for "Score submitted successfully"

4. **Test Leaderboard**:
   - Click "Leaderboard" from main menu
   - Should show your saved score

## Troubleshooting

### Images Not Showing
- Check that `images/` folder exists in project root
- Verify folder names match exactly:
  - `Mario (6x6 Easy)`
  - `Sonic (8x8 Medium)`
  - `Pokemon (10x10 Hard)`

### API Connection Failed
- Is the server running? Check terminal
- Try: http://localhost:3000/api/health in browser
- Check firewall settings

### JavaFX Not Found
- Download from https://openjfx.io
- Add to IDE module path
- Or use Maven (handles automatically)

### Gson Not Found
- If using Maven: `mvn clean install`
- If manual: Download Gson JAR and add to classpath

## Common Issues

**"API server is not running" message**
→ Start the server with `npm start` or `start-api.bat`

**"Image not found" in console**
→ Check image folder paths and names

**Game won't start**
→ Check JavaFX is properly configured
→ Verify Java version is 11+

**Maven build fails**
→ Check internet connection (needs to download dependencies)
→ Try: `mvn clean install -U`

## Next Steps

- Play the game and try all difficulty levels!
- Submit scores and check the leaderboard
- Customize images in the `images/` folders
- Modify game logic in `Game.java`

For detailed information, see [SETUP.md](SETUP.md)

