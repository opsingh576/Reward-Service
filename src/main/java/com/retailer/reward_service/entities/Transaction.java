package com.retailer.reward_service.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents a transaction entity.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The customer associated with this transaction.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    /**
     * The amount spent in this transaction. Must be greater than 1.
     */
    @NotNull(message = "Amount must not be null")
   // @Min(value = 1, message = "Amount must be greater than 1")
    private Double amount;

    /**
     * The date when the transaction occurred.
     */
    private LocalDate transactionDate;


    public Transaction(Customer customer, Double amount, LocalDate transactionDate) {
        this.customer = customer;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

}
