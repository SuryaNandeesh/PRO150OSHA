import java.util.List;

/**
 * A class that handles HTTP communication with the backend API.
 * 
 * Responsibilities:
 * - Sends GET request → retrieve leaderboard
 * - Sends POST request → upload score
 * - Keeps your UI code clean by abstracting API calls
 * 
 * NOTE: This is a skeleton implementation. The actual HTTP client code
 * should be implemented when the backend API is ready.
 */
public class HttpClientService {
    private static HttpClientService instance;
    private static final String API_BASE_URL = "https://your-api-url.com/api"; // TODO: Update with actual API URL
    
    /**
     * Private constructor for singleton pattern
     */
    private HttpClientService() {
        // Initialize HTTP client here when implementing
    }
    
    /**
     * Gets the singleton instance of HttpClientService
     * @return The HttpClientService instance
     */
    public static HttpClientService getInstance() {
        if (instance == null) {
            instance = new HttpClientService();
        }
        return instance;
    }
    
    /**
     * Retrieves the leaderboard from the API
     * @return List of Score objects representing the leaderboard
     */
    public List<Score> getLeaderboard() {
        // TODO: Implement HTTP GET request to retrieve leaderboard
        // Example:
        // String url = API_BASE_URL + "/leaderboard";
        // Make GET request and parse JSON response into List<Score>
        
        // For now, return empty list
        return new java.util.ArrayList<>();
    }
    
    /**
     * Submits a score to the API
     * @param score The Score object to submit
     * @return true if submission was successful, false otherwise
     */
    public boolean submitScore(Score score) {
        // TODO: Implement HTTP POST request to submit score
        // Example:
        // String url = API_BASE_URL + "/scores";
        // Convert Score to JSON and send POST request
        // Return true on success (status 200/201), false on failure
        
        // For now, just return true (simulate success)
        System.out.println("Score submission (not yet implemented): " + score.getPlayerName() + " - " + score.getScore());
        return true;
    }
}
