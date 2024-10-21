package com.comarch.skodaapp.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

record CreateOrderDto(
        @NotNull Long customerId,
        @NotNull @Min(1) BigDecimal amount) {
}
