package com.retailer.reward_service.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents a customer in the reward system.
 * Each customer has an ID, a name, and a list of associated transactions.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    /**
     * Unique identifier for the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    /**
     * Name of the customer.
     */
    private String name;

    /**
     * List of transactions made by the customer.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Transaction> transactions;
}
