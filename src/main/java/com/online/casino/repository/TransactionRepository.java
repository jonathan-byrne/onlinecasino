package com.online.casino.repository;

import com.online.casino.model.GameRoundTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends CrudRepository<GameRoundTransaction,Long> {
    List<GameRoundTransaction> findByPlayerId(UUID playerId);
}
