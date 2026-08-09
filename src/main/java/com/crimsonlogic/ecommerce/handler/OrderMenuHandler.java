package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.service.AuthenticationService;
import com.crimsonlogic.ecommerce.service.OrderService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles Order Menu operations.
 */
public class OrderMenuHandler {

    /**
     * Order Service.
     */
    private final OrderService orderService;
    /**
     * Default Constructor.
     */
    public OrderMenuHandler() {

        this.orderService = new OrderService();
    }

    // Displays Customer Order Dashboard.

    public void showMenu(Customer customer) {

        boolean back = false;

        while (!back) {

            showDashboard();

            String choice = InputUtil.readString("Enter Choice : ")
                            .trim().toLowerCase();

            switch (choice) {

                case "place order":

                    orderService.placeOrder(customer);

                    break;

                case "view order":

                    orderService.viewOrders(customer);

                    break;

                case "search order":

                    orderService.searchOrder(customer);

                    break;

                case "cancel order":

                    orderService.cancelOrder(customer);

                    break;
                case "return":

                    orderService.returnOrder(customer);

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
     * Displays Seller Order Dashboard.
     *
     * @param seller Logged-in Seller
     */
    public void showSellerMenu(Seller seller) {

        boolean back = false;

        while (!back) {

            showSellerDashboard();

            String choice = InputUtil.readString("Enter Choice : ")
                            .trim().toLowerCase();

            switch (choice) {

                case "view":

                    orderService.viewSellerOrders(
                            seller);

                    break;

                case "search":

                    orderService.searchOrder(
                            seller);

                    break;

                case "track":

                    orderService.trackOrder(
                            seller);

                    break;

                case "update status":

                    orderService.updateOrderStatus(seller);

                    break;

                case "approve":
                case "reject":
                case "order approval":
                    showConfirmCancelMenu(seller);
                    break;

                case "back":
                    back = true;
                    break;

                default:
                    DisplayUtil.printInvalidChoice();
            }
        }
    }

    // Displays Confirm/Cancel Order Menu.

    private void showConfirmCancelMenu(Seller seller) {

        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("ORDER APPROVAL");
            System.out.println("==========================================");
            System.out.println("CONFIRM Order");
            System.out.println("CANCEL Order");
            System.out.println("BACK");

            String choice = InputUtil.readString("Enter Choice : ")
                            .trim().toLowerCase();

            switch (choice) {

                case "confirm order":

                    orderService.confirmOrder(seller);

                    break;

                case "cancel order":

                    orderService.cancelSellerOrder(seller);

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
     * Displays Customer Order Dashboard.
     */
    private void showDashboard(){

        System.out.println("ORDER MENU");
        System.out.println("------------------------------------");
        System.out.println("PLACE Order");
        System.out.println("VIEW Order");
        System.out.println("SEARCH Orders");
        System.out.println("CANCEL Order");
        System.out.println("RETURN Order");
        System.out.println("BACK");

        System.out.println("==========================================");
    }

    /**
     * Displays Seller Order Dashboard.
     */
    private void showSellerDashboard() {

        System.out.println("SELLER ORDER MENU");

        System.out.println("==========================================");

        System.out.println("VIEW");
        System.out.println("SEARCH");
        System.out.println("TRACK");
        System.out.println("ORDER APPROVAL");
        System.out.println("UPDATE STATUS");
        System.out.println("BACK");

        System.out.println(
                "==========================================");
    }

    /**
     * Displays Admin Order Dashboard.
     */
    public void showMenu() {

        boolean back = false;

        while (!back) {

            showAdminDashboard();

            String choice =
                    InputUtil.readString(
                                    "Enter Choice : ")
                            .trim()
                            .toLowerCase();

            switch (choice) {

                case "view":

                    orderService.viewAllOrders();

                    break;

                case "search":

                    orderService.searchOrder();

                    break;

                case "track":

                    orderService.trackOrder();

                    break;

                case "confirm":

                    orderService.confirmOrder();

                    break;

                case "update status":

                    orderService.updateOrderStatus();

                    break;

                case "delete":

                    orderService.deleteOrder();

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
     * Displays Admin Order Dashboard.
     */
    private void showAdminDashboard() {

        System.out.println(
                "           ADMIN ORDER MENU");

        System.out.println(
                "==========================================");

        System.out.println("VIEW");
        System.out.println("SEARCH");
        System.out.println("TRACK");
        System.out.println("CONFIRM");
        System.out.println("UPDATE STATUS");
        System.out.println("DELETE");
        System.out.println("BACK");

        System.out.println(
                "==========================================");
    }
}