package com.online.casino.model.promotion;

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
    private Integer numberOfFreeWagersRemaining;
    public static Integer FREE_WAGER_PROMOTION_START_AMOUNT = 5;

    /**
     * The FreeWagersPromotion will allow for a certain amount of free
     * wager amounts
     *
     * It is a specific promotion type with it's own database table, this way it's
     * a stand alone module each promotion is able to reference it's current state
     * via the database entry
     *
     * Other promotions will have their own table to manage their state
     *
     * The FreeWagersPromotion tracks the amount of free wagers remaining in
     * the numberOfFreeWagersRemaining field
     */
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

    public Integer getNumberOfFreeWagersRemaining() {
        return numberOfFreeWagersRemaining;
    }
}
