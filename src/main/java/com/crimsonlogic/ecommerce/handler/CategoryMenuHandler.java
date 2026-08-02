package com.crimsonlogic.ecommerce.handler;

import com.crimsonlogic.ecommerce.service.CategoryService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;

/**
 * Handles all Category menu operations.
 */
public class CategoryMenuHandler {

    private final CategoryService categoryService;

    /**
     * Parameterized Constructor.
     *
     * @param categoryService Category Service
     */
    public CategoryMenuHandler(CategoryService categoryService) {

        this.categoryService = categoryService;

    }

    /**
     * Displays Category Menu.
     */
    public void showMenu() {

        boolean back = false;

        while (!back) {

            showDashboard();

            String choice = InputUtil.readString("Enter Choice : ").trim().toLowerCase();

            switch (choice) {

                case "add":

                    categoryService.addCategory();
                    break;

                case "view":

                    categoryService.viewAllCategories();
                    break;

                case "search":

                    categoryService.searchCategory();
                    break;

                case "update":

                    categoryService.updateCategory();
                    break;

                case "delete":

                    categoryService.deleteCategory();
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
     * Displays Category Dashboard.
     */
    private void showDashboard() {

        System.out.println("--------------------------------------------");
        System.out.println("             CATEGORY MENU");
        System.out.println("--------------------------------------------");
        System.out.println("ADD");
        System.out.println("VIEW");
        System.out.println("SEARCH");
        System.out.println("UPDATE");
        System.out.println("DELETE");
        System.out.println("BACK");

    }

}