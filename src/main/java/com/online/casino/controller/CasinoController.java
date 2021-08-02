package com.online.casino.controller;

import com.online.casino.dto.GameRoundTransactionDTO;
import com.online.casino.dto.PlayerDTO;
import com.online.casino.model.GameRoundTransaction;
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

    private static final String TRANSACTION_TYPE_WAGER = "wager";
    private static final String TRANSACTION_TYPE_WINNINGS = "winnings";
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
    public PlayerDTO createPlayer(@RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName, @RequestParam("email") String email) {
            return toPlayerDTO(playerService.createPlayer(firstName, lastName, email, 0.0));
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

        if(player == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return toPlayerDTO(playerService.getByPlayerId(playerId));
    }

    @GetMapping("/getalltransactions")
    @ResponseBody
    public List<GameRoundTransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions()
                .stream()
                .map(GameRoundTransactionDTO::toGameRoundTransactionDTO)
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

        if(player.getCurrentBalance() <= 0.0 && wagerAmount > 0.0) {
            throw new ResponseStatusException(
                    HttpStatus.I_AM_A_TEAPOT);
        }

        player.wager(wagerAmount);
        transactionService.createTransaction(playerId, TRANSACTION_TYPE_WAGER, wagerAmount);

        return toPlayerDTO(playerService.updatePlayer(player));
    }

    @PostMapping("/addwinnings")
    public PlayerDTO addWinnings(@RequestParam UUID playerId, @RequestParam Double winningsAmount) {
        Player player = playerService.getByPlayerId(playerId);
        player.addWinnings(winningsAmount);
        transactionService.createTransaction(playerId, TRANSACTION_TYPE_WINNINGS, winningsAmount);
        return toPlayerDTO(playerService.updatePlayer(player));
    }

    @GetMapping("/latesttransactions")
    @ResponseBody
    public List<GameRoundTransactionDTO> latestTransactions(@RequestParam UUID playerId, @RequestParam String password) {

        if(!password.equals("swordfish")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST);
        }

        List<GameRoundTransaction> transactions = transactionService.getTransactionsByPlayerId(playerId);

        transactions.sort((o1, o2)
                -> o2.getCreateDate().compareTo(
                o1.getCreateDate()));
        int transactionNumberLimit = transactions.size() >= 10 ? 10 : transactions.size();
        List<GameRoundTransaction> responseTransactions = transactions.subList(0, transactionNumberLimit);

        return responseTransactions
                .stream()
                .map(GameRoundTransactionDTO::toGameRoundTransactionDTO)
                .collect(Collectors.toList());
    }
}
