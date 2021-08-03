package com.online.casino.model.promotions;

import com.online.casino.model.Player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class FreeWagersPromotion implements Promotion {
    @Id
    @GeneratedValue
    private UUID promotionId;
    private UUID playerId;
    private String promotionCode;
    private int numberOfFreeWagersRemaining;
    public static Integer FREE_WAGER_PROMOTION_START_AMOUNT = 5;

    public FreeWagersPromotion() {}

    public FreeWagersPromotion(Player player, String promotionCode, int numberOfFreeWagersRemaining) {
        this.playerId = player.getPlayerId();
        this.promotionCode = promotionCode;
        this.numberOfFreeWagersRemaining = numberOfFreeWagersRemaining;
    }

    @Override
    public Double processGameEvent(Player player, Double wagerAmount) {

        if (numberOfFreeWagersRemaining > 0) {
            numberOfFreeWagersRemaining --;
            wagerAmount = 0.0;
        }

        //TODO update promotion db entry

        return wagerAmount;
    }

    public UUID getPromotionId() {
        return promotionId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public int getNumberOfFreeWagersRemaining() {
        return numberOfFreeWagersRemaining;
    }
}
