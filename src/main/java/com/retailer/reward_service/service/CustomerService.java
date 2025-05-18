package com.retailer.reward_service.service;

import com.retailer.reward_service.dtos.CustomerDetailsResponse;
import com.retailer.reward_service.entities.Customer;
import com.retailer.reward_service.exceptions.CustomerNotFoundException;
import com.retailer.reward_service.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing customer-related operations.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Retrieves a customer by their unique ID.
     * id the ID of the customer
     * return the Customer if found
     *  ResourceNotFoundException if no customer exists with the given ID
     */
    public CustomerDetailsResponse getCustomerDetailsById(Integer customerId) {

       Customer customer= customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

       return CustomerDetailsResponse.builder()
               .customerId(customer.getCustomerId())
               .name(customer.getName())
               .transactions(customer.getTransactions())
               .build();
    }

    /**
     * Retrieves all customers from the repository.
     *
     * @return List of all customers
     */
    public List<Customer> getAllCustomerDetails() {
        return customerRepository.findAll();
    }

}
