package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.CustomerDAO;
import com.crimsonlogic.ecommerce.exceptionhandling.user.UserNotFoundException;
import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.Address;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.service.abstraction.UserService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

/**
 * Service class responsible for Customer operations.
 */
public class CustomerService extends UserService<Customer> {

    private final CustomerDAO customerDAO = new CustomerDAO();

    // Updates Customer Profile for a Logged-in Customer
    @Override
    public void updateProfile(Customer customer) {

        // Update common details
        super.updateProfile(customer);

        System.out.println("\n========== UPDATE ADDRESS ==========");

        while (true) {

            try {
                String houseNumber = InputUtil.readOptionalString("Enter House Number: ");
                if (houseNumber == null) {
                    customer.setAddress(null);
                    customerDAO.updateCustomer(customer);
                    break;
                }
                String street = InputUtil.readString("Enter Street: ");

                String city = InputUtil.readString("Enter City: ");

                String state = InputUtil.readString("Enter State: ");

                String country = InputUtil.readString("Enter Country: ");

                String zipCode = InputUtil.readString("Enter Zip Code: ");

                Address address = new Address(IdGenerator.generateId("ADDR"),
                        houseNumber, street, city, state, country, zipCode);

                ValidationUtil.validateAddress(address);

                customer.setAddress(address);

                customerDAO.updateCustomer(customer);

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printError(exception.getMessage());

            }

        }

        DisplayUtil.printSuccess(
                "Customer Profile Updated Successfully.");

    }

    // Deletes Customer Account.
    public boolean deleteAccount(Customer customer) {

        try {
            if (customerDAO.findCustomerById(customer.getUserId()) == null) {
                throw new UserNotFoundException("Customer Account Not Found.");
            }
            customerDAO.deleteCustomer(customer.getUserId());
            return true;
        } catch (UserNotFoundException exception) {
            DisplayUtil.printMessage(exception.getMessage());
            return false;
        }
    }
}