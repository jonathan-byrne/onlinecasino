package com.online.casino.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private UUID playerId;

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Double currentBalance;

    public Player() {

    }

    public Player(String firstname, String lastname, String username, String email, Double currentBalance) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.currentBalance = currentBalance;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {  return username; }

    public String getEmail() {
        return email;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public String toString() {
        return "First Name: " + firstname + " Last Name: " + lastname + " Email Address: " +
                email + " current balance: " + currentBalance;
    }

    public void wager(Double wagerAmount) {
        currentBalance -= wagerAmount;
    }

    public void addWinnings(Double winningsAmount) {
        currentBalance += winningsAmount;
    }
}
