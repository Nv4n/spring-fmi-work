package com.spring.bank.service.impl;

import com.spring.bank.dao.BankAccountRepository;
import com.spring.bank.dao.UserRepository;
import com.spring.bank.exception.EntityNotFoundException;
import com.spring.bank.exception.InvalidEntityException;
import com.spring.bank.model.BankAccount;
import com.spring.bank.model.User;
import com.spring.bank.service.BankAccountService;
import jakarta.validation.Valid;
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
    @Autowired
    private UserRepository userRepo;


    @Override
    public Collection<BankAccount> getBankAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public Collection<BankAccount> getBankAccountsByOwnerId(UUID id) {
        return accountRepo.findAllByOwnerId(id);
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
    public BankAccount createBankAccount(@Valid BankAccount bankAccount) {
        UUID ownerId;
        if (bankAccount.getOwner() != null && bankAccount.getOwner().getId() != null) {
            ownerId = bankAccount.getOwner().getId();
        } else {
            ownerId = bankAccount.getOwnerId();
        }
        if (ownerId != null) {
            User owner = userRepo.findById(ownerId)
                    .orElseThrow(() -> new InvalidEntityException("User with ID=" + ownerId + " does not exist."));
            bankAccount.setOwner(owner);
        }
        bankAccount.setCreatedAt(new Date());
        bankAccount.setModifiedAt(new Date());
        return accountRepo.save(bankAccount);
    }

    @Override
    @Transactional
    public BankAccount updateBankAccount(BankAccount bankAccount) {
        bankAccount.setModifiedAt(new Date());
        BankAccount old = getBankAccountById(bankAccount.getId());
        if (old == null) {
            throw new EntityNotFoundException(String.format("Bank Account with ID=%s not found.", bankAccount.getId()));
        }
        if (!old.getIban().equals(bankAccount.getIban())) {
            throw new InvalidEntityException("IBAN of bank account couldn't be changed.");
        }
        bankAccount.setOwner(old.getOwner());
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
