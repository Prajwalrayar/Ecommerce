package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Admin;
import com.crimsonlogic.ecommerce.service.AdminService;
import com.crimsonlogic.ecommerce.service.CategoryService;
import com.crimsonlogic.ecommerce.service.ProductService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles all Admin menu operations.
 */
public class AdminMenuHandler {

    private final AdminService adminService;

    private final CategoryMenuHandler categoryMenuHandler;

    private final ProductMenuHandler productMenuHandler;
    private final OrderMenuHandler orderMenuHandler;

    /**
     * Parameterized Constructor.
     *
     * @param adminService Admin Service
     */
    public AdminMenuHandler(AdminService adminService) {
        this.adminService = adminService;
        this.categoryMenuHandler = new CategoryMenuHandler(new CategoryService());
        this.productMenuHandler = new ProductMenuHandler(new ProductService());
        this.orderMenuHandler = new OrderMenuHandler();
    }

    /**
     * Displays the Admin Dashboard.
     *
     * @param admin Logged-in Admin
     */
    public void showMenu(Admin admin) {

        boolean logout = false;

        while (!logout) {

            showDashboard(admin);

            String choice = InputUtil.readString("Enter Choice : ")
                    .trim()
                    .toLowerCase();

            switch (choice) {

                case "profile":

                    viewProfile(admin);
                    break;

                case "customers":

                    adminService.viewAllCustomers();
                    break;

                case "sellers":

                    adminService.viewAllSellers();
                    break;

                case "categories":

                    categoryMenuHandler.showMenu();
                    break;

                case "products":

                    productMenuHandler.showMenu();
                    break;
                case "orders":

                    orderMenuHandler.showMenu();

                    break;

                case "delete customer":

                    deleteCustomer();
                    break;

                case "delete seller":

                    deleteSeller();
                    break;

                case "logout":

                    DisplayUtil.printSuccess(
                            "Admin Logged Out Successfully.");

                    logout = true;
                    break;

                default:

                    DisplayUtil.printInvalidChoice();

            }

        }

    }

    /**
     * Displays Admin Dashboard.
     *
     * @param admin Logged-in Admin
     */
    private void showDashboard(Admin admin) {

        System.out.println("\n==========================================");
        System.out.println("                ADMIN MENU");
        System.out.println("==========================================");
        System.out.println("Welcome : " + admin.getUserName());
        System.out.println("------------------------------------------");
        System.out.println("PROFILE");
        System.out.println("CUSTOMERS");
        System.out.println("SELLERS");
        System.out.println("CATEGORIES");
        System.out.println("PRODUCTS");
        System.out.println("ORDERS");
        System.out.println("DELETE CUSTOMER");
        System.out.println("DELETE SELLER");
        System.out.println("LOGOUT");
        System.out.println("==========================================");

    }

    /**
     * Displays Admin Profile.
     *
     * @param admin Logged-in Admin
     */
    private void viewProfile(Admin admin) {

        String[] headers = {
                "Field",
                "Value"
        };

        DisplayUtil.printTable(
                "ADMIN PROFILE",
                headers,
                admin.getTableRows());

    }

    /**
     * Deletes a Customer.
     */
    private void deleteCustomer() {

        String customerId =
                InputUtil.readString(
                        "Enter Customer ID : ");

        adminService.deleteCustomer(customerId);

    }

    /**
     * Deletes a Seller.
     */
    private void deleteSeller() {

        String sellerId =
                InputUtil.readString(
                        "Enter Seller ID : ");

        adminService.deleteSeller(sellerId);

    }

}