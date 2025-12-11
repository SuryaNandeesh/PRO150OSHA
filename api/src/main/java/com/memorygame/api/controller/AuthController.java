package com.memorygame.api.controller;

import com.memorygame.api.dto.AuthResponse;
import com.memorygame.api.dto.LoginRequest;
import com.memorygame.api.dto.RegisterRequest;
import com.memorygame.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        if (request.getUsername() == null || request.getPassword() == null ||
            request.getUsername().trim().isEmpty() || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Username and password are required"));
        }
        
        boolean success = authService.login(request.getUsername().trim(), request.getPassword());
        
        if (success) {
            return ResponseEntity.ok(new AuthResponse(true, "Login successful", request.getUsername().trim()));
        } else {
            return ResponseEntity.ok(new AuthResponse(false, "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (request.getUsername() == null || request.getPassword() == null ||
            request.getUsername().trim().isEmpty() || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Username and password are required"));
        }

        String username = request.getUsername().trim();
        String password = request.getPassword();

        // Basic username validation: length 4-20, no leading/trailing spaces, no double spaces
        if (username.length() < 4 || username.length() > 20) {
            return ResponseEntity.ok(new AuthResponse(false, "Username must be between 4 and 20 characters."));
        }
        if (username.startsWith(" ") || username.endsWith(" ") || username.contains("  ")) {
            return ResponseEntity.ok(new AuthResponse(false, "Username can only have single spaces between words."));
        }

        // Password rules:
        // - At least 8 characters, max 50
        // - No spaces
        // - At least one capital letter, one number, and one special character
        if (password.length() < 8 || password.length() > 50) {
            return ResponseEntity.ok(new AuthResponse(false, "Password must be between 8 and 50 characters."));
        }
        if (password.contains(" ")) {
            return ResponseEntity.ok(new AuthResponse(false, "Password cannot contain spaces."));
        }

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        if (!hasUpper || !hasDigit || !hasSpecial) {
            return ResponseEntity.ok(new AuthResponse(false,
                    "Password must have at least one capital letter, one number, and one special character."));
        }

        boolean success = authService.register(username, password);

        if (success) {
            return ResponseEntity.ok(new AuthResponse(true, "Registration successful", username));
        } else {
            return ResponseEntity.ok(new AuthResponse(false, "Username already exists"));
        }
    }
}

