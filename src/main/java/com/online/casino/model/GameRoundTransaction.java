package com.online.casino.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class GameRoundTransaction {

    @Id
    @GeneratedValue
    private UUID transactionId;
    private UUID playerId;
    private String transactionType;
    private Double transactionAmount;
    private LocalDateTime createDate;

    public GameRoundTransaction() {
    }

    public GameRoundTransaction(UUID playerId, String transactionType, Double transactionAmount) {
        createDate = LocalDateTime.now();
        this.playerId = playerId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
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

    public LocalDateTime getCreateDate() { return createDate; }
}
