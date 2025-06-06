package com.retailer.reward_service.service;

import com.retailer.reward_service.dto.RewardResponse;
import com.retailer.reward_service.entity.Customer;
import com.retailer.reward_service.entity.Transaction;
import com.retailer.reward_service.exceptions.CustomerNotFoundException;
import com.retailer.reward_service.exceptions.InvalidTransactionAmountException;
import com.retailer.reward_service.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

/**
 * Service to calculate reward points.
 */

@RequiredArgsConstructor
@Service
public class RewardServiceImpl implements RewardService {

    private final CustomerRepository customerRepository;

    /**
     * Calculates reward summary for all customers.
     * @return list of reward responses
     */
    @Override
    public List<RewardResponse> calculateAllCustomersRewards() {
        List<Customer> customers = customerRepository.findAll();
        List<RewardResponse> rewards = new ArrayList<>();

        for (Customer customer : customers) {
            rewards.add(calculateCustomerRewards(customer));
        }
        return rewards;
    }

    // Add this private method to calculate rewards directly from a Customer object
    private RewardResponse calculateCustomerRewards(Customer customer) {
        Map<String, Integer> monthlyPoints = new HashMap<>();
        int totalPoints = 0;

        LocalDate now = LocalDate.now();
        LocalDate threeMonthsAgo = now.minusMonths(3);

        for (Transaction transaction : customer.getTransactions()) {
            if (!transaction.getTransactionDate().isBefore(threeMonthsAgo)) {  // inclusive 3 months
                int points = calculatePoints(transaction.getAmount());
                String month = transaction.getTransactionDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                monthlyPoints.put(month, monthlyPoints.getOrDefault(month, 0) + points);
                totalPoints += points;
            }
        }

        return new RewardResponse(customer.getCustomerId(), customer.getName(), monthlyPoints, totalPoints);
    }

    /**
     * Calculates rewards for a single customer.
     * @param customerId the ID of the customer
     * @return reward response for the customer
     */
    @Override
    public RewardResponse calculateCustomerRewardsById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        return calculateCustomerRewards(customer);
    }

    /**
     * Calculates points based on amount spent.
     * @param amount the transaction amount
     * @return reward points
     */
    private int calculatePoints(Double amount) {

        if (amount == null || amount <= 0) {
            throw new InvalidTransactionAmountException("Transaction amount must be greater than 0");
        }
        int points = 0;
        if (amount > 100) {
            points += (int) ((amount - 100) * 2) + 50;
        }
        else if (amount > 50) {
            points += (int) (amount - 50);
        }
        return points;
    }
}
