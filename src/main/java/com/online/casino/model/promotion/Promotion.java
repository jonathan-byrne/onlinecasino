package com.online.casino.model.promotion;

import com.online.casino.model.Player;

public interface Promotion {
    public Double processGameEvent(Player player, Double amount);
    public String getPromotionCode();
}
