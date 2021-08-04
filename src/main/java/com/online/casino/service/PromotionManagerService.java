package com.online.casino.service;

import com.online.casino.model.Player;
import com.online.casino.model.promotion.FreeWagersPromotion;
import com.online.casino.model.promotion.Promotion;
import com.online.casino.util.PromotionCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The promotion engine will manage itself according to the promotion code.
 * We can add new specific promotion database access services to this service class to handle new promotions
 * if we need to.
 *
 * A new promotion class will implement the Promotion interface and allow a promotion to
 * process an amount sent in specific to the new promotion behaviour (implemented in the processGameEvent() method).
 *
 * The promotions engine is designed on the idea that the parameters for any promotion are going to be a player
 * and an amount. The promotion will update the amount if applicable
 */
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
        //the dummy promotion will run without altering the amount and without throwing a null pointer exception
        Double processedAmount = promotion.processGameEvent(player, amount);

        //update promotion database entry
        if(PromotionCodes.FREE_WAGERS_PROMOTION_CODE.equalsIgnoreCase(promotion.getPromotionCode())) {
            freeWagersPromotionService.updateFreeWagersPromotion(((FreeWagersPromotion)promotion));
        }
        return processedAmount;
    }
}