package com.online.casino.repository;

import com.online.casino.model.GameEventTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends CrudRepository<GameEventTransaction,Long> {
    List<GameEventTransaction> findByPlayerId(UUID playerId);
}
