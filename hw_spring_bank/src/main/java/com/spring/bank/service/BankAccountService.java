package com.spring.bank.service;

import com.spring.bank.model.BankAccount;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface BankAccountService {
    Collection<BankAccount> getBankAccounts();

    BankAccount getBankAccountById(UUID id);

    BankAccount getBankAccountByIBAN(String id);

    BankAccount createBankAccount(BankAccount bankAccount);

    BankAccount updateBankAccount(BankAccount bankAccount);

    BankAccount deleteBankAccount(UUID id);

    Long getBankAccountCount();

    List<BankAccount> createBankAccountBatch(List<BankAccount> bankAccounts);
}
