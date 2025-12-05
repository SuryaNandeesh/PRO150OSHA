
import java.lang.reflect.Method;
import java.util.Objects;

public class ScoreDatabase {
    private final String url;
    private final String user;
    private final String password;

    public ScoreDatabase(String url, String user, String password) {
        this.url = Objects.requireNonNull(url);
        this.user = user;
        this.password = password;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
            // driver may be provided by the runtime
        }
    }

    private java.sql.Connection getConnection() throws java.sql.SQLException {
        return java.sql.DriverManager.getConnection(url, user, password);
    }

    public void init() throws java.sql.SQLException {
        String ddl = "CREATE TABLE IF NOT EXISTS scores ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "player VARCHAR(255) NOT NULL, "
                + "points INT NOT NULL, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        try (java.sql.Connection c = getConnection();
             java.sql.Statement s = c.createStatement()) {
            s.executeUpdate(ddl);
        }
    }

    public void insertScore(Score score) throws java.sql.SQLException {
        String player = extractString(score, "getName", "getPlayer", "getUsername", "name");
        int points = extractInt(score, "getScore", "getPoints", "score", "points");

        String sql = "INSERT INTO scores (player, points) VALUES (?, ?)";
        try (java.sql.Connection c = getConnection();
             java.sql.PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, player);
            ps.setInt(2, points);
            ps.executeUpdate();
        }
    }

    private static String extractString(Object obj, String... methodNames) {
        if (obj == null) return "unknown";
        for (String mName : methodNames) {
            try {
                Method m = obj.getClass().getMethod(mName);
                Object val = m.invoke(obj);
                if (val != null) return val.toString();
            } catch (Exception ignored) { }
        }
        // fallback to toString
        return obj.toString();
    }

    private static int extractInt(Object obj, String... methodNames) {
        if (obj == null) return 0;
        for (String mName : methodNames) {
            try {
                Method m = obj.getClass().getMethod(mName);
                Object val = m.invoke(obj);
                if (val instanceof Number) return ((Number) val).intValue();
                if (val != null) return Integer.parseInt(val.toString());
            } catch (Exception ignored) { }
        }
        return 0;
    }
}

