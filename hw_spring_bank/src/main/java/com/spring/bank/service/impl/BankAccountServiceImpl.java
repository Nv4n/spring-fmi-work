package com.spring.bank.service.impl;

import com.spring.bank.dao.BankAccountRepository;
import com.spring.bank.exception.EntityNotFoundException;
import com.spring.bank.exception.InvalidEntityException;
import com.spring.bank.model.BankAccount;
import com.spring.bank.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository accountRepo;


    @Override
    public Collection<BankAccount> getBankAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public BankAccount getBankAccountById(UUID id) {
        return accountRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Bank Account with ID{ %s } not found", id)));
    }

    @Override
    public BankAccount getBankAccountByIBAN(String iban) {
        return accountRepo.findByIban(iban).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Bank Account %s not found", iban)
                ));
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        accountRepo.findById(bankAccount.getId()).ifPresent(ba -> {
            throw new InvalidEntityException(
                    String.format("Bank Account with ID{ %s } already exist.", bankAccount.getId()));
        });
        bankAccount.setCreatedAt(new Date());
        bankAccount.setModifiedAt(new Date());
        return accountRepo.save(bankAccount);
    }

    @Override
    @Transactional
    public BankAccount updateBankAccount(BankAccount bankAccount) {
        BankAccount old = getBankAccountById(bankAccount.getId());
        if (!old.getIban().equals(bankAccount.getIban())) {
            throw new InvalidEntityException("IBAN of bank account couldn't be changed.");
        }
        return null;
    }

    @Override
    public BankAccount deleteBankAccount(UUID id) {
        BankAccount old = accountRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("Bank Account with ID{ %s } not found.", id)
                ));
        accountRepo.deleteById(id);
        return old;
    }

    @Override
    public Long getBankAccountCount() {
        return accountRepo.count();
    }

    @Override
    @Transactional
    public List<BankAccount> createBankAccountBatch(List<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .map(this::createBankAccount)
                .collect(Collectors.toList());
    }
}
