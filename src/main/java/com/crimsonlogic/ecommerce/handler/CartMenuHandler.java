package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.service.CartService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles Cart Menu operations.
 */
public class CartMenuHandler {

    /**
     * Cart Service.
     */
    private final CartService cartService;

    /**
     * Parameterized Constructor.
     *
     * @param cartService Cart Service
     */
    public CartMenuHandler(CartService cartService) {

        this.cartService = cartService;

    }

    /**
     * Displays Cart Dashboard.
     *
     * @param customer Logged-in Customer
     */
    public void showMenu(Customer customer) {

        boolean back = false;

        while (!back) {

            showDashboard();

            String choice = InputUtil.readString("Enter Choice : ")
                    .trim()
                    .toLowerCase();

            switch (choice) {

                case "add":

                    cartService.addToCart(customer);
                    break;

                case "view":

                    cartService.viewCart(customer);
                    break;

                case "update":

                    cartService.updateQuantity(customer);
                    break;

                case "remove":

                    cartService.removeItem(customer);
                    break;

                case "clear":

                    cartService.clearCart(customer);
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
     * Displays Cart Dashboard.
     */
    private void showDashboard() {

        System.out.println("\n==========================================");
        System.out.println("               CART MENU");
        System.out.println("==========================================");
        System.out.println("ADD");
        System.out.println("VIEW");
        System.out.println("UPDATE");
        System.out.println("REMOVE");
        System.out.println("CLEAR");
        System.out.println("BACK");
        System.out.println("==========================================");

    }

}