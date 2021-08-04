package com.online.casino.service;

import com.online.casino.model.Player;
import com.online.casino.model.promotion.FreeWagersPromotion;
import com.online.casino.model.promotion.Promotion;
import com.online.casino.util.PromotionCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionManagerService {

    PromotionFactoryService promotionFactoryService;
    FreeWagersPromotionService freeWagersPromotionService;

    @Autowired
    public PromotionManagerService(PromotionFactoryService promotionFactoryService, FreeWagersPromotionService freeWagersPromotionService) {
        this.promotionFactoryService = promotionFactoryService;
        this.freeWagersPromotionService = freeWagersPromotionService;
    }

    public Double processPromotion(Player player, Double amount, String promotionCode) {
        Promotion promotion = promotionFactoryService.getPromotion(player, promotionCode);

        //if there isn't a promotion with this player and promotion code we will get a dummy promotion
        //we can process the game event without worrying that we could get a null pointer exception
        //the dummy promotion will run without altering the amount without throwing a null pointer exception
        Double processedAmount = promotion.processGameEvent(player, amount);

        //update promotion database entry
        if(PromotionCodes.FREE_WAGERS_PROMOTION_CODE.equalsIgnoreCase(promotion.getPromotionCode())) {
            freeWagersPromotionService.updateFreeWagersPromotion(((FreeWagersPromotion)promotion));
        }
        return processedAmount;
    }
}
