package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.service.ProductService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles Browse Product Menu operations.
 */
public class BrowseProductMenuHandler {

    private final ProductService productService;

    /**
     * Default Constructor.
     */
    public BrowseProductMenuHandler() {

        this.productService = new ProductService();

    }

    /**
     * Displays Browse Products Dashboard.
     */
    public void showMenu(Customer customer) {

        boolean back = false;

        while (!back) {

            showDashboard();

            String choice = InputUtil.readString("Enter Choice : ")
                    .trim()
                    .toLowerCase();

            switch (choice) {

                case "view":

                    productService.viewAllProducts();
                    break;

                case "search":

                    productService.searchProduct();
                    break;

                case "filter":

                    productService.filterProductsByCategory();
                    break;

                case "sort":

                    productService.sortProductsByPrice();
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
     * Displays Browse Products Dashboard.
     */
    private void showDashboard() {

        System.out.println("\n==========================================");
        System.out.println("          BROWSE PRODUCTS");
        System.out.println("==========================================");
        System.out.println("VIEW");
        System.out.println("SEARCH");
        System.out.println("FILTER");
        System.out.println("SORT");
        System.out.println("BACK");
        System.out.println("==========================================");

    }

}