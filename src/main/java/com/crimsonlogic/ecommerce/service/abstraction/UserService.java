package com.crimsonlogic.ecommerce.service.abstraction;

import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.abstraction.User;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;
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

                DisplayUtil.printError(exception.getMessage());

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

                DisplayUtil.printError(exception.getMessage());

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

                DisplayUtil.printError(exception.getMessage());

            }

        }

    }

    /**
     * Changes the user's password.
     *
     * @param user Logged-in User
     */
    public void changePassword(T user) {

        System.out.println("\n========== CHANGE PASSWORD ==========");

        while (true) {

            String currentPassword =
                    InputUtil.readString("Enter Current Password : ");

            if (!user.getUserPassword().equals(currentPassword)) {

                DisplayUtil.printError(
                        "Current Password is incorrect.");

                continue;

            }

            try {

                String newPassword =
                        InputUtil.readString("Enter New Password : ");

                ValidationUtil.validatePassword(newPassword);

                String confirmPassword =
                        InputUtil.readString("Confirm Password : ");

                if (!newPassword.equals(confirmPassword)) {

                    DisplayUtil.printError(
                            "Passwords do not match.");

                    continue;

                }

                user.setUserPassword(newPassword);

                DisplayUtil.printSuccess(
                        "Password Changed Successfully.");

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printError(exception.getMessage());

            }

        }

    }

}