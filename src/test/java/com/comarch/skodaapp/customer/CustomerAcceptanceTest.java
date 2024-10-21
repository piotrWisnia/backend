package com.comarch.skodaapp.customer;

import com.comarch.skodaapp.common.ErrorResponse;
import com.comarch.skodaapp.common.RestApiExceptionHandler;
import com.comarch.skodaapp.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class CustomerAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("""
            given existing Customer id,
            when request is sent,
            then return Customer details and HTTP 200 status""")
    void givenExistingCustomerId_whenRequestIsSent_thenCustomerDetailsReturnedAndHttp200() {
        //given
        var createCustomerDto = new CreateCustomerDto("ferdek@gemail.com", "Ferdzio");
        var customerId = customerService.addCustomer(createCustomerDto);

        //when
        ResponseEntity<CustomerDto> response = restTemplate.getForEntity(getBaseCustomersUrl() + "/" + customerId, CustomerDto.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("email", createCustomerDto.email())
                .hasFieldOrPropertyWithValue("name", createCustomerDto.name())
                .hasFieldOrPropertyWithValue("points", 0);
    }

    @Test
    @DisplayName("""
            given non-existing Customer id,
            when request is sent,
            then do not return Customer details and HTTP 404 status""")
    void givenNonExistingCustomerId_whenRequestIsSent_thenCustomerDetailsNotReturnedAndHttp404() {
        //given
        int notExistingId = 125;

        //when
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(getBaseCustomersUrl() + "/" + notExistingId, ErrorResponse.class);

        //then
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
            given request for creating Customer with all mandatory data correctly,
            when request is sent,
            then Customer is added and HTTP 201 status received""")
    void givenRequestForCreatingCustomer_whenRequestIsSent_thenCustomerAddedAndHttp201() {
        //given
        var createCustomerDto = new CreateCustomerDto("marek@gemail.com", "Mareczek");

        //when
        ResponseEntity<Void> postResponse = restTemplate.postForEntity(getBaseCustomersUrl(), createCustomerDto, Void.class);

        //then
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getHeaders().getLocation()).isNotNull();

        ResponseEntity<CustomerDto> getResponse = restTemplate.getForEntity(postResponse.getHeaders().getLocation(), CustomerDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("email", createCustomerDto.email())
                .hasFieldOrPropertyWithValue("name", createCustomerDto.name())
                .hasFieldOrPropertyWithValue("points", 0);
    }

    private String getBaseCustomersUrl() {
        return "http://localhost:" + port + "/customers";
    }
}