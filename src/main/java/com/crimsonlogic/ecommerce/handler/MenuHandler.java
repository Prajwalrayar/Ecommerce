package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.enums.Role;
import com.crimsonlogic.ecommerce.model.Admin;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.service.AdminService;
import com.crimsonlogic.ecommerce.service.AuthenticationService;
import com.crimsonlogic.ecommerce.service.CustomerService;
import com.crimsonlogic.ecommerce.service.SellerService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles the navigation of the E-Commerce Marketplace.
 */
public class MenuHandler {

    private final AuthenticationService authenticationService;

    private final AdminMenuHandler adminMenuHandler;

    private final SellerMenuHandler sellerMenuHandler;

    private final CustomerMenuHandler customerMenuHandler;

    /**
     * Default Constructor.
     */
    public MenuHandler() {

        authenticationService = new AuthenticationService();

        adminMenuHandler = new AdminMenuHandler(new AdminService());

        sellerMenuHandler = new SellerMenuHandler(new SellerService());

        customerMenuHandler = new CustomerMenuHandler(new CustomerService());

    }

    /**
     * Starts the Application.
     */
    public void mainMenu() {

        boolean running = true;

        while (running) {

            showMainMenu();

            String choice = InputUtil.readString("Enter Choice : ").trim().toLowerCase();

            switch (choice) {

                case "admin":
                case "a":

                    navigateAdmin();
                    break;

                case "seller":
                case "s":

                    navigateSeller();
                    break;

                case "customer":
                case "c":

                    navigateCustomer();
                    break;

                case "exit":
                case "e":

                    running = false;
                    exitApplication();
                    break;

                default:
                    DisplayUtil.printInvalidChoice();

            }

        }

    }

    /**
     * Displays Main Menu.
     */
    private void showMainMenu() {

        System.out.println("\n==========================================");
        System.out.println("        E-COMMERCE MARKETPLACE");
        System.out.println("==========================================");
        System.out.println("Admin     -> Login as Administrator");
        System.out.println("Seller    -> Register/Login as Seller");
        System.out.println("Customer  -> Register/Login as Customer");
        System.out.println("Exit      -> Exit Application");
        System.out.println("==========================================");

    }

    /**
     * Navigates to Admin Module.
     */
    private void navigateAdmin() {

        Admin admin = authenticationService.loginAdmin();

        if (admin != null && admin.getRole() == Role.ADMIN) {

            adminMenuHandler.showMenu(admin);

        }

    }

    /**
     * Navigates to Seller Module.
     */
    private void navigateSeller() {

        boolean back = false;

        while (!back) {

            System.out.println();
            System.out.println("                SELLER");
            System.out.println("-----------------------------------------");
            System.out.println("1.Login");
            System.out.println("2. Register");
            System.out.println("3. Back");
            System.out.println("-----------------------------------------");

            String choice = InputUtil.readString("Enter Choice : ").trim().toLowerCase();

            switch (choice) {

                case "login":

                    Seller seller = authenticationService.loginSeller();

                    if (seller != null) {

                        sellerMenuHandler.showMenu(seller);

                    }

                    break;

                case "register":

                    authenticationService.registerSeller();

                    break;

                case "back":

                    back = true;
                    break;

                default:

                    System.out.println("\nInvalid Choice! Please try again.\n");

            }

        }

    }

    /**
     * Navigates to Customer Module.
     */
    private void navigateCustomer() {

        boolean back = false;

        while (!back) {

            System.out.println();
            System.out.println("               CUSTOMER");
            System.out.println("-----------------------------------------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Back");
            System.out.println("-----------------------------------------");

            String choice = InputUtil.readString("Enter Choice : ").trim().toLowerCase();

            switch (choice) {

                case "login":
                case "l":

                    Customer customer = authenticationService.loginCustomer();

                    if (customer != null) {

                        customerMenuHandler.showMenu(customer);

                    }

                    break;

                case "register":
                case "r":

                    authenticationService.registerCustomer();

                    break;

                case "back":
                case "b":

                    back = true;
                    break;
                default:
                    System.out.println("\nInvalid Choice! Please try again.\n");
            }
        }
    }

    /**
     * Exits the Application.
     */
    private void exitApplication() {

        System.out.println();
        System.out.println("Thank you for using E-Commerce Marketplace.");
        System.out.println("Have a Nice Day!");
        System.out.println();

    }

}