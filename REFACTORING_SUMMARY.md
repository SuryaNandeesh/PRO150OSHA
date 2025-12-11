# API Refactoring Summary

## What Was Done

Your Memory Game desktop application has been successfully refactored to use a Spring Boot REST API instead of direct database access. All database operations now go through the API.

## Architecture Changes

### Before
```
Desktop App → DatabaseService → SQLite Database
```

### After
```
Desktop App → ApiClientService → Spring Boot API → SQLite Database
```

## Files Created

### API Project (`api/` directory)
- **Spring Boot Application**: Complete REST API with Spring Boot 3.2.0
- **Models**: `User.java`, `Score.java` (JPA entities)
- **DTOs**: Request/Response objects for API communication
- **Repositories**: `UserRepository`, `ScoreRepository` (Spring Data JPA)
- **Services**: `AuthService`, `LeaderboardService`, `GameService`
- **Controllers**: `AuthController`, `LeaderboardController`, `GameController`
- **Configuration**: CORS enabled for desktop app access

### Desktop App Changes
- **ApiClientService.java**: New HTTP client replacing `DatabaseService`
- **Updated Controllers**: 
  - `LoginController` - Uses API for login/register
  - `GameController` - Submits scores via API
  - `LeaderboardController` - Fetches scores via API
  - `GameBoard` - Gets deck order from API

## API Endpoints

### Authentication
- `POST /auth/login` - Login with username/password
- `POST /auth/register` - Register new user

### Leaderboard
- `GET /leaderboard?limit=50` - Get top scores
- `POST /leaderboard/submit` - Submit a score

### Game
- `GET /game/deck?difficulty=easy|medium|hard` - Get randomized deck

## How to Use

### 1. Start the API
```bash
cd api
mvn spring-boot:run
```
Or use `api/start-api.bat` (Windows) or `api/start-api.sh` (Linux/Mac)

The API runs on `http://localhost:8080`

### 2. Start the Desktop App
Run `Main.java` as usual. The app will automatically connect to the API.

## Database Compatibility

The API uses the **same SQLite database** (`memory_game.db`) with the **same schema**:
- `users` table: id, username, password
- `scores` table: id, user_id, username, score, moves, time_seconds, difficulty, created_at

Spring Boot will automatically create/update the schema on startup.

## Key Features

### ✅ Authentication via API
- Login and register go through `/auth/login` and `/auth/register`
- Returns success/failure with messages

### ✅ Leaderboard via API
- Scores are submitted to `/leaderboard/submit`
- Leaderboard fetched from `/leaderboard`
- Same data structure, just through API

### ✅ Deck Generation via API
- Game board gets card order from `/game/deck`
- API returns shuffled list of card IDs (0 to pairs-1)
- Desktop app maps IDs to local image files
- Falls back to local shuffling if API unavailable

### ✅ Graceful Fallback
- Desktop app checks API health before making calls
- Shows user-friendly messages if API is down
- Game still works (just can't save scores or get API deck)

## Code Changes Summary

### Replaced Direct DB Calls

**LoginController.java**
- Before: `db.loginUser(username, password)`
- After: `apiClient.login(username, password)`

**GameController.java**
- Before: `DatabaseService.getInstance().saveScore(...)`
- After: `apiClient.submitScore(...)`

**LeaderboardController.java**
- Before: `DatabaseService.getInstance().getLeaderboard(50)`
- After: `apiClient.getLeaderboard(50)`

**GameBoard.java**
- Before: Local shuffling with `Collections.shuffle()`
- After: Gets deck from API, maps card IDs to images

## Testing

### Test API Endpoints

```bash
# Test login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test"}'

# Test deck
curl http://localhost:8080/game/deck?difficulty=easy

# Get leaderboard
curl http://localhost:8080/leaderboard?limit=10
```

### Test Desktop App
1. Start API
2. Run desktop app
3. Try login/register
4. Play a game
5. Check leaderboard
6. Verify scores are saved

## Benefits

1. **Separation of Concerns**: Database logic in API, UI logic in desktop app
2. **Scalability**: Easy to add features (multiplayer, cloud sync, etc.)
3. **Security**: Can add authentication tokens, rate limiting, etc.
4. **Flexibility**: Can change database backend without touching desktop app
5. **Testing**: API can be tested independently
6. **Future-Proof**: Easy to add web client, mobile app, etc.

## Next Steps (Optional Enhancements)

1. **Add JWT Authentication**: Token-based auth instead of plain passwords
2. **Add API Rate Limiting**: Prevent abuse
3. **Add Game Rules Endpoint**: `/game/rules` for configurable game settings
4. **Add User Profiles**: More user information
5. **Add Statistics**: Track user stats over time
6. **Add Multiplayer**: Real-time multiplayer support

## Troubleshooting

### API won't start
- Check Java version (needs Java 17+)
- Check if port 8080 is in use
- Verify Maven dependencies downloaded

### Desktop app can't connect
- Verify API is running: `http://localhost:8080/leaderboard?limit=1`
- Check `ApiClientService.API_BASE_URL` matches API port
- Check firewall settings

### Database errors
- API creates database automatically
- Check file permissions on `memory_game.db`
- Verify database location in `application.properties`

## Files to Keep/Remove

### Keep
- `DatabaseService.java` - Can keep for reference, but not used
- All existing game logic and UI files

### New Files (Keep)
- `api/` directory - Complete Spring Boot API
- `ApiClientService.java` - HTTP client for desktop app
- `API_SETUP.md` - Setup instructions

The refactoring is complete and ready to use! 🎉

