package com.spring.bank.dao;

import com.spring.bank.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByIban(String iban);

    @Query(value = "SELECT ba FROM BankAccount ba WHERE ba.owner.id = :ownerId")
    List<BankAccount> findAllByOwnerId(@Param("ownerId") UUID uuid);

    @Query(value = "SELECT ba FROM BankAccount ba JOIN User us ON ba.owner.id = us.id WHERE us.username= :username")
    List<BankAccount> findAllByOwnerUsername(@Param("username") String ownerUsername);
}
