package com.online.casino.repository;

import com.online.casino.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PlayerRepository extends CrudRepository<Player,Long> {

    Player findByPlayerId(UUID playerId);

    Player findByUsername(String username);
}
