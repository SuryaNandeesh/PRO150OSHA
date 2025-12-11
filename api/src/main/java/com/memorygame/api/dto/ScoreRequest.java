package com.memorygame.api.dto;

public class ScoreRequest {
    private String username;
    private Integer score;
    private Integer moves;
    private Long timeSeconds;
    private String difficulty;

    public ScoreRequest() {}

    public ScoreRequest(String username, Integer score, Integer moves, Long timeSeconds, String difficulty) {
        this.username = username;
        this.score = score;
        this.moves = moves;
        this.timeSeconds = timeSeconds;
        this.difficulty = difficulty;
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
}

