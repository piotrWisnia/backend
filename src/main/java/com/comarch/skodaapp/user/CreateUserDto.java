package com.comarch.skodaapp.user;

public record CreateUserDto(String name, String password, String email, int loyaltyPoints) {
}
