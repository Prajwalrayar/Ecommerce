package com.crimsonlogic.ecommerce.exceptionhandling;

/**
 * Thrown when login credentials
 * are incorrect.
 */
public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
