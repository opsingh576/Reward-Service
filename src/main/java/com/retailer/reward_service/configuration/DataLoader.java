package com.retailer.reward_service.configuration;

import com.retailer.reward_service.entity.Customer;
import com.retailer.reward_service.entity.Transaction;
import com.retailer.reward_service.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;


/**
 * Initializes and loads sample customer and transaction data at application startup.
 * This data is used to demonstrate reward calculation logic.
 * customerRepository Repository for saving customer data along with associated transactions
 * CommandLineRunner that performs the data loading logic
 **/
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(CustomerRepository customerRepository) {
        return args -> {
            Customer alice = Customer.builder()
                    .name("Alice")
                    .build();

            Customer bob = Customer.builder()
                    .name("Bob")
                    .build();

            Customer jon = Customer.builder()
                    .name("Jon")
                    .build();

            Customer jack = Customer.builder()
                    .name("Jack")
                    .build();

            Transaction t1 = new Transaction(alice, 120.0, LocalDate.now().minusDays(10));
            Transaction t2 = new Transaction( alice, 75.0, LocalDate.now().minusDays(20));

            Transaction t3 = new Transaction(bob, 200.0, LocalDate.now().minusDays(5));
            Transaction t4 = new Transaction(bob, 30.0, LocalDate.now().minusDays(2));

            Transaction t5 = new Transaction(jon,  120.0, LocalDate.now().minusDays(0));
            Transaction t6 = new Transaction(jon, 1.0, LocalDate.now().minusDays(31));

            Transaction t7 = new Transaction(jack, 170.0, LocalDate.now().minusDays(90));
            Transaction t8 = new Transaction(jack, 50.0, LocalDate.now().minusDays(60));
            Transaction t9 = new Transaction(jack, 75.0, LocalDate.now().minusDays(30));

            alice.setTransactions(List.of(t1, t2));
            bob.setTransactions(List.of(t3, t4));
            jon.setTransactions(List.of(t5,t6));
            jack.setTransactions(List.of(t7,t8,t9));
            customerRepository.saveAll(List.of(alice,bob,jon,jack));
        };
    }
}

