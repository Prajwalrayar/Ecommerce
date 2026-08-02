package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.exceptionhandling.user.UserNotFoundException;
import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.Address;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.repository.DataStore;
import com.crimsonlogic.ecommerce.service.abstraction.UserService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

/**
 * Service class responsible for Customer operations.
 */
public class CustomerService extends UserService<Customer> {

    /**
     * Updates Customer Profile.
     *
     * @param customer Logged-in Customer
     */
    @Override
    public void updateProfile(Customer customer) {

        // Update common details
        super.updateProfile(customer);

        System.out.println("\n========== UPDATE ADDRESS ==========");

        while (true) {

            try {

                String houseNumber = InputUtil.readOptionalString(
                        "Enter House Number (Press Enter to Skip): ");

                if (houseNumber == null) {

                    customer.setAddress(null);
                    break;

                }

                String street =
                        InputUtil.readString("Enter Street: ");

                String city =
                        InputUtil.readString("Enter City: ");

                String state =
                        InputUtil.readString("Enter State: ");

                String country =
                        InputUtil.readString("Enter Country: ");

                String zipCode =
                        InputUtil.readString("Enter Zip Code: ");

                Address address = new Address(
                        houseNumber,
                        street,
                        city,
                        state,
                        country,
                        zipCode);

                ValidationUtil.validateAddress(address);

                customer.setAddress(address);

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printError(exception.getMessage());

            }

        }

        DisplayUtil.printSuccess(
                "Customer Profile Updated Successfully.");

    }

    /**
     * Deletes Customer Account.
     *
     * @param customer Logged-in Customer
     * @return true if deleted successfully, otherwise false
     */
    public boolean deleteAccount(Customer customer) {

        try {

            Customer deletedCustomer =
                    DataStore.CUSTOMERS.remove(customer.getUserId());

            if (deletedCustomer == null) {

                throw new UserNotFoundException(
                        "Customer Account Not Found.");

            }

            return true;

        } catch (UserNotFoundException exception) {

            DisplayUtil.printMessage(exception.getMessage());

            return false;

        }

    }

}