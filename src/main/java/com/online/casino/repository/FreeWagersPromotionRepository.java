package com.online.casino.repository;

import com.online.casino.model.promotion.FreeWagersPromotion;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FreeWagersPromotionRepository extends CrudRepository<FreeWagersPromotion, Long> {

    FreeWagersPromotion findByPlayerIdAndPromotionCode(UUID playerId, String promotionCode);
}
