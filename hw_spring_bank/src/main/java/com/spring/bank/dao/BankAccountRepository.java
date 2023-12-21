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

    @Query(value = "SELECT * FROM bankacounts ba WHERE owner_id = :ownerId", nativeQuery = true)
    List<BankAccount> findAllByOwnerId(@Param("ownerId") UUID uuid);

}
