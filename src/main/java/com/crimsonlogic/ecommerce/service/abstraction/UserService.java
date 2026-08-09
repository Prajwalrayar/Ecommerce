package com.crimsonlogic.ecommerce.service.abstraction;

import com.crimsonlogic.ecommerce.exceptionhandling.ValidationException;
import com.crimsonlogic.ecommerce.model.abstraction.User;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.PasswordUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

/**
 * Generic abstract service containing common
 * operations shared by all users.
 */
public abstract class UserService<T extends User> {

    /**
     * Displays the profile of the logged-in user.
     *
     * @param user Logged-in User
     */
    public void viewProfile(T user) {

        System.out.println();
        System.out.println("========== PROFILE ==========");
        System.out.println(user);

    }

    /**
     * Updates common profile details.
     *
     * @param user Logged-in User
     */
    public void updateProfile(T user) {

        System.out.println("\n========== UPDATE PROFILE ==========");

        while (true) {

            try {

                String userName =
                        InputUtil.readString("Enter Name: ");

                ValidationUtil.validateUserName(userName);

                user.setUserName(userName);

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(exception.getMessage());

            }

        }

        while (true) {

            try {

                String userEmail =
                        InputUtil.readString("Enter Email: ");

                ValidationUtil.validateEmail(userEmail);

                user.setUserEmail(userEmail);

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(exception.getMessage());

            }

        }

        while (true) {

            try {

                String userPhone =
                        InputUtil.readString("Enter Phone Number: ");

                ValidationUtil.validatePhone(userPhone);

                user.setUserPhNo(userPhone);

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(exception.getMessage());

            }

        }

    }

    /**
     * Changes the user's password.
     *
     * @param user Logged-in User
     */
    public String changePassword(T user) {

        System.out.println("\n========== CHANGE PASSWORD ==========");

        String currentPassword =
                InputUtil.readString(
                        "Enter Current Password : ");

        if (!PasswordUtil.verifyPassword(
                currentPassword,
                user.getUserPassword())) {

            DisplayUtil.printMessage(
                    "Current Password is incorrect.");

            return null;
        }

        try {

            String newPassword =
                    InputUtil.readString(
                            "Enter New Password : ");

            ValidationUtil.validatePassword(
                    newPassword);

            // New password cannot be same as current password
            if (PasswordUtil.verifyPassword(
                    newPassword,
                    user.getUserPassword())) {

                DisplayUtil.printMessage(
                        "New Password cannot be the same as Current Password.");

                return null;
            }

            String confirmPassword =
                    InputUtil.readString(
                            "Confirm Password : ");

            // Wrong confirmation -> return to menu
            if (!newPassword.equals(confirmPassword)) {

                DisplayUtil.printMessage(
                        "Passwords do not match.");

                return null;
            }

            String encryptedPassword =
                    PasswordUtil.encryptPassword(
                            newPassword);

            user.setUserPassword(
                    encryptedPassword);

            return encryptedPassword;

        } catch (ValidationException exception) {

            DisplayUtil.printMessage(
                    exception.getMessage());

            return null;
        }
    }

}