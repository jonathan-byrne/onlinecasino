package com.online.casino.model.promotions;

import com.online.casino.model.GameEventTransaction;

public interface Promotion {
    public void process(GameEventTransaction eventTransaction);
}
