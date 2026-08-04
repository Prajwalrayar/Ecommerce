package com.crimsonlogic.ecommerce.exceptionhandling;

/**
 * Thrown when an invalid business
 * operation is attempted.
 */
public class InvalidOperationException extends Exception {

    // Parameterized Constructor.
    public InvalidOperationException(String message) {
        super(message);
    }

}
