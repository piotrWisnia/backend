package com.comarch.skodaapp.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final Long id) {
        super("Could not find user with userId: " + id);
    }
}
