package com.crimsonlogic.ecommerce.exceptionhandling.user;


/**
 * Thrown when a requested user is not found.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
