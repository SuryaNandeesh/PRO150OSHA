package com.memorygame.api.service;

import com.memorygame.api.dto.DeckResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {
    
    // Card counts per difficulty (based on image folders)
    private static final int EASY_CARDS = 18;   // 6x6 = 36 cards = 18 pairs
    private static final int MEDIUM_CARDS = 32; // 8x8 = 64 cards = 32 pairs
    private static final int HARD_CARDS = 50;   // 10x10 = 100 cards = 50 pairs

    public DeckResponse generateDeck(String difficulty) {
        int pairs;
        int totalCards;
        
        switch (difficulty.toLowerCase()) {
            case "easy":
                pairs = EASY_CARDS;
                totalCards = pairs * 2;
                break;
            case "medium":
                pairs = MEDIUM_CARDS;
                totalCards = pairs * 2;
                break;
            case "hard":
                pairs = HARD_CARDS;
                totalCards = pairs * 2;
                break;
            default:
                pairs = EASY_CARDS;
                totalCards = pairs * 2;
        }
        
        // Generate card IDs: [0, 0, 1, 1, 2, 2, ...] then shuffle
        List<Integer> cardIds = new ArrayList<>();
        for (int i = 0; i < pairs; i++) {
            cardIds.add(i);
            cardIds.add(i);
        }
        
        Collections.shuffle(cardIds);
        
        return new DeckResponse(cardIds, difficulty, totalCards);
    }
}

