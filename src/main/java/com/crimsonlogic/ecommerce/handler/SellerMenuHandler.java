package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.service.AuthenticationService;
import com.crimsonlogic.ecommerce.service.ProductService;
import com.crimsonlogic.ecommerce.service.SellerService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles all Seller menu operations.
 */
public class SellerMenuHandler {

    private final SellerService sellerService;

    private final AuthenticationService authenticationService;

    private final ProductMenuHandler productMenuHandler;
    private final OrderMenuHandler orderMenuHandler;

    /**
     * Parameterized Constructor.
     *
     * @param sellerService Seller Service
     */
    public SellerMenuHandler(SellerService sellerService) {

        this.sellerService = sellerService;
        this.authenticationService = new AuthenticationService();
        this.productMenuHandler = new ProductMenuHandler(new ProductService());
        this.orderMenuHandler = new OrderMenuHandler();

    }

    /**
     * Displays Seller Dashboard.
     *
     * @param seller Logged-in Seller
     */
    public void showMenu(Seller seller) {

        boolean logout = false;

        while (!logout) {

            showDashboard(seller);

            String choice = InputUtil.readString("Enter Choice : ")
                    .trim()
                    .toLowerCase();

            switch (choice) {

                case "profile":

                    viewProfile(seller);
                    break;

                case "update":

                    sellerService.updateProfile(seller);
                    break;

                case "password":

                    sellerService.changePassword(seller);
                    break;

                case "products":

                    productMenuHandler.showMenu(seller);
                    break;
                case "orders":

                    orderMenuHandler.showSellerMenu(seller);

                    break;

                case "delete":

                    if (sellerService.deleteAccount(seller)) {

                        DisplayUtil.printSuccess(
                                "Seller Account Deleted Successfully.");

                        logout = true;

                    }

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
     * Displays Seller Dashboard.
     *
     * @param seller Logged-in Seller
     */
    private void showDashboard(Seller seller) {

        System.out.println("\n==========================================");
        System.out.println("              SELLER MENU");
        System.out.println("==========================================");
        System.out.println("Welcome : " + seller.getUserName());
        System.out.println("------------------------------------------");
        System.out.println("PROFILE");
        System.out.println("UPDATE");
        System.out.println("PASSWORD");
        System.out.println("PRODUCTS");
        System.out.println("ORDERS");
        System.out.println("DELETE");
        System.out.println("LOGOUT");
        System.out.println("==========================================");

    }

    /**
     * Displays Seller Profile.
     *
     * @param seller Logged-in Seller
     */
    private void viewProfile(Seller seller) {

        String[] headers = {
                "Field",
                "Value"
        };

        DisplayUtil.printTable(
                "SELLER PROFILE",
                headers,
                seller.getTableRows());

    }

}