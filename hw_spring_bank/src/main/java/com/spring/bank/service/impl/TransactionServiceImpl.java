package com.spring.bank.service.impl;

import com.spring.bank.dao.TransactionRepository;
import com.spring.bank.exception.EntityNotFoundException;
import com.spring.bank.exception.InvalidEntityException;
import com.spring.bank.model.Transaction;
import com.spring.bank.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;

    @Override
    public Collection<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }

    @Override
    public Transaction getTransactionById(UUID id) {
        return transactionRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Transaction with ID{ %s } not found", id))
        );
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transactionRepo.findById(transaction.getId()).ifPresent(t -> {
            throw new InvalidEntityException(
                    String.format("Transaction with ID{ %s } already exist.", transaction.getId())
            );
        });
        return transactionRepo.save(transaction);
    }

    @Override
    @Transactional
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    @Override
    public Transaction deleteTransaction(UUID id) {
        Transaction old = transactionRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Transaction with ID{ %s } not found.", id)));
        transactionRepo.deleteById(id);
        return old;
    }

    @Override
    public Long getTransactionCount() {
        return transactionRepo.count();
    }

    @Override
    @Transactional
    public List<Transaction> createTransactionBatch(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::createTransaction)
                .collect(Collectors.toList());
    }
}
