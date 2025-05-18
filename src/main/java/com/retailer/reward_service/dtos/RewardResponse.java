package com.retailer.reward_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO representing the reward response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {

    private Integer customerId;
    private String customerName;
    private Map<String, Integer> monthlyPoints;
    private Integer totalPoints;
}

