package com.online.casino.model.promotions;

import com.online.casino.model.GameEventTransaction;

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
    private int numberOfFreeWagersRemaining;

    @Override
    public void process(GameEventTransaction eventTransaction) {
        //get FreeWagersPromotion db entry for user id and promotion type
        //
    }
}
