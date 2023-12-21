package com.spring.bank.dao;

import com.spring.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query(value = "SELECT * FROM TRANSACTIONS t WHERE t.sender_id = :id or t.receiver_id = :id ORDER BY t.transaction_date DESC FETCH FIRST :limit ROWS ONLY"
            , nativeQuery = true)
    List<Transaction> findAllByAccountIdWithLimit(@Param("id") UUID id, @Param("limit") int limit);
}
