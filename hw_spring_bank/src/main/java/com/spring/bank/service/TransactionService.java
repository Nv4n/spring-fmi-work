package com.spring.bank.service;

import com.spring.bank.model.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Collection<Transaction> getTransactions();

    Transaction getTransactionById(UUID id);

    Transaction createTransaction(Transaction transaction);

    Transaction updateTransaction(Transaction transaction);

    Transaction deleteTransaction(UUID id);

    Long getTransactionCount();

    List<Transaction> createTransactionBatch(List<Transaction> transactions);
}
