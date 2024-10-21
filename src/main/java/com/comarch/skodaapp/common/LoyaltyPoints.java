package com.comarch.skodaapp.common;

import jakarta.persistence.Embeddable;

@Embeddable
public record LoyaltyPoints(int points) {

    public static final LoyaltyPoints ZERO = new LoyaltyPoints(0);

    public LoyaltyPoints {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
    }

    public static LoyaltyPoints fromMoney(Money money) {
        int points = money.amount().intValue();
        return new LoyaltyPoints(points);
    }

    public boolean isGreaterOrEqualThan(LoyaltyPoints otherPoints) {
        return this.points >= otherPoints.points();
    }

    public LoyaltyPoints add(LoyaltyPoints otherPoints) {
        return new LoyaltyPoints(this.points + otherPoints.points);
    }

    public LoyaltyPoints multiply(int multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("Multiplier cannot be negative");
        }
        return new LoyaltyPoints(this.points * multiplier);
    }

    public LoyaltyPoints doublePoints() {
        return this.multiply(2);
    }
}
