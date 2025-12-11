import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * HTTP client service for communicating with the Memory Game API.
 * Replaces direct database access with API calls.
 */
public class ApiClientService {
    private static final String API_BASE_URL = "http://localhost:8080";
    private static ApiClientService instance;
    private final Gson gson;
    
    private ApiClientService() {
        this.gson = new Gson();
    }
    
    public static synchronized ApiClientService getInstance() {
        if (instance == null) {
            instance = new ApiClientService();
        }
        return instance;
    }
    
    /**
     * Login user via API
     */
    public AuthResult login(String username, String password) {
        try {
            URL url = new URL(API_BASE_URL + "/auth/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            
            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("password", password);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine);
                    }
                    
                    JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();
                    boolean success = responseJson.get("success").getAsBoolean();
                    String message = responseJson.get("message").getAsString();
                    String responseUsername = responseJson.has("username") ? 
                        responseJson.get("username").getAsString() : username;
                    
                    return new AuthResult(success, message, responseUsername);
                }
            }
            return new AuthResult(false, "Connection error: " + responseCode);
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return new AuthResult(false, "Error: " + e.getMessage());
        }
    }
    
    /**
     * Register user via API
     */
    public AuthResult register(String username, String password) {
        try {
            URL url = new URL(API_BASE_URL + "/auth/register");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            
            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("password", password);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine);
                    }
                    
                    JsonObject responseJson = JsonParser.parseString(response.toString()).getAsJsonObject();
                    boolean success = responseJson.get("success").getAsBoolean();
                    String message = responseJson.get("message").getAsString();
                    String responseUsername = responseJson.has("username") ? 
                        responseJson.get("username").getAsString() : username;
                    
                    return new AuthResult(success, message, responseUsername);
                }
            }
            return new AuthResult(false, "Connection error: " + responseCode);
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return new AuthResult(false, "Error: " + e.getMessage());
        }
    }
    
    /**
     * Submit score via API
     */
    public boolean submitScore(String username, int score, int moves, long timeSeconds, String difficulty) {
        try {
            URL url = new URL(API_BASE_URL + "/leaderboard/submit");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            
            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("score", score);
            json.addProperty("moves", moves);
            json.addProperty("timeSeconds", timeSeconds);
            json.addProperty("difficulty", difficulty);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error submitting score: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get leaderboard via API
     */
    public List<Score> getLeaderboard(int limit) {
        List<Score> scores = new ArrayList<>();
        try {
            String urlString = API_BASE_URL + "/leaderboard?limit=" + limit;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine);
                    }
                    
                    JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();
                    for (JsonElement element : jsonArray) {
                        JsonObject obj = element.getAsJsonObject();
                        Score score = new Score(
                            obj.get("username").getAsString(),
                            obj.get("score").getAsInt(),
                            obj.get("moves").getAsInt(),
                            obj.get("timeSeconds").getAsLong()
                        );
                        scores.add(score);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching leaderboard: " + e.getMessage());
        }
        return scores;
    }
    
    /**
     * Get deck from API
     */
    public DeckInfo getDeck(String difficulty) {
        try {
            String urlString = API_BASE_URL + "/game/deck?difficulty=" + difficulty;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine);
                    }
                    
                    JsonObject obj = JsonParser.parseString(response.toString()).getAsJsonObject();
                    JsonArray cardIdsArray = obj.get("cardIds").getAsJsonArray();
                    List<Integer> cardIds = new ArrayList<>();
                    for (JsonElement element : cardIdsArray) {
                        cardIds.add(element.getAsInt());
                    }
                    
                    return new DeckInfo(cardIds, obj.get("difficulty").getAsString());
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching deck: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Check if API is available
     */
    public boolean checkApiHealth() {
        try {
            URL url = new URL(API_BASE_URL + "/leaderboard?limit=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            
            int responseCode = conn.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Result class for authentication operations
     */
    public static class AuthResult {
        private final boolean success;
        private final String message;
        private final String username;
        
        public AuthResult(boolean success, String message) {
            this(success, message, null);
        }
        
        public AuthResult(boolean success, String message, String username) {
            this.success = success;
            this.message = message;
            this.username = username;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public String getUsername() {
            return username;
        }
    }
    
    /**
     * Deck information from API
     */
    public static class DeckInfo {
        private final List<Integer> cardIds;
        private final String difficulty;
        
        public DeckInfo(List<Integer> cardIds, String difficulty) {
            this.cardIds = cardIds;
            this.difficulty = difficulty;
        }
        
        public List<Integer> getCardIds() {
            return cardIds;
        }
        
        public String getDifficulty() {
            return difficulty;
        }
    }
}

