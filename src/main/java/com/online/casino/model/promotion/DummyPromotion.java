package com.online.casino.model.promotion;

import com.online.casino.model.Player;

/**
 * The DummyPromotion will only return the original amount in its processGameEvent method.
 * It won't alter the amount coming in and it will prevent null pointer exceptions if used where
 * an implemented Promotion object can't be created
 */
public class DummyPromotion implements Promotion{

    public static final String PROMOTION_CODE = "dummy";

    @Override
    public Double processGameEvent(Player player, Double amount) {
        return amount;
    }

    @Override
    public String getPromotionCode() {
        return PROMOTION_CODE;
    }
}
