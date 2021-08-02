package com.online.casino.service;

import com.online.casino.model.GameRoundTransaction;
import com.online.casino.model.Player;
import com.online.casino.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public GameRoundTransaction createTransaction(UUID playerId, String transactionType, Double transactionAmount) {
        return transactionRepository.save(new GameRoundTransaction(playerId, transactionType,transactionAmount));
    }

    public List<GameRoundTransaction> getAllTransactions() {
        return StreamSupport.stream(transactionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<GameRoundTransaction> getTransactionsByPlayerId(UUID playerId) {
        return StreamSupport.stream(transactionRepository.findByPlayerId(playerId).spliterator(), false)
                .collect(Collectors.toList());
    }
}
