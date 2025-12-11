@echo off
echo Compiling Memory Game...
javac --module-path "C:\Program Files\Java\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml -d target/classes -cp "lib\sqlite-jdbc.jar" src/main/java/*.java src/main/java/models/*.java 2>nul

echo.
echo Running Memory Game...
java --module-path "C:\Program Files\Java\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes;src/main/resources;lib\sqlite-jdbc.jar" Main

pause

