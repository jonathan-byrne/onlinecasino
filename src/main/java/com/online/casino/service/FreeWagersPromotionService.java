package com.online.casino.service;

import com.online.casino.model.Player;
import com.online.casino.model.promotions.DummyPromotion;
import com.online.casino.model.promotions.FreeWagersPromotion;
import com.online.casino.model.promotions.Promotion;
import com.online.casino.model.promotions.PromotionFactoryService;
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

    public Promotion createOrUpdateFreeWagersPromotion(Player player, String promotionCode, Integer freeWagersAmount) {
        return freeWagersPromotionRepository.save(new FreeWagersPromotion(player, promotionCode, freeWagersAmount));
    }

    public Promotion findByPlayerAndPromotionCode(Player player, String promotionCode) {
        Promotion promotion = freeWagersPromotionRepository.findByPlayerIdAndPromotionCode(player.getPlayerId(), promotionCode);

        //we check for an empty result from the freeWagersPromotionRepository in the case that the FreeWagerPromotion isn't found
        //if the result is null we return a dummy promotion, this way we won't struggle with null pointer exceptions down the line
        //and the promotion won't alter the data being given
        return  promotion == null ? new DummyPromotion(): promotion;
    }
}
