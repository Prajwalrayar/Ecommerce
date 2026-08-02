package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.service.ProductService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles Product Menu operations.
 */
public class ProductMenuHandler {

    private final ProductService productService;

    /**
     * Parameterized Constructor.
     *
     * @param productService Product Service
     */
    public ProductMenuHandler(ProductService productService) {

        this.productService = productService;

    }

    /**
     * Displays Product Dashboard for Seller.
     *
     * @param seller Logged-in Seller
     */
    public void showMenu(Seller seller) {

        boolean back = false;

        while (!back) {

            showSellerDashboard();

            String choice = InputUtil.readString("Enter Choice : ")
                    .trim()
                    .toLowerCase();

            switch (choice) {

                case "add":

                    productService.addProduct(seller);
                    break;

                case "view":

                    productService.viewSellerProducts(seller);
                    break;

                case "search":

                    productService.searchProduct();
                    break;

                case "update":

                    productService.updateProduct(seller);
                    break;

                case "delete":

                    productService.deleteProduct(seller);
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
     * Displays Product Dashboard for Admin.
     */
    public void showMenu() {

        boolean back = false;

        while (!back) {

            showAdminDashboard();

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

                case "delete":

                    productService.deleteProductByAdmin();
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
     * Displays Seller Product Dashboard.
     */
    private void showSellerDashboard() {

        System.out.println("\n==========================================");
        System.out.println("             PRODUCT MENU");
        System.out.println("==========================================");
        System.out.println("ADD");
        System.out.println("VIEW");
        System.out.println("SEARCH");
        System.out.println("UPDATE");
        System.out.println("DELETE");
        System.out.println("BACK");
        System.out.println("==========================================");

    }

    /**
     * Displays Admin Product Dashboard.
     */
    private void showAdminDashboard() {

        System.out.println("\n==========================================");
        System.out.println("          ADMIN PRODUCT MENU");
        System.out.println("==========================================");
        System.out.println("VIEW");
        System.out.println("SEARCH");
        System.out.println("DELETE");
        System.out.println("BACK");
        System.out.println("==========================================");

    }

}