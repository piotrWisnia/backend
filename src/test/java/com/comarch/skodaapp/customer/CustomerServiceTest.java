package com.comarch.skodaapp.customer;

import com.comarch.skodaapp.common.LoyaltyPoints;
import com.comarch.skodaapp.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    void whenGetCustomerThenReturnsCustomerDto() {
        //given
        var customer = new Customer("test@example.com", "Test Customer");
        customer.addPoints(new LoyaltyPoints(50));

        customer = customerRepository.save(customer);

        //when
        var result = customerService.getCustomer(customer.getId());

        //then
        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("email", customer.getEmail())
                .hasFieldOrPropertyWithValue("name", customer.getName())
                .hasFieldOrPropertyWithValue("points", customer.getPoints().points());
    }

    @Test
    void whenAddCustomerThenCustomerIsAdded() {
        //given
        var newCustomer = new CreateCustomerDto("new@example.com", "New Customer");

        //when
        var customerId = customerService.addCustomer(newCustomer);

        //then
        assertThat(customerRepository.existsById(customerId)).isTrue();
    }

    @Test
    void whenAddPointsToCustomerThenPointsAreAdded() {
        //given
        var customer = new Customer("boczek@gmail.com", "Arnold Boczek");
        customer = customerRepository.save(customer);
        var pointsToAdd = new LoyaltyPoints(20);

        //when
        customerService.addPoints(customer.getId(), pointsToAdd);

        //then
        Assertions.assertThat(customerRepository.findById(customer.getId()).orElseThrow().getPoints().points()).isEqualTo(20);

        //when
        customerService.addPoints(customer.getId(), pointsToAdd);

        //then
        Assertions.assertThat(customerRepository.findById(customer.getId()).orElseThrow().getPoints().points()).isEqualTo(40);
    }
}