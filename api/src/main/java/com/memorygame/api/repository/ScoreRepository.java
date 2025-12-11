package com.memorygame.api.repository;

import com.memorygame.api.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    @Query("SELECT s FROM Score s ORDER BY s.score DESC, s.timeSeconds ASC")
    List<Score> findAllOrderByScoreDesc();
    
    List<Score> findByDifficultyOrderByScoreDescTimeSecondsAsc(String difficulty);
}

