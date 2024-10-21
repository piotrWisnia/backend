package com.comarch.skodaapp.customer;

import com.comarch.skodaapp.common.LoyaltyPoints;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Table(name = "customers")
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    private LoyaltyPoints points;

    @Version
    private int version;

    protected Customer() {
    }

    public Customer(String email, String name) {
        this.email = email;
        this.name = name;
        this.points = LoyaltyPoints.ZERO;
    }

    public void addPoints(LoyaltyPoints pointsToAdd) {
        this.points = this.points.add(pointsToAdd);
    }
}
