package com.online.casino.service;

import com.online.casino.model.Player;
import com.online.casino.model.promotion.DummyPromotion;
import com.online.casino.model.promotion.FreeWagersPromotion;
import com.online.casino.model.promotion.Promotion;
import com.online.casino.util.PromotionCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionFactoryService {

    private FreeWagersPromotionService freeWagersPromotionService;

    @Autowired
    public PromotionFactoryService(FreeWagersPromotionService freeWagersPromotionService) {
        this.freeWagersPromotionService = freeWagersPromotionService;
    }

    public Promotion getPromotion(Player player, String promotionCode) {
        Promotion promotion;

        //checking the promotion code like this will protect us against null pointer exceptions
        if(PromotionCodes.FREE_WAGERS_PROMOTION_CODE.equalsIgnoreCase(promotionCode)) {
            promotion = freeWagersPromotionService.findByPlayerAndPromotionCode(player, promotionCode);

            //if a dummy promotion is returned then create a new promotion, we are avoiding branching on nulls to check whether the promotion
            //exists or not
            if(DummyPromotion.PROMOTION_CODE.equalsIgnoreCase(promotion.getPromotionCode())){
                promotion = freeWagersPromotionService.createFreeWagersPromotion(player, promotionCode, FreeWagersPromotion.FREE_WAGER_PROMOTION_START_AMOUNT);
            }
        } else {
            promotion = new DummyPromotion();
        }

        return promotion;
    }
}
