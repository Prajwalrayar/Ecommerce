package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.exceptionhandling.user.UserNotFoundException;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.repository.DataStore;
import com.crimsonlogic.ecommerce.util.DisplayUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for Admin operations.
 */
public class AdminService {

    /**
     * Displays all registered Customers.
     */
    public void viewAllCustomers() {

        if (DataStore.CUSTOMERS.isEmpty()) {

            DisplayUtil.printWarning("No Customers Found.");

            return;

        }

        String[] headers = {
                "Customer ID",
                "Name",
                "Email",
                "Phone Number"
        };

        List<String[]> rows = DataStore.CUSTOMERS.values()
                .stream()
                .map(customer -> new String[]{
                        customer.getUserId(),
                        customer.getUserName(),
                        customer.getUserEmail(),
                        customer.getUserPhNo()
                })
                .collect(Collectors.toList());

        DisplayUtil.printTable(
                "CUSTOMERS",
                headers,
                rows);

    }

    /**
     * Displays all registered Sellers.
     */
    public void viewAllSellers() {

        if (DataStore.SELLERS.isEmpty()) {

            DisplayUtil.printWarning("No Sellers Found.");

            return;

        }

        String[] headers = {
                "Seller ID",
                "Name",
                "Email",
                "Phone Number",
                "Shop Name"
        };

        List<String[]> rows = DataStore.SELLERS.values()
                .stream()
                .map(seller -> new String[]{
                        seller.getUserId(),
                        seller.getUserName(),
                        seller.getUserEmail(),
                        seller.getUserPhNo(),
                        seller.getShopName()
                })
                .collect(Collectors.toList());

        DisplayUtil.printTable(
                "SELLERS",
                headers,
                rows);

    }

    /**
     * Deletes a Customer.
     *
     * @param customerId Customer ID
     */
    public void deleteCustomer(String customerId) {

        try {

            Customer customer =
                    DataStore.CUSTOMERS.remove(customerId);

            if (customer == null) {

                throw new UserNotFoundException(
                        "Customer Not Found.");

            }

            DisplayUtil.printSuccess(
                    "Customer Deleted Successfully.");

        } catch (UserNotFoundException exception) {

            DisplayUtil.printError(
                    exception.getMessage());

        }

    }

    /**
     * Deletes a Seller.
     *
     * @param sellerId Seller ID
     */
    public void deleteSeller(String sellerId) {

        try {

            Seller seller =
                    DataStore.SELLERS.remove(sellerId);

            if (seller == null) {

                throw new UserNotFoundException(
                        "Seller Not Found.");

            }

            DisplayUtil.printSuccess(
                    "Seller Deleted Successfully.");

        } catch (UserNotFoundException exception) {

            DisplayUtil.printError(
                    exception.getMessage());

        }

    }

}