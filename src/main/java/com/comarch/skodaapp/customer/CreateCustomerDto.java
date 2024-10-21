package com.comarch.skodaapp.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

record CreateCustomerDto(
        @Email @NotBlank String email,
        @NotBlank String name) {
}
