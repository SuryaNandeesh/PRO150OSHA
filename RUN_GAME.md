# How to Run the Memory Game

## Option 1: Using VS Code (Recommended)

1. **Make sure JavaFX SDK is installed** at: `C:\Program Files\Java\javafx-sdk-21.0.9`
2. **Open the project in VS Code**
3. **Open `src/main/java/Main.java`**
4. **Click the "Run" button** above `public static void main`
5. **OR** press `F5` and select "Launch Memory Game"

The launch configuration is already set up with JavaFX modules.

## Option 2: Using Maven (If Maven is installed)

Open terminal in VS Code (`Ctrl+``) and run:

```powershell
# First time: Download dependencies
mvn clean compile

# Run the game
mvn javafx:run
```

## Option 3: Manual Command Line

If VS Code doesn't work, run from terminal:

```powershell
# Compile
javac --module-path "C:\Program Files\Java\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml -d target/classes src/main/java/*.java

# Run
java --module-path "C:\Program Files\Java\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes;src/main/resources" Main
```

## Troubleshooting

### "JavaFX runtime components are missing"

**Solution:** Make sure JavaFX SDK is installed at:
```
C:\Program Files\Java\javafx-sdk-21.0.9
```

If it's in a different location, update `.vscode/launch.json`:
- Change the path in `vmArgs` to match your JavaFX installation

**To download JavaFX SDK:**
1. Go to: https://openjfx.io/
2. Download JavaFX 21 SDK for Windows
3. Extract to `C:\Program Files\Java\javafx-sdk-21.0.9`

### "Cannot find Main class"

**Solution:** Make sure the project is compiled:
- VS Code should auto-compile
- Or run: `mvn clean compile`
- Or manually compile with javac

### VS Code still stuck on "Importing Projects"

**Solution:** 
1. Press `Ctrl+Shift+P`
2. Type: `Java: Clean Java Language Server Workspace`
3. Restart VS Code

## Quick Test

To verify everything works, run this in terminal:

```powershell
java --module-path "C:\Program Files\Java\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes;src/main/resources" Main
```

If this works, the game should launch!

