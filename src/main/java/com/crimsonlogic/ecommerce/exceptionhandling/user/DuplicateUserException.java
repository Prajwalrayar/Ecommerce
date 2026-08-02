package com.crimsonlogic.ecommerce.exceptionhandling.user;

/**
 * Thrown when a user tries to register
 * with an email or phone number that
 * already exists.
 */
public class DuplicateUserException extends Exception {

    public DuplicateUserException(String message) {
        super(message);
    }

}
