package com.comarch.skodaapp.customer;

import com.comarch.skodaapp.common.LoyaltyPoints;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    @Test
    void customerInitialization() {
        var customer = new Customer("ronalno@example.com", "Ronaldo Luís Nazário de Lima");

        assertEquals("ronalno@example.com", customer.getEmail());
        assertEquals("Ronaldo Luís Nazário de Lima", customer.getName());
        Assertions.assertEquals(LoyaltyPoints.ZERO, customer.getPoints());
    }

    @Test
    void addPointsToCustomer() {
        var customer = new Customer("ronaldinho@example.com", "Ronaldinho Gaúcho");
        var initialPoints = new LoyaltyPoints(10);
        customer.addPoints(initialPoints);

        Assertions.assertEquals(new LoyaltyPoints(10), customer.getPoints());
    }

    @Test
    void addMultiplePointsToCustomer() {
        var customer = new Customer("adriano@example.com", "Adriano Leite Ribeiro");
        customer.addPoints(new LoyaltyPoints(10));
        customer.addPoints(new LoyaltyPoints(20));

        Assertions.assertEquals(new LoyaltyPoints(30), customer.getPoints());
    }
}