package com.memorygame.api.dto;

import java.util.List;

public class DeckResponse {
    private List<Integer> cardIds;
    private String difficulty;
    private Integer totalCards;

    public DeckResponse() {}

    public DeckResponse(List<Integer> cardIds, String difficulty, Integer totalCards) {
        this.cardIds = cardIds;
        this.difficulty = difficulty;
        this.totalCards = totalCards;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Integer> cardIds) {
        this.cardIds = cardIds;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(Integer totalCards) {
        this.totalCards = totalCards;
    }
}

