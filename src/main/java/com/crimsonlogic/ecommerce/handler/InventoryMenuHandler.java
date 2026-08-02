package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.service.InventoryService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles Inventory Menu operations.
 */
public class InventoryMenuHandler {
    private final InventoryService inventoryService;
    /**
     * Parameterized Constructor.
     *
     * @param inventoryService Inventory Service
     */
    public InventoryMenuHandler(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Displays Seller Inventory Dashboard.
     *
     * @param seller Logged-in Seller
     */
    public void showMenu(Seller seller) {
        boolean back = false;
        while (!back) {
            showSellerDashboard();
            String choice = InputUtil.readString("Enter Choice : ")
                            .trim().toLowerCase();
            switch (choice) {
                case "add stock":
                    inventoryService.addStock(seller);
                    break;
                case "view stock":
                    inventoryService.viewSellerInventory(seller);
                    break;
                case "search stock":
                    inventoryService.searchInventory(seller);
                    break;
                case "update stock":
                    inventoryService.updateStock(seller);
                    break;
                case "delete stock":
                    inventoryService.deleteStock(seller);
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
     * Displays Admin Inventory Dashboard.
     */
    public void showMenu() {
        boolean back = false;
        while (!back) {
            showAdminDashboard();
            String choice = InputUtil.readString("Enter Choice : ")
                            .trim().toLowerCase();

            switch (choice) {

                case "add stock":
                    inventoryService.addStock();
                    break;
                case "view stock":
                    inventoryService.viewAllInventory();
                    break;
                case "search stock":
                    inventoryService.searchInventory();
                    break;
                case "update stock":
                    inventoryService.updateStock();
                    break;
                case "delete stock":
                    inventoryService.deleteStock();
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
     * Displays Seller Inventory Dashboard.
     */
    private void showSellerDashboard() {

        System.out.println("\n==========================================");
        System.out.println("           INVENTORY MENU");
        System.out.println("==========================================");
        System.out.println("ADD STOCK");
        System.out.println("VIEW STOCK");
        System.out.println("SEARCH STOCK");
        System.out.println("UPDATE STOCK");
        System.out.println("DELETE STOCK");
        System.out.println("BACK");
        System.out.println("==========================================");

    }

    /**
     * Displays Admin Inventory Dashboard.
     */
    private void showAdminDashboard() {

        System.out.println("\n==========================================");
        System.out.println("        ADMIN INVENTORY MENU");
        System.out.println("==========================================");
        System.out.println("ADD STOCK");
        System.out.println("VIEW STOCK");
        System.out.println("SEARCH STOCK");
        System.out.println("UPDATE STOCK");
        System.out.println("DELETE STOCK");
        System.out.println("BACK");
        System.out.println("==========================================");

    }

}