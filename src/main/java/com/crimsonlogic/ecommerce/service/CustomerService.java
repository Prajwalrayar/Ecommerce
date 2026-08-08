package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.AddressDAO;
import com.crimsonlogic.ecommerce.dao.CustomerDAO;
import com.crimsonlogic.ecommerce.exceptionhandling.UserNotFoundException;
import com.crimsonlogic.ecommerce.exceptionhandling.ValidationException;
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
        while (true) {

            try {

                String houseNumber = InputUtil.readString("Enter House Number: ");

                ValidationUtil.validateField(houseNumber, "House Number");

                String street = InputUtil.readString("Enter Street: ");

                ValidationUtil.validateField(street, "Street");

                String city = InputUtil.readString("Enter City: ");

                ValidationUtil.validateLocationName(city,"City");

                String state = InputUtil.readString("Enter State: ");

                ValidationUtil.validateLocationName(state,"State");

                String country = InputUtil.readString("Enter Country: ");

                ValidationUtil.validateLocationName(country, "Country");

                String zipCode = InputUtil.readString("Enter Zip Code: ");

                ValidationUtil.validateZipCode(zipCode);

                Address address = new Address(IdGenerator.generateId("ADDR"),
                                houseNumber, street, city, state, country, zipCode);

                customer.setAddress(address);

                customerDAO.updateCustomer(customer);

                break;

            } catch (ValidationException exception) {
                DisplayUtil.printMessage(exception.getMessage());
            }
        }

        DisplayUtil.printSuccess("Customer Profile Updated Successfully.");
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