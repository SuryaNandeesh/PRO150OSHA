package com.memorygame.api.service;

import com.memorygame.api.dto.ScoreRequest;
import com.memorygame.api.dto.ScoreResponse;
import com.memorygame.api.model.Score;
import com.memorygame.api.model.User;
import com.memorygame.api.repository.ScoreRepository;
import com.memorygame.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {
    @Autowired
    private ScoreRepository scoreRepository;
    
    @Autowired
    private UserRepository userRepository;

    public void submitScore(ScoreRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Score score = new Score(
            user,
            request.getUsername(),
            request.getScore(),
            request.getMoves(),
            request.getTimeSeconds(),
            request.getDifficulty()
        );
        
        scoreRepository.save(score);
    }

    public List<ScoreResponse> getLeaderboard(Integer limit) {
        List<Score> scores = scoreRepository.findAllOrderByScoreDesc();
        
        if (limit != null && limit > 0) {
            scores = scores.stream().limit(limit).collect(Collectors.toList());
        }
        
        return scores.stream()
                .map(score -> new ScoreResponse(
                    score.getUsername(),
                    score.getScore(),
                    score.getMoves(),
                    score.getTimeSeconds(),
                    score.getDifficulty(),
                    score.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}

