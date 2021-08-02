package com.online.casino.dto;

import com.online.casino.model.Player;

import java.util.UUID;

public class PlayerDTO {

    UUID playerId;
    String firstName;
    String lastName;
    String email;
    Double currentBalance;

    public PlayerDTO(UUID playerId, String firstName, String lastName, String email, Double currentBalance) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.currentBalance = currentBalance;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public static PlayerDTO toPlayerDTO(Player player) {
        return new PlayerDTO(player.getPlayerId(), player.getFirstname(), player.getLastname(), player.getEmail(), player.getCurrentBalance());
    }
}
