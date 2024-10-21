package com.comarch.skodaapp.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class LoyaltyPointsTest {

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException for negative points")
    void constructorShouldThrowExceptionWhenPointsAreNegative() {
        assertThatThrownBy(() -> new LoyaltyPoints(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Points cannot be negative");
    }

    @Test
    @DisplayName("Constructor should not throw exception for zero or positive points")
    void constructorShouldNotThrowExceptionWhenPointsAreZeroOrPositive() {
        assertThatCode(() -> new LoyaltyPoints(0))
                .doesNotThrowAnyException();

        assertThatCode(() -> new LoyaltyPoints(10))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("fromMoney should correctly convert Money to LoyaltyPoints")
    void fromMoneyShouldCorrectlyConvertMoneyToLoyaltyPoints() {
        Money money = new Money(BigDecimal.valueOf(100));
        LoyaltyPoints expectedPoints = new LoyaltyPoints(100);

        assertThat(LoyaltyPoints.fromMoney(money)).isEqualTo(expectedPoints);
    }

    @Test
    @DisplayName("isGreaterOrEqualThan should correctly compare two LoyaltyPoints objects")
    void isGreaterOrEqualThanShouldCorrectlyCompareTwoLoyaltyPointsObjects() {
        LoyaltyPoints basePoints = new LoyaltyPoints(100);
        LoyaltyPoints greaterPoints = new LoyaltyPoints(150);
        LoyaltyPoints equalPoints = new LoyaltyPoints(100);

        assertThat(basePoints.isGreaterOrEqualThan(greaterPoints)).isFalse();
        assertThat(basePoints.isGreaterOrEqualThan(equalPoints)).isTrue();
        assertThat(greaterPoints.isGreaterOrEqualThan(basePoints)).isTrue();
    }

    @Test
    @DisplayName("add should correctly add two LoyaltyPoints objects")
    void addShouldCorrectlyAddTwoLoyaltyPointsObjects() {
        LoyaltyPoints points1 = new LoyaltyPoints(100);
        LoyaltyPoints points2 = new LoyaltyPoints(50);
        LoyaltyPoints expectedResult = new LoyaltyPoints(150);

        assertThat(points1.add(points2)).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("multiply should correctly multiply LoyaltyPoints by a multiplier")
    void multiplyShouldCorrectlyMultiplyLoyaltyPointsByMultiplier() {
        LoyaltyPoints points = new LoyaltyPoints(100);
        int multiplier = 2;
        LoyaltyPoints expectedResult = new LoyaltyPoints(200);

        assertThat(points.multiply(multiplier)).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("multiply should throw IllegalArgumentException for negative multiplier")
    void multiplyShouldThrowExceptionForNegativeMultiplier() {
        LoyaltyPoints points = new LoyaltyPoints(100);

        assertThatThrownBy(() -> points.multiply(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Multiplier cannot be negative");
    }

    @Test
    @DisplayName("doublePoints should correctly double the LoyaltyPoints")
    void doublePointsShouldCorrectlyDoubleTheLoyaltyPoints() {
        LoyaltyPoints points = new LoyaltyPoints(100);
        LoyaltyPoints expectedResult = new LoyaltyPoints(200);

        assertThat(points.doublePoints()).isEqualTo(expectedResult);
    }
}
