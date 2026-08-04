package com.crimsonlogic.ecommerce.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for Password Encryption.
 */
public final class PasswordUtil {

    private PasswordUtil() {

    }

    /**
     * Encrypt Password.
     *
     * @param password Plain Password
     * @return BCrypt Hash
     */
    public static String encryptPassword(
            String password) {

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt());

    }

    /**
     * Verify Password.
     *
     * @param password Plain Password
     * @param hashedPassword Stored Password
     * @return true if matched
     */
    public static boolean verifyPassword(
            String password,
            String hashedPassword) {

        return BCrypt.checkpw(
                password,
                hashedPassword);

    }

}