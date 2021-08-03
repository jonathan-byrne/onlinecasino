package com.online.casino.service;

import com.online.casino.model.Player;
import com.online.casino.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(String firstname, String lastname, String username, String email, Double currentBalance) {
        return playerRepository.save(new Player(firstname, lastname, username, email, currentBalance));
    }

    public Player updatePlayer(Player player) {
        return  playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return StreamSupport.stream(playerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Player getByPlayerId(UUID playerId) {
        return playerRepository.findByPlayerId(playerId);
    }

    public Player getByUsername(String username) {
        return playerRepository.findByUsername(username);
    }
}
