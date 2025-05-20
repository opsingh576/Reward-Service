package com.retailer.reward_service.controller;

import com.retailer.reward_service.dto.RewardResponse;
import com.retailer.reward_service.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rewards/customers")
@RequiredArgsConstructor
public class RewardController {
    private final RewardService rewardService;

    /**
     * Retrieves reward details for customers.
     * <p>
     * If a customer ID is provided as a request parameter, the method returns the reward details
     * for that specific customer. If no ID is provided, it returns the reward details for all customers.
     *
     * @param customerId (optional) the ID of the customer to fetch reward details for.
     * @return a {@link ResponseEntity} containing a list of {@link RewardResponse} objects.
     * The list will contain either one item (for a specific customer) or multiple items (for all customers).
     */

    @GetMapping
    public ResponseEntity<List<RewardResponse>> getCustomersRewards(@RequestParam(value = "id", required = false) Integer CustomerId) {
        if (CustomerId == null) {
            return ResponseEntity.ok(rewardService.calculateAllCustomersRewards());
        } else {
            return ResponseEntity.ok(List.of(rewardService.calculateCustomerRewardsById(CustomerId)));
        }
    }
}


