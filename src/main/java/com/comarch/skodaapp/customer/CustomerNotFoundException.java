package com.comarch.skodaapp.customer;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(final Long id) {
        super("Could not find customer with customerId: " + id);
    }
}
