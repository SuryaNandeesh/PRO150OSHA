package com.memorygame.api.dto;

import java.time.LocalDateTime;

public class ScoreResponse {
    private String username;
    private Integer score;
    private Integer moves;
    private Long timeSeconds;
    private String difficulty;
    private LocalDateTime createdAt;

    public ScoreResponse() {}

    public ScoreResponse(String username, Integer score, Integer moves, Long timeSeconds, String difficulty, LocalDateTime createdAt) {
        this.username = username;
        this.score = score;
        this.moves = moves;
        this.timeSeconds = timeSeconds;
        this.difficulty = difficulty;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getMoves() {
        return moves;
    }

    public void setMoves(Integer moves) {
        this.moves = moves;
    }

    public Long getTimeSeconds() {
        return timeSeconds;
    }

    public void setTimeSeconds(Long timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

