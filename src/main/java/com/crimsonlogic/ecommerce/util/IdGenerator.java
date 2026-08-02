package com.crimsonlogic.ecommerce.util;

import java.util.Random;

/**
 * Utility class for generating unique IDs.
 */
public class IdGenerator {

    private static final Random RANDOM = new Random();

    /**
     * Private Constructor.
     */
    private IdGenerator() {

    }

    /**
     * Generates an ID using the given prefix.
     *
     * Examples:
     * CAT4832
     * PRO1098
     * CUS7854
     * SEL5631
     *
     * @param prefix ID Prefix
     * @return Generated ID
     */
    public static String generateId(String prefix) {

        int randomNumber = 1000 + RANDOM.nextInt(9000);

        return prefix.toUpperCase() + randomNumber;

    }

}