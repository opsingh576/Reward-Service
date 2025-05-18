package com.retailer.reward_service.repositories;

import com.retailer.reward_service.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
