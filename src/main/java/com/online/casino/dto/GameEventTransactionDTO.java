package com.online.casino.dto;

import com.online.casino.model.GameEventTransaction;

import java.time.LocalDateTime;
import java.util.UUID;

public class GameEventTransactionDTO {

    private UUID transactionId;
    private UUID playerId;
    private String transactionType;
    private Double transactionAmount;
    private LocalDateTime createDate;

    public GameEventTransactionDTO(UUID transactionId, UUID playerId, String transactionType, Double transactionAmount, LocalDateTime createDate){
        this.transactionId = transactionId;
        this.playerId = playerId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.createDate = createDate;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public static GameEventTransactionDTO toGameRoundTransactionDTO(GameEventTransaction gameEventTransaction) {
        return new GameEventTransactionDTO(gameEventTransaction.getTransactionId(), gameEventTransaction.getPlayerId(),
                gameEventTransaction.getTransactionType(), gameEventTransaction.getTransactionAmount(), gameEventTransaction.getCreateDate());
    }
}
