# Memory Game API Setup Guide

## Overview

The Memory Game now uses a Spring Boot REST API instead of direct database access. The desktop app communicates with the API running on `localhost:8080`.

## Project Structure

```
PRO150OSHA/
├── api/                          # Spring Boot API project
│   ├── pom.xml                   # Maven dependencies
│   └── src/main/java/com/memorygame/api/
│       ├── MemoryGameApiApplication.java
│       ├── config/               # CORS configuration
│       ├── controller/           # REST controllers
│       ├── service/              # Business logic
│       ├── repository/           # Data access
│       ├── model/                # Entity models
│       └── dto/                  # Data transfer objects
└── src/main/java/                # Desktop app
    └── ApiClientService.java     # HTTP client for API
```

## API Endpoints

### Authentication
- `POST /auth/login` - Login user
- `POST /auth/register` - Register new user

### Leaderboard
- `GET /leaderboard?limit=50` - Get top scores
- `POST /leaderboard/submit` - Submit a score

### Game
- `GET /game/deck?difficulty=easy|medium|hard` - Get randomized deck

## Setup Instructions

### Step 1: Install Maven (if not already installed)

Download from: https://maven.apache.org/download.cgi

Or use your IDE's built-in Maven support.

### Step 2: Build the API

Open terminal in the `api` directory:

```bash
cd api
mvn clean install
```

This will:
- Download all dependencies
- Compile the API
- Create a JAR file

### Step 3: Run the API

**Option A: Using Maven**
```bash
cd api
mvn spring-boot:run
```

**Option B: Using the JAR**
```bash
cd api
java -jar target/memory-game-api-1.0.0.jar
```

**Option C: From IDE**
- Open the `api` folder as a separate project
- Run `MemoryGameApiApplication.java`

The API will start on `http://localhost:8080`

### Step 4: Run the Desktop App

1. Make sure the API is running (Step 3)
2. Run the desktop app as usual
3. The app will automatically connect to the API

## Database

The API uses the same SQLite database (`memory_game.db`) in the project root. The database schema is automatically created/updated by Spring Boot on startup.

## API Configuration

Edit `api/src/main/resources/application.properties` to change:
- Port (default: 8080)
- Database location
- Logging levels

## Troubleshooting

### API won't start
- Check if port 8080 is already in use
- Verify Maven dependencies downloaded correctly
- Check Java version (requires Java 17+)

### Desktop app can't connect
- Verify API is running: Open `http://localhost:8080/leaderboard?limit=1` in browser
- Check firewall settings
- Verify API_BASE_URL in `ApiClientService.java` matches API port

### Database errors
- Make sure `memory_game.db` is in the project root
- The API will create the database if it doesn't exist
- Check file permissions

## Development Workflow

1. **Start API first**: `cd api && mvn spring-boot:run`
2. **Then start desktop app**: Run `Main.java` from IDE
3. **API logs**: Check console output for API requests
4. **Desktop logs**: Check console for API connection status

## Testing the API

You can test endpoints using curl or Postman:

```bash
# Test login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test"}'

# Test deck generation
curl http://localhost:8080/game/deck?difficulty=easy

# Get leaderboard
curl http://localhost:8080/leaderboard?limit=10
```

## What Changed

### Desktop App Changes
- `DatabaseService` → `ApiClientService` (HTTP client)
- `LoginController` now uses API for login/register
- `GameController` submits scores via API
- `LeaderboardController` fetches scores via API
- `GameBoard` gets deck order from API

### New Files
- `ApiClientService.java` - HTTP client for desktop app
- `api/` directory - Complete Spring Boot API project

### Removed/Deprecated
- Direct database access from desktop app (still works as fallback)
- `DatabaseService` can be kept for reference but is no longer used

## Next Steps

The API is now the single source of truth for:
- User authentication
- Score storage and retrieval
- Deck generation

All database operations go through the API, making it easier to:
- Add features (like multiplayer, cloud sync)
- Change database backend
- Add authentication tokens
- Scale the application

