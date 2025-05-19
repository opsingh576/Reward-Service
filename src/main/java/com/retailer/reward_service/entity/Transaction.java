package com.retailer.reward_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents a transaction entity.
 */
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The customer associated with this transaction.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    @NonNull
    private Customer customer;

    /**
     * The amount spent in this transaction. Must be grater than 0.
     */
    @NonNull
    private Double amount;

    /**
     * The date when the transaction occurred.
     */
    @NonNull
    private LocalDate transactionDate;
}
