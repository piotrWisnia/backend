package com.comarch.skodaapp.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class MoneyTest {

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException for null amount")
    void constructorShouldThrowExceptionWhenAmountIsNull() {
        assertThatThrownBy(() -> new Money(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount cannot be null");
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException for negative amount")
    void constructorShouldThrowExceptionWhenAmountIsNegative() {
        var negativeAmount = new BigDecimal("-1");
        assertThatThrownBy(() -> new Money(negativeAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount cannot be negative");
    }

    @Test
    @DisplayName("Constructor should not throw exception for zero amount")
    void constructorShouldNotThrowExceptionWhenAmountIsZero() {
        assertThatCode(() -> new Money(BigDecimal.ZERO))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Constructor should not throw exception for positive amount")
    void constructorShouldNotThrowExceptionWhenAmountIsPositive() {
        assertThatCode(() -> new Money(new BigDecimal("10")))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Add should correctly add two Money amounts")
    void addShouldCorrectlyAddTwoMoneyAmounts() {
        Money money1 = new Money(new BigDecimal("10.00"));
        Money money2 = new Money(new BigDecimal("20.00"));
        Money result = money1.add(money2);

        assertThat(result.amount()).isEqualByComparingTo("30.00");
    }

    @Test
    @DisplayName("Money amounts should have correct scale and rounding")
    void moneyShouldHaveCorrectScaleAndRounding() {
        Money money = new Money(new BigDecimal("10.005"));

        assertThat(money.amount()).isEqualByComparingTo("10.00");
    }
}
