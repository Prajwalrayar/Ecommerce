package com.crimsonlogic.ecommerce.exceptionhandling.user;

/**
 * Thrown when user input validation fails.
 *
 * Examples:
 * - Invalid Email
 * - Invalid Password
 * - Invalid Phone Number
 * - Invalid Name
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }

}
