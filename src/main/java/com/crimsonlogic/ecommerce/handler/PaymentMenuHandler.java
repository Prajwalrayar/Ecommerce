package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.service.PaymentService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles Payment Menu operations.
 */
public class PaymentMenuHandler {

    /**
     * Payment Service.
     */
    private final PaymentService paymentService;

    /**
     * Default Constructor.
     */
    public PaymentMenuHandler() {

        this.paymentService =
                new PaymentService();

    }

    /**
     * Displays Payment Menu.
     *
     * @param customer Logged-in Customer
     */
    public void showMenu(Customer customer) {

        boolean back = false;

        while (!back) {

            showDashboard();

            String choice =
                    InputUtil.readString(
                                    "Enter Choice : ")
                            .trim()
                            .toLowerCase();

            switch (choice) {

                case "make payment":

                    paymentService.makePayment(customer);

                    break;

                case "view":

                    paymentService.viewPayments(customer);

                    break;

                case "search":

                    paymentService.searchPayment(customer);

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

    /**
     * Displays Payment Dashboard.
     */
    private void showDashboard() {

        System.out.println("\n==========================================");
        System.out.println("             PAYMENT MENU");
        System.out.println("==========================================");
        System.out.println("MAKE PAYMENT");
        System.out.println("VIEW");
        System.out.println("SEARCH");
        System.out.println("BACK");
        System.out.println("==========================================");

    }

}