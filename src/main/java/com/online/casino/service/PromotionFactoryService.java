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

    public Promotion getPromotion(Player player, String promotionCode, Boolean createNewPromotion) {
        Promotion promotion;

        //checking the promotion code like this will protect us against null pointer exceptions
        if(PromotionCodes.FREE_WAGERS_PROMOTION_CODE.equalsIgnoreCase(promotionCode)) {
            promotion = freeWagersPromotionService.findByPlayerAndPromotionCode(player, promotionCode);

            //if a dummy promotion is returned then create a new promotion, we are avoiding branching on nulls to check whether the promotion
            //exists or not
            if((DummyPromotion.PROMOTION_CODE.equalsIgnoreCase(promotion.getPromotionCode()))) {
                if(createNewPromotion) {
                    promotion = createFreeWagersPromotion(player, promotionCode);
                }
                
            //if current promotion is exhausted then create a new one
            } else if((((FreeWagersPromotion)promotion).getNumberOfFreeWagersRemaining() <= 0) && createNewPromotion) {
                //delete old promotion
                freeWagersPromotionService.deleteFreeWagersPromotion((FreeWagersPromotion)promotion);

                //create new promotion
                if(createNewPromotion) {
                    promotion = createFreeWagersPromotion(player, promotionCode);
                }
            }
        } else {
            //in the case that we don't have a promotion of any type in the if statement above
            promotion = new DummyPromotion();
        }

        return promotion;
    }

    private Promotion createFreeWagersPromotion(Player player, String promotionCode) {
        return freeWagersPromotionService.createFreeWagersPromotion(player, promotionCode, FreeWagersPromotion.FREE_WAGER_PROMOTION_START_AMOUNT);
    }
}
