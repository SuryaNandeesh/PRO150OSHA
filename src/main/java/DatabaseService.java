import java.sql.*;

/**
 * Simple database service for user accounts and scores.
 * Uses SQLite database.
 */
public class DatabaseService {
    private static DatabaseService instance;
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:memory_game.db";
    
    private DatabaseService() {
        initializeDatabase();
    }
    
    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }
    
    private void initializeDatabase() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
    
    private void createTables() throws SQLException {
        // Users table
        String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                           "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                           "username TEXT UNIQUE NOT NULL, " +
                           "password TEXT NOT NULL)";
        
        // Scores table
        String scoresTable = "CREATE TABLE IF NOT EXISTS scores (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "user_id INTEGER NOT NULL, " +
                            "username TEXT NOT NULL, " +
                            "score INTEGER NOT NULL, " +
                            "moves INTEGER NOT NULL, " +
                            "time_seconds INTEGER NOT NULL, " +
                            "difficulty TEXT NOT NULL, " +
                            "created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                            "FOREIGN KEY (user_id) REFERENCES users(id))";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(scoresTable);
        }
    }
    
    public boolean registerUser(String username, String password) {
        try {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean loginUser(String username, String password) {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }
    
    public void saveScore(String username, int score, int moves, long timeSeconds, String difficulty) {
        try {
            // Get user_id
            int userId = getUserId(username);
            if (userId == -1) return;
            
            String sql = "INSERT INTO scores (user_id, username, score, moves, time_seconds, difficulty) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, username);
                pstmt.setInt(3, score);
                pstmt.setInt(4, moves);
                pstmt.setLong(5, timeSeconds);
                pstmt.setString(6, difficulty);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error saving score: " + e.getMessage());
        }
    }
    
    public java.util.List<Score> getLeaderboard(int limit) {
        java.util.List<Score> scores = new java.util.ArrayList<>();
        try {
            String sql = "SELECT username, score, moves, time_seconds, difficulty " +
                        "FROM scores ORDER BY score DESC, time_seconds ASC LIMIT ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, limit);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    scores.add(new Score(
                        rs.getString("username"),
                        rs.getInt("score"),
                        rs.getInt("moves"),
                        rs.getLong("time_seconds")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading leaderboard: " + e.getMessage());
        }
        return scores;
    }
    
    private int getUserId(String username) {
        try {
            String sql = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user ID: " + e.getMessage());
        }
        return -1;
    }
    
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
}

