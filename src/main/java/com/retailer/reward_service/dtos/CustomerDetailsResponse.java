package com.retailer.reward_service.dtos;


import com.retailer.reward_service.entities.Transaction;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDetailsResponse {

    private Integer customerId;
    private String name;
    private List<Transaction> transactions;
}
