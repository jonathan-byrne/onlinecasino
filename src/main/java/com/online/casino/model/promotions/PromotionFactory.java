package com.online.casino.model.promotions;

import com.online.casino.model.GameEventTransaction;
import com.online.casino.model.promotions.Promotion;

public class PromotionFactory {

    public Promotion getPromotion(String promotionCode) {
        Promotion promotion;

        if(promotionCode.equalsIgnoreCase("paper")) {
            promotion = new FreeWagersPromotion();
        } else {
            //return an empty Promotion object, calling process won't do anything,
            //we also avoid potential null pointer exceptions down the line
            promotion = new Promotion() {
                @Override
                public void process(GameEventTransaction eventTransaction) {}
            };
        }

        return promotion;
    }
}
