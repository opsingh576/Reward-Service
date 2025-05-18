package com.retailer.reward_service.controller;

import com.retailer.reward_service.dtos.CustomerDetailsResponse;
import com.retailer.reward_service.dtos.RewardResponse;
import com.retailer.reward_service.entities.Customer;
import com.retailer.reward_service.service.CustomerService;
import com.retailer.reward_service.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@Validated
@RequiredArgsConstructor
public class RewardController {


    private final RewardService rewardService;

    private final CustomerService customerService;

    /**
     * Get rewards summary for all customers.
     * @return list of RewardResponse
     */
    @GetMapping
    public ResponseEntity<List<RewardResponse>> getAllRewards() {
        return ResponseEntity.ok(rewardService.calculateCustomerRewards());
    }

    /**
     * Get rewards summary for a single customer.
     * customerId ID of the customer
     * @return RewardResponse
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<RewardResponse> getRewardByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(rewardService.getCustomerRewardById(customerId));
    }

    /**
     * Get customer details by ID.
     * @param customerId ID of the customer
     * @return Customer
     */
    @GetMapping("/customer/CustomerDetails/{customerId}")
    public ResponseEntity<CustomerDetailsResponse> getCustomerDetailsById(@PathVariable Integer customerId) {
        CustomerDetailsResponse customer = customerService.getCustomerDetailsById(customerId);
        return ResponseEntity.ok(customer);
    }

    /**
     * Get All customer details and its transaction.
     *
     * @return Customer
     **/

    @GetMapping("/customer/AllCustomerDetails")
    public ResponseEntity<List<Customer>> getAllCustomerDetails() {
        List<Customer> customer = customerService.getAllCustomerDetails();
        return ResponseEntity.ok(customer);
    }


}


