package com.online.casino.service;

import com.online.casino.model.GameEventTransaction;
import com.online.casino.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public GameEventTransaction createTransaction(UUID playerId, String transactionType, Double transactionAmount) {
        return transactionRepository.save(new GameEventTransaction(playerId, transactionType,transactionAmount));
    }

    public List<GameEventTransaction> getAllTransactions() {
        return StreamSupport.stream(transactionRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<GameEventTransaction> getTransactionsByPlayerId(UUID playerId) {
        return StreamSupport.stream(transactionRepository.findByPlayerId(playerId).spliterator(), false)
                .collect(Collectors.toList());
    }
}
