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

    private final AddressDAO addressDAO = new AddressDAO();

    // Updates Customer Profile for a Logged-in Customer
    @Override
    public void updateProfile(Customer customer) {

        // Update common details
        super.updateProfile(customer);

        while (true) {

            try {

                String houseNumber =
                        InputUtil.readOptionalString("Enter House Number: ");

                // Remove Address
                if (houseNumber == null) {

                    if (customer.getAddress() != null) {

                        // Optional
                        // addressDAO.deleteAddress(
                        //         customer.getAddress().getAddressId());

                    }

                    customer.setAddress(null);

                    customerDAO.updateCustomer(customer);

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

                Address address;

                // Existing address
                if (customer.getAddress() != null) {

                    address = customer.getAddress();

                    address.setHouseNumber(houseNumber);
                    address.setStreet(street);
                    address.setCity(city);
                    address.setState(state);
                    address.setCountry(country);
                    address.setZipCode(zipCode);

                    ValidationUtil.validateAddress(address);

                    addressDAO.updateAddress(address);

                }

                // New address
                else {

                    address = new Address(

                            IdGenerator.generateId("ADDR"),

                            houseNumber,

                            street,

                            city,

                            state,

                            country,

                            zipCode

                    );

                    ValidationUtil.validateAddress(address);

                    addressDAO.insertAddress(address);

                }

                customer.setAddress(address);

                customerDAO.updateCustomer(customer);

                break;

            }

            catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

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