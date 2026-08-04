package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.CustomerDAO;
import com.crimsonlogic.ecommerce.dao.SellerDAO;
import com.crimsonlogic.ecommerce.exceptionhandling.UserNotFoundException;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import java.util.List;
/**
 * Service class responsible for Admin operations.
 */
public class AdminService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    private final SellerDAO sellerDAO = new SellerDAO();

    private static final String[] CUSTOMER_HEADERS = {

            "Customer ID",

            "Name",

            "Email",

            "Phone Number"

    };

    private static final String[] SELLER_HEADERS = {
            "Seller ID",
            "Name",
            "Email",
            "Phone Number",
            "Shop Name"
    };

    /**
     * Displays all Customers.
     */
    public void viewAllCustomers() {

        if (!validateCustomers()) {

            return;

        }

        displayCustomers(

                customerDAO.findAllCustomers(),

                "CUSTOMERS"

        );

    }

    /**
     * Displays all Sellers.
     */
    public void viewAllSellers() {

        if (!validateSellers()) {

            return;

        }

        displaySellers(

                sellerDAO.findAllSellers(),

                "SELLERS"

        );

    }

    /**
     * Deletes Customer.
     *
     * @param customerId Customer ID
     */
    public void deleteCustomer(
            String customerId) {

        try {

            Customer customer =
                    customerDAO.findCustomerById(
                            customerId);

            if (customer == null) {

                throw new UserNotFoundException(
                        "Customer Not Found.");

            }

            customerDAO.deleteCustomer(
                    customerId);

            DisplayUtil.printSuccess(
                    "Customer Deleted Successfully.");

        } catch (UserNotFoundException exception) {

            DisplayUtil.printMessage(
                    exception.getMessage());

        }

    }

    /**
     * Deletes Seller.
     *
     * @param sellerId Seller ID
     */
    public void deleteSeller(
            String sellerId) {

        try {

            Seller seller =
                    sellerDAO.findSellerById(
                            sellerId);

            if (seller == null) {

                throw new UserNotFoundException(
                        "Seller Not Found.");

            }

            sellerDAO.deleteSeller(
                    sellerId);

            DisplayUtil.printSuccess(
                    "Seller Deleted Successfully.");

        } catch (UserNotFoundException exception) {

            DisplayUtil.printMessage(
                    exception.getMessage());

        }

    }

    /**
     * Validates Customers.
     *
     * @return true if customers exist
     */
    private boolean validateCustomers() {

        if (customerDAO.findAllCustomers().isEmpty()) {

            DisplayUtil.printMessage(
                    "No Customers Found.");

            return false;

        }

        return true;

    }

    /**
     * Validates Sellers.
     *
     * @return true if sellers exist
     */
    private boolean validateSellers() {

        if (sellerDAO.findAllSellers().isEmpty()) {

            DisplayUtil.printMessage(
                    "No Sellers Found.");

            return false;

        }

        return true;

    }

    /**
     * Displays Customers.
     *
     * @param customers Customers
     * @param title Table Title
     */
    private void displayCustomers(
            List<Customer> customers,
            String title) {

        DisplayUtil.printTable(

                title,

                CUSTOMER_HEADERS,

                buildCustomerRows(
                        customers)

        );

    }

    /**
     * Displays Sellers.
     *
     * @param sellers Sellers
     * @param title Table Title
     */
    private void displaySellers(
            List<Seller> sellers,
            String title) {

        DisplayUtil.printTable(

                title,

                SELLER_HEADERS,

                buildSellerRows(
                        sellers)

        );

    }

    /**
     * Builds Customer Rows.
     *
     * @param customers Customers
     * @return Table Rows
     */
    private List<String[]> buildCustomerRows(
            List<Customer> customers) {

        return customers.stream()

                .map(customer -> new String[]{

                        customer.getUserId(),

                        customer.getUserName(),

                        customer.getUserEmail(),

                        customer.getUserPhNo()

                })

                .toList();

    }

    /**
     * Builds Seller Rows.
     *
     * @param sellers Sellers
     * @return Table Rows
     */
    private List<String[]> buildSellerRows(
            List<Seller> sellers) {

        return sellers.stream()

                .map(seller -> new String[]{

                        seller.getUserId(),

                        seller.getUserName(),

                        seller.getUserEmail(),

                        seller.getUserPhNo(),

                        seller.getShopName()

                })

                .toList();

    }

}