package com.retailer.reward_service.service;

import com.retailer.reward_service.dto.RewardResponse;
import com.retailer.reward_service.entity.Customer;
import com.retailer.reward_service.entity.Transaction;
import com.retailer.reward_service.exceptions.CustomerNotFoundException;
import com.retailer.reward_service.exceptions.InvalidTransactionAmountException;
import com.retailer.reward_service.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RewardServiceImpl} using Mockito.
 * Covers reward calculation logic for both individual and multiple customers.
 */
class RewardServiceImplApplicationTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private RewardServiceImpl rewardServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Creates a {@link Customer} object with the given ID, name, and transactions.
     * @param id           the customer ID
     * @param name         the customer's name
     * @param transactions the list of transactions
     * @return a populated {@link Customer} instance
     */
    private Customer createCustomerWithTransactions(Integer id, String name, List<Transaction> transactions) {
        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setName(name);
        customer.setTransactions(transactions);
        return customer;
    }

    /**
     * Creates a {@link Transaction} object with the given amount and date.
     * @param amount the transaction amount
     * @param date   the transaction date
     * @return a {@link Transaction} instance
     */
    private Transaction createTransaction(Double amount, LocalDate date) {
        Transaction tx = new Transaction();
        tx.setAmount(amount);
        tx.setTransactionDate(date);
        return tx;
    }

    /**
     * Tests successful calculation of reward points for a single customer
     * with valid transactions within the last 3 months.
     */
    @Test
    void testCalculateCustomerRewardsById_Success() {
        Integer customerId = 1;
        LocalDate now = LocalDate.now();
        List<Transaction> transactions = Arrays.asList(
                createTransaction(120.0, now.minusMonths(1)),
                createTransaction(75.0, now.minusMonths(2)),
                createTransaction(40.0, now.minusMonths(1)), // should not count
                createTransaction(200.0, now.minusMonths(3)) // edge of 3 months
        );
        Customer customer = createCustomerWithTransactions(customerId, "John", transactions);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        RewardResponse response = rewardServiceImpl.calculateCustomerRewardsById(customerId);

        assertEquals(customerId, response.getCustomerId());
        assertEquals("John", response.getCustomerName());
        assertTrue(response.getTotalPoints() > 0);
        assertFalse(response.getMonthlyPoints().isEmpty());
    }

    /**
     * Tests that a {@link CustomerNotFoundException} is thrown when a customer ID does not exist.
     */
    @Test
    void testCalculateCustomerRewardsById_CustomerNotFound() {
        when(customerRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> rewardServiceImpl.calculateCustomerRewardsById(99));
    }

    /**
     * Tests that an {@link InvalidTransactionAmountException} is thrown
     * when a customer has zero or negative transaction amounts.
     */
    @Test
    void testCalculateCustomerRewardsById_ZeroOrNegativeAmount() {
        Integer customerId = 2;
        LocalDate now = LocalDate.now();
        List<Transaction> transactions = Arrays.asList(
                createTransaction(0.0, now),
                createTransaction(-10.0, now)
        );
        Customer customer = createCustomerWithTransactions(customerId, "Jane", transactions);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        assertThrows(InvalidTransactionAmountException.class, () -> rewardServiceImpl.calculateCustomerRewardsById(customerId));
    }

    /**
     * Tests reward calculation for all customers.
     * Validates that the result contains entries for each customer.
     */
    @Test
    void testCalculateAllCustomersRewards() {
        LocalDate now = LocalDate.now();
        Customer c1 = createCustomerWithTransactions(1, "A", Arrays.asList(createTransaction(120.0, now)));
        Customer c2 = createCustomerWithTransactions(2, "B", Arrays.asList(createTransaction(80.0, now.minusMonths(1))));
        List<Customer> customers = Arrays.asList(c1, c2);

        when(customerRepository.findAll()).thenReturn(customers);

        List<RewardResponse> responses = rewardServiceImpl.calculateAllCustomersRewards();

        assertEquals(2, responses.size());
        RewardResponse respA = responses.stream().filter(r -> "A".equals(r.getCustomerName())).findFirst().orElse(null);
        RewardResponse respB = responses.stream().filter(r -> "B".equals(r.getCustomerName())).findFirst().orElse(null);
        assertNotNull(respA);
        assertNotNull(respB);
    }

    /**
     * Tests that transactions older than 3 months are not included in reward point calculation.
     */
    @Test
    void testCalculateCustomerRewardsById_TransactionOutsideThreeMonths() {
        Integer customerId = 3;
        LocalDate now = LocalDate.now();
        List<Transaction> transactions = Arrays.asList(
                createTransaction(120.0, now.minusMonths(4)), // should not count
                createTransaction(90.0, now.minusMonths(5))   // should not count
        );
        Customer customer = createCustomerWithTransactions(customerId, "Old", transactions);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        RewardResponse response = rewardServiceImpl.calculateCustomerRewardsById(customerId);

        assertEquals(0, response.getTotalPoints());
        assertTrue(response.getMonthlyPoints().isEmpty());
    }

    /**
     * Tests that a customer with no transactions gets zero reward points and empty monthly breakdown.
     */
    @Test
    void testCalculateCustomerRewardsById_NoTransactions() {
        Integer customerId = 10;
        Customer customer = createCustomerWithTransactions(customerId, "NoTx", Collections.emptyList());
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        RewardResponse response = rewardServiceImpl.calculateCustomerRewardsById(customerId);

        assertEquals(customerId, response.getCustomerId());
        assertEquals("NoTx", response.getCustomerName());
        assertEquals(0, response.getTotalPoints());
        assertTrue(response.getMonthlyPoints().isEmpty());
    }

}
