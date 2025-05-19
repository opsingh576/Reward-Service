package com.retailer.reward_service.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Customer name Should not blank")
    @NotNull(message = "Customer name should not null")
    private String name;

    /**
     * List of transactions made by the customer.
     */
     @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Transaction> transactions;
}
