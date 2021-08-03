package com.online.casino.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class GameEventTransaction {

    public  static final String WAGER_TRANSACTION_TYPE = "wager";
    public static final String WINNINGS_TRANSACTION_TYPE = "winnings";

    @Id
    @GeneratedValue
    private UUID transactionId;
    private UUID playerId;
    private String transactionType;
    private Double transactionAmount;
    private LocalDateTime createDate;

    public GameEventTransaction() {
    }

    public GameEventTransaction(UUID playerId, String transactionType, Double transactionAmount) {
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

    public Boolean isDeductWager() {
        return transactionType == WAGER_TRANSACTION_TYPE;
    }

    public Boolean isAddWinnings() {
        return transactionType == WINNINGS_TRANSACTION_TYPE;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public LocalDateTime getCreateDate() { return createDate; }
}
