package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.service.*;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles all Customer menu operations.
 */
public class CustomerMenuHandler {

    private final CustomerService customerService;

    private final AuthenticationService authenticationService;

    private final BrowseProductMenuHandler browseProductMenuHandler;

    private final CartMenuHandler cartMenuHandler;
    private final OrderMenuHandler orderMenuHandler;
    private final PaymentMenuHandler paymentMenuHandler;

    private ReviewService reviewService;

    /**
     * Parameterized Constructor.
     *
     * @param customerService Customer Service
     */
    public CustomerMenuHandler(CustomerService customerService) {

        this.customerService = customerService;

        this.authenticationService = new AuthenticationService();

        this.browseProductMenuHandler = new BrowseProductMenuHandler();

        this.cartMenuHandler = new CartMenuHandler(new CartService());

        this.orderMenuHandler = new OrderMenuHandler();

        this.paymentMenuHandler = new PaymentMenuHandler();
        this.reviewService = new ReviewService();
    }

    /**
     * Displays Customer Dashboard.
     *
     * @param customer Logged-in Customer
     */
    public void showMenu(Customer customer) {

        boolean logout = false;

        while (!logout) {

            showDashboard(customer);

            String choice = InputUtil.readString("Enter Choice : ")
                    .trim()
                    .toLowerCase();

            switch (choice) {

                case "profile":

                    viewProfile(customer);
                    break;

                case "update":

                    customerService.updateProfile(customer);
                    break;

                case "password":

                    customerService.changePassword(customer);
                    break;

                case "browse products":

                    browseProductMenuHandler.showMenu(customer);
                    break;

                case "cart":

                    cartMenuHandler.showMenu(customer);
                    break;
                case "orders":

                    orderMenuHandler.showMenu(customer);

                    break;
                case "payments":

                    paymentMenuHandler.showMenu(customer);

                    break;

                case "wallet":
                    showWallet(customer);

                    break;
                case "review":

                    reviewService.addReview(customer);

                    break;

                case "logout":

                    authenticationService.logout();

                    logout = true;

                    break;

                default:

                    DisplayUtil.printInvalidChoice();

            }

        }

    }

    /**
     * Displays Customer Dashboard.
     *
     * @param customer Logged-in Customer
     */
    private void showDashboard(Customer customer) {

        System.out.println("\n==========================================");
        System.out.println("             CUSTOMER MENU");
        System.out.println("==========================================");
        System.out.println("Welcome : " + customer.getUserName());
        System.out.println("------------------------------------------");
        System.out.println("PROFILE");
        System.out.println("UPDATE");
        System.out.println("PASSWORD");
        System.out.println("BROWSE PRODUCTS");
        System.out.println("CART");
        System.out.println("ORDERS");
        System.out.println("WALLET");
        System.out.println("REVIEW");
        System.out.println("PAYMENTS");
        System.out.println("LOGOUT");
        System.out.println("==========================================");

    }

    /**
     * Displays Customer Profile.
     *
     * @param customer Logged-in Customer
     */
    private void viewProfile(Customer customer) {

        String[] headers = {
                "Field",
                "Value"
        };

        DisplayUtil.printTable(
                "CUSTOMER PROFILE",
                headers,
                customer.getTableRows());

    }

    /**
     * Displays Customer Wallet Balance.
     *
     * @param customer Logged-in Customer
     */
    private void showWallet(Customer customer) {

        System.out.println("\n-----------------------------------------");
        System.out.println("     CUSTOMER WALLET");
        System.out.println("-------------------------------------------");

        System.out.println("Wallet Balance : ₹"
                + String.format("%.2f", customer.getWalletBalance()));

    }

}