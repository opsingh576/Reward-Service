package com.retailer.reward_service.repositories;

import com.retailer.reward_service.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
