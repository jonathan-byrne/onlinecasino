package com.online.casino.model.promotions;

import com.online.casino.model.Player;

public interface Promotion {
    public Double processGameEvent(Player player, Double amount);
    public String getPromotionCode();
}
