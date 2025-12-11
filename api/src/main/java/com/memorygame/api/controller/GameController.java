package com.memorygame.api.controller;

import com.memorygame.api.dto.DeckResponse;
import com.memorygame.api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/deck")
    public ResponseEntity<DeckResponse> getDeck(@RequestParam String difficulty) {
        if (difficulty == null || difficulty.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        DeckResponse deck = gameService.generateDeck(difficulty.trim());
        return ResponseEntity.ok(deck);
    }
}

