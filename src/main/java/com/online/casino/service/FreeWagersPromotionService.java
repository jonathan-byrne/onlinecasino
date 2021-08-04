package com.online.casino.service;

import com.online.casino.model.Player;
import com.online.casino.model.promotion.DummyPromotion;
import com.online.casino.model.promotion.FreeWagersPromotion;
import com.online.casino.model.promotion.Promotion;
import com.online.casino.repository.FreeWagersPromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreeWagersPromotionService {

    FreeWagersPromotionRepository freeWagersPromotionRepository;

    @Autowired
    public FreeWagersPromotionService(FreeWagersPromotionRepository freeWagersPromotionRepository){
        this.freeWagersPromotionRepository = freeWagersPromotionRepository;
    }

    public Promotion createFreeWagersPromotion(Player player, String promotionCode, Integer freeWagersAmount) {
        return freeWagersPromotionRepository.save(new FreeWagersPromotion(player, promotionCode, freeWagersAmount));
    }

    public Promotion updateFreeWagersPromotion(FreeWagersPromotion freeWagersPromotion) {
        //Player player, String promotionCode, Integer freeWagersAmount
        return freeWagersPromotionRepository. save(freeWagersPromotion);
    }

    public Promotion findByPlayerAndPromotionCode(Player player, String promotionCode) {
        Promotion promotion = freeWagersPromotionRepository.findByPlayerIdAndPromotionCode(player.getPlayerId(), promotionCode);

        //we check for an empty result from the freeWagersPromotionRepository in the case that the FreeWagerPromotion isn't found
        //if the result is null we return a dummy promotion, this way we won't struggle with null pointer exceptions down the line
        //and when processing the amount to be altered the promotion won't alter the data amount given
        return  promotion == null ? new DummyPromotion(): promotion;
    }
}
