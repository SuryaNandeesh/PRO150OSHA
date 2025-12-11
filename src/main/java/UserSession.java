/**
 * Simple user session manager.
 * Tracks the currently logged-in user.
 */
public class UserSession {
    private static UserSession instance;
    private String currentUsername;
    
    private UserSession() {
        currentUsername = null;
    }
    
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    public void login(String username) {
        this.currentUsername = username;
    }
    
    public void logout() {
        this.currentUsername = null;
    }
    
    public boolean isLoggedIn() {
        return currentUsername != null;
    }
    
    public String getUsername() {
        return currentUsername;
    }
}

