package com.comarch.skodaapp.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @PositiveOrZero
    private int loyaltyPoints;

    protected User(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
        this.loyaltyPoints = 0;
    }

    protected User() {

    }
}
