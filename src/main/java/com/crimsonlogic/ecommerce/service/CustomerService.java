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
                        InputUtil.readString(
                                "Enter House Number: ");

                ValidationUtil.validateField(
                        houseNumber,
                        "House Number");

                String street =
                        InputUtil.readString(
                                "Enter Street: ");

                ValidationUtil.validateField(
                        street,
                        "Street");

                String city =
                        InputUtil.readString(
                                "Enter City: ");

                ValidationUtil.validateLocationName(
                        city,
                        "City");

                String state =
                        InputUtil.readString(
                                "Enter State: ");

                ValidationUtil.validateLocationName(
                        state,
                        "State");

                String country =
                        InputUtil.readString(
                                "Enter Country: ");

                ValidationUtil.validateLocationName(
                        country,
                        "Country");

                String zipCode =
                        InputUtil.readString(
                                "Enter Zip Code: ");

                ValidationUtil.validateZipCode(
                        zipCode);

                Address address;

                /*
                 * If customer already has an address,
                 * update the existing address.
                 */
                if (customer.getAddress() != null) {

                    address =
                            customer.getAddress();

                    address.setHouseNumber(
                            houseNumber);

                    address.setStreet(
                            street);

                    address.setCity(
                            city);

                    address.setState(
                            state);

                    address.setCountry(
                            country);

                    address.setZipCode(
                            zipCode);

                    addressDAO.updateAddress(
                            address);

                }

                /*
                 * If customer does not have an address,
                 * create and insert a new address.
                 */
                else {

                    address =
                            new Address(
                                    IdGenerator.generateId(
                                            "ADDR"),
                                    houseNumber,
                                    street,
                                    city,
                                    state,
                                    country,
                                    zipCode);

                    addressDAO.insertAddress(
                            address);

                    customer.setAddress(
                            address);
                }

                /*
                 * Address now exists in the address table,
                 * so customers.address_id can safely reference it.
                 */
                customerDAO.updateCustomer(
                        customer);

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());
            }
        }

        DisplayUtil.printSuccess(
                "Customer Profile Updated Successfully.");
    }

}