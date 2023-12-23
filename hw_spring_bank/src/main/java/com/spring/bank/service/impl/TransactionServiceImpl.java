package com.spring.bank.service.impl;

import com.spring.bank.dao.BankAccountRepository;
import com.spring.bank.dao.TransactionRepository;
import com.spring.bank.exception.EntityNotFoundException;
import com.spring.bank.model.BankAccount;
import com.spring.bank.model.Transaction;
import com.spring.bank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private BankAccountRepository accountRepo;

    @Override
    public Collection<Transaction> getTransactions() {
        return transactionRepo.findAll(Sort.by("transactionDate"));
    }

    @Override
    public Collection<Transaction> getTransactionsByAccountIdWithLimit(UUID id, int limit) {
        return transactionRepo.findAllByAccountIdWithLimit(id, limit);
    }

    @Override
    public Transaction getTransactionById(UUID id) {
        return transactionRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Transaction with ID{ %s } not found", id))
        );
    }

    @Override
    public Transaction createTransaction(@Valid Transaction transaction) {
        BankAccount sender = transaction.getSender();
        BankAccount receiver = transaction.getReceiver();

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        receiver.setBalance(receiver.getBalance() + transaction.getAmount());
        accountRepo.save(sender);
        accountRepo.save(receiver);
        transaction.setCreatedAt(new Date());
        
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
