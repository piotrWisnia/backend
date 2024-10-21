package com.comarch.skodaapp.customer;

import com.comarch.skodaapp.common.LoyaltyPoints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto getCustomer(Long id) {
        return customerRepository.findById(id)
                .map(customer -> new CustomerDto(customer.getEmail(), customer.getName(), customer.getPoints().points()))
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Long addCustomer(CreateCustomerDto customerDto) {
        var customer = new Customer(customerDto.email(), customerDto.name());
        return customerRepository.save(customer).getId();
    }

    @Transactional
    public void addPoints(Long customerId, LoyaltyPoints pointsToAdd) {
        var customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        customer.addPoints(pointsToAdd);
    }
}
