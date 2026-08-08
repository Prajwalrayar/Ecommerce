package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.service.AuthenticationService;
import com.crimsonlogic.ecommerce.service.PaymentService;
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
    private final PaymentService paymentService;

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
        this.paymentService = new PaymentService();

    }

    /**
     * Displays Seller Dashboard.
     *
     * @param seller Logged-in Seller
     */
    public void showMenu(Seller seller) {

        boolean back = false;

        while (!back) {

            showDashboard(seller);

            String choice = InputUtil.readString("Enter Choice : ")
                    .trim().toLowerCase();

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
                case "payments":

                    showPaymentMenu(seller);

                    break;

                case "back":

                    back = true;
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
        System.out.println("PAYMENTS");
        System.out.println("BACK");
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

    /**
     * Displays Seller Payment Menu.
     *
     * @param seller Logged-in Seller
     */
    private void showPaymentMenu(Seller seller) {

        boolean back = false;

        while (!back) {

            System.out.println("\n==========================================");
            System.out.println("         SELLER PAYMENT MENU");
            System.out.println("==========================================");
            System.out.println("VIEW");
            System.out.println("SEARCH");
            System.out.println("BACK");
            System.out.println("==========================================");

            String choice =
                    InputUtil.readString(
                                    "Enter Choice : ")
                            .trim()
                            .toLowerCase();

            switch (choice) {

                case "view":

                    paymentService.viewSellerPayments(
                            seller);

                    break;

                case "search":

                    paymentService.searchSellerPayment(
                            seller);

                    break;

                case "back":

                    back = true;

                    break;

                default:

                    DisplayUtil.printInvalidChoice();

                    back = true;

            }

        }

    }

}