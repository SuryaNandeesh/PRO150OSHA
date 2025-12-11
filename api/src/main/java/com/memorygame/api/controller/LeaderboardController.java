package com.memorygame.api.controller;

import com.memorygame.api.dto.ScoreRequest;
import com.memorygame.api.dto.ScoreResponse;
import com.memorygame.api.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
@CrossOrigin(origins = "*")
public class LeaderboardController {
    @Autowired
    private LeaderboardService leaderboardService;

    @GetMapping
    public ResponseEntity<List<ScoreResponse>> getLeaderboard(
            @RequestParam(required = false) Integer limit) {
        List<ScoreResponse> scores = leaderboardService.getLeaderboard(limit);
        return ResponseEntity.ok(scores);
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitScore(@RequestBody ScoreRequest request) {
        if (request.getUsername() == null || request.getScore() == null ||
            request.getMoves() == null || request.getTimeSeconds() == null ||
            request.getDifficulty() == null) {
            return ResponseEntity.badRequest().body("All fields are required");
        }
        
        try {
            leaderboardService.submitScore(request);
            return ResponseEntity.ok("Score submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error submitting score: " + e.getMessage());
        }
    }
}

