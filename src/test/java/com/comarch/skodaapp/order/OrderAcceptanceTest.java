package com.comarch.skodaapp.order;

import com.comarch.skodaapp.common.ErrorResponse;
import com.comarch.skodaapp.common.LoyaltyPoints;
import com.comarch.skodaapp.common.Money;
import com.comarch.skodaapp.common.RestApiExceptionHandler;
import com.comarch.skodaapp.customer.Customer;
import com.comarch.skodaapp.customer.CustomerRepository;
import com.comarch.skodaapp.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class OrderAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final static int DOUBLE_POINTS_THRESHOLD = 250;

    @DynamicPropertySource
    static void loyaltyPointsProperties(DynamicPropertyRegistry registry) {
        registry.add("loyalty.points.double.threshold", () -> DOUBLE_POINTS_THRESHOLD);
    }

    @AfterEach
    void cleanUp() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("""
            given request to add order for existing customer,
            when request is sent,
            then save order and HTTP 200 status received""")
    void givenRequestToAddOrderForExistingCustomer_whenRequestIsSent_thenOrderSavedAndHttp200() {
        // given
        var customerId = customerRepository.save(new Customer("waldek@gmail.com", "Waldek")).getId();

        var amount = new BigDecimal("15.67");
        var createOrderDto = new CreateOrderDto(customerId, amount);

        assertThat(orderRepository.findAll()).isEmpty();

        // when
        ResponseEntity<Void> response = restTemplate.postForEntity(getBaseOrdersUrl(), createOrderDto, Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var allOrders = orderRepository.findAll();
        assertThat(allOrders)
                .hasSize(1)
                .first()
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("customerId", customerId)
                .hasFieldOrPropertyWithValue("amount", new Money(amount));
    }

    @Test
    @DisplayName("""
            given request to add order for non-existent customer,
            when request is sent,
            then HTTP 404 status received""")
    void givenRequestToAddOrderForNonExistentCustomer_whenRequestIsSent_thenHttp404Received() {
        // given
        Long nonExistentCustomerId = 156L;
        var createOrderDto = new CreateOrderDto(nonExistentCustomerId, new BigDecimal("100.00"));

        // when
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(getBaseOrdersUrl(), createOrderDto, ErrorResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("code", RestApiExceptionHandler.NOT_FOUND_ERROR_CODE)
                .extracting("message")
                .asString()
                .contains("Could not find customer");
    }

    @Test
    @DisplayName("""
            given order is added,
            when processed,
            then add loyalty points to customer account""")
    void givenOrderAdded_whenProcessed_thenLoyaltyPointsAddedToCustomerAccount() {
        // given
        var customerId = customerRepository.save(new Customer("waldek@gmail.com", "Waldek")).getId();

        var amount = new BigDecimal("87.42");
        var createOrderDto = new CreateOrderDto(customerId, amount);

        // when
        ResponseEntity<Void> response = restTemplate.postForEntity(getBaseOrdersUrl(), createOrderDto, Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var customer = customerRepository.findById(customerId).orElseThrow();
        Assertions.assertThat(customer.getPoints()).isEqualTo(LoyaltyPoints.fromMoney(new Money(amount)));
    }

    private String getBaseOrdersUrl() {
        return "http://localhost:" + port + "/orders";
    }
}