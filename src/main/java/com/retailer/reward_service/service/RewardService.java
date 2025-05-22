package com.retailer.reward_service.service;
import com.retailer.reward_service.dto.RewardResponse;
import java.util.List;

public interface RewardService {

    /**
     * Calculates reward summary for all customers.
     */
    List<RewardResponse> calculateAllCustomersRewards();

    /**
     * Calculates rewards for a single customer.
     */
    RewardResponse calculateCustomerRewardsById(Integer customerId);
}
