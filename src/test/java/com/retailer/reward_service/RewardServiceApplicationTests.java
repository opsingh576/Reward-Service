package com.retailer.reward_service;

import com.retailer.reward_service.entities.Customer;
import com.retailer.reward_service.entities.Transaction;
import com.retailer.reward_service.exceptions.CustomerNotFoundException;
import com.retailer.reward_service.exceptions.InvalidTransactionAmountException;
import com.retailer.reward_service.repositories.CustomerRepository;
import com.retailer.reward_service.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RewardServiceApplicationTests {
	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private RewardService rewardService;


	@Test
	void testGetCustomerRewardById_customerNotFound() {
		when(customerRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(CustomerNotFoundException.class, () -> rewardService.getCustomerRewardById(99));
	}

	@Test
	void testGetCustomerRewardById_TransactionAmountInvalid() {
		Customer customer = new Customer();
		customer.setCustomerId(2);
		customer.setName("InvalidUser");

		Transaction txn = new Transaction();
		txn.setAmount(0.0);
		txn.setTransactionDate(LocalDate.now().minusDays(5));
		txn.setCustomer(customer);

		customer.setTransactions(List.of(txn));

		when(customerRepository.findById(2)).thenReturn(Optional.of(customer));

		InvalidTransactionAmountException exception = assertThrows(InvalidTransactionAmountException.class,
				() -> rewardService.getCustomerRewardById(2));
		assertEquals("Transaction amount must be greater than 0", exception.getMessage());
	}

	@Test
	void testGetCustomerRewardById_validTransactions() {
		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setName("TestUser");

		Transaction txn1 = new Transaction();
		txn1.setAmount(120.0);
		txn1.setTransactionDate(LocalDate.now().minusDays(10));
		txn1.setCustomer(customer);

		Transaction txn2 = new Transaction();
		txn2.setAmount(80.0);
		txn2.setTransactionDate(LocalDate.now().minusDays(20));
		txn2.setCustomer(customer);

		customer.setTransactions(List.of(txn1, txn2));
		when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

		var response = rewardService.getCustomerRewardById(1);
		assertEquals(1, response.getCustomerId());
		assertEquals("TestUser", response.getCustomerName());
		assertTrue(response.getMonthlyPoints().values().stream().allMatch(points -> points > 0));
		assertEquals(90 + 30, response.getTotalPoints()); // 120->90, 80->30
	}

	@Test
	void testGetCustomerRewardById_ignoresOldTransactions() {
		Customer customer = new Customer();
		customer.setCustomerId(3);
		customer.setName("OldTxnUser");

		Transaction txnOld = new Transaction();
		txnOld.setAmount(200.0);
		txnOld.setTransactionDate(LocalDate.now().minusMonths(4));
		txnOld.setCustomer(customer);

		customer.setTransactions(List.of(txnOld));
		when(customerRepository.findById(3)).thenReturn(Optional.of(customer));

		var response = rewardService.getCustomerRewardById(3);
		assertEquals(0, response.getTotalPoints());
		assertTrue(response.getMonthlyPoints().isEmpty());
	}

	@Test
	void testGetCustomerRewardById_noTransactions() {
		Customer customer = new Customer();
		customer.setCustomerId(4);
		customer.setName("NoTxnUser");
		customer.setTransactions(List.of());
		when(customerRepository.findById(4)).thenReturn(Optional.of(customer));

		var response = rewardService.getCustomerRewardById(4);
		assertEquals(0, response.getTotalPoints());
		assertTrue(response.getMonthlyPoints().isEmpty());
	}

	@Test
	void testCalculateCustomerRewards_multipleCustomers() {
		Customer customer1 = new Customer();
		customer1.setCustomerId(1);
		customer1.setName("User1");
		Transaction txn1 = new Transaction();
		txn1.setAmount(120.0);
		txn1.setTransactionDate(LocalDate.now().minusDays(5));
		txn1.setCustomer(customer1);
		customer1.setTransactions(List.of(txn1));

		Customer customer2 = new Customer();
		customer2.setCustomerId(2);
		customer2.setName("User2");
		Transaction txn2 = new Transaction();
		txn2.setAmount(80.0);
		txn2.setTransactionDate(LocalDate.now().minusDays(10));
		txn2.setCustomer(customer2);
		customer2.setTransactions(List.of(txn2));

		when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));
		when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));
		when(customerRepository.findById(2)).thenReturn(Optional.of(customer2));

		var responses = rewardService.calculateCustomerRewards();
		assertEquals(2, responses.size());
		assertTrue(responses.stream().anyMatch(r -> r.getCustomerId() == 1 && r.getTotalPoints() == 90));
		assertTrue(responses.stream().anyMatch(r -> r.getCustomerId() == 2 && r.getTotalPoints() == 30));
	}

	@Test
	void testCalculateCustomerRewards_noCustomers() {
		when(customerRepository.findAll()).thenReturn(List.of());
		var responses = rewardService.calculateCustomerRewards();
		assertTrue(responses.isEmpty());
	}
}
