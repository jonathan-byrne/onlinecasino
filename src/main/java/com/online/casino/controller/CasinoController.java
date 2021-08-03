package com.online.casino.controller;

import com.online.casino.dto.GameEventTransactionDTO;
import com.online.casino.dto.PlayerDTO;
import com.online.casino.model.GameEventTransaction;
import com.online.casino.model.Player;
import com.online.casino.service.PlayerService;
import com.online.casino.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.online.casino.dto.PlayerDTO.toPlayerDTO;

@RestController
public class CasinoController {

    private static final String PASSWORD_ANSWER = "swordfish";
    private static final int TRANSACTIONS_LIST_LIMIT = 10;
    private final PlayerService playerService;
    private final TransactionService transactionService;

    @Autowired
    public CasinoController(PlayerService playerService, TransactionService transactionService) {
        this.playerService = playerService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    String entryPoint() {
        return "The Casino Server is up and running...";
    }

    @PostMapping("/createplayer")
    @ResponseBody
    public PlayerDTO createPlayer(@RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName,
                                  @RequestParam("username") String username, @RequestParam("email") String email) {
            return toPlayerDTO(playerService.createPlayer(firstName, lastName, username, email, 0.0));
    }

    @GetMapping("/getallplayers")
    @ResponseBody
    public List<PlayerDTO> getAllPlayers() {
        return playerService.getAllPlayers()
                .stream()
                .map(PlayerDTO::toPlayerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/getplayer")
    @ResponseBody
    public PlayerDTO getPlayer (@RequestParam UUID playerId) {
        Player player = playerService.getByPlayerId(playerId);

        checkPlayerExists(player);

        return toPlayerDTO(playerService.getByPlayerId(playerId));
    }

    @GetMapping("/getalltransactions")
    @ResponseBody
    public List<GameEventTransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions()
                .stream()
                .map(GameEventTransactionDTO::toGameRoundTransactionDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/getcurrentbalance")
    @ResponseBody
    public Double getCurrentBalance(@RequestParam UUID playerId) {
        return playerService.getByPlayerId(playerId).getCurrentBalance();
    }

    @PostMapping("/deductwager")
    public PlayerDTO deductWager(@RequestParam UUID playerId,  @RequestParam Double wagerAmount) {
        Player player = playerService.getByPlayerId(playerId);

        checkPlayerExists(player);

        if(player.getCurrentBalance() <= 0.0 && wagerAmount > 0.0) {
            throw new ResponseStatusException(
                    HttpStatus.I_AM_A_TEAPOT);
        }

        player.wager(wagerAmount);
        transactionService.createTransaction(playerId, GameEventTransaction.WAGER_TRANSACTION_TYPE, wagerAmount);

        return toPlayerDTO(playerService.updatePlayer(player));
    }

    @PostMapping("/addwinnings")
    public PlayerDTO addWinnings(@RequestParam UUID playerId, @RequestParam Double winningsAmount) {
        Player player = playerService.getByPlayerId(playerId);

        checkPlayerExists(player);

        player.addWinnings(winningsAmount);
        transactionService.createTransaction(playerId, GameEventTransaction.WINNINGS_TRANSACTION_TYPE, winningsAmount);
        return toPlayerDTO(playerService.updatePlayer(player));
    }

    @GetMapping("/latesttransactions")
    @ResponseBody
    public List<GameEventTransactionDTO> latestTransactions(@RequestParam String username, @RequestParam String password) {

        if(!password.equals(PASSWORD_ANSWER)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST);
        }

        Player player = playerService.getByUsername(username);

        checkPlayerExists(player);

        List<GameEventTransaction> transactions = transactionService.getTransactionsByPlayerId(player.getPlayerId());

        //swapped the comparison around to compare transaction2 create date to transaction1 date so that
        //we get the latest transactions at the top of our list
        transactions.sort((transaction1, transaction2)
                -> transaction2.getCreateDate().compareTo(
                transaction1.getCreateDate()));

        //check the size of the transaction list passed in, if the list's size is less than the TRANSACTIONS_LIST_LIMIT
        //adjust transaction sublist size to be the size of the list passed in. This handles an ArrayIndexOutOfBounds runtime exception
        int transactionsAmountLimit = transactions.size() >= TRANSACTIONS_LIST_LIMIT ? TRANSACTIONS_LIST_LIMIT : transactions.size();
        List<GameEventTransaction> responseTransactions = transactions.subList(0, transactionsAmountLimit);

        return responseTransactions
                .stream()
                .map(GameEventTransactionDTO::toGameRoundTransactionDTO)
                .collect(Collectors.toList());
    }

    private void checkPlayerExists(Player player) {
        if(player == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
