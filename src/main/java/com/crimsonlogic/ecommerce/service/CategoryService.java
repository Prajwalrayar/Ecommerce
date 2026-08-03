package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.CategoryDAO;
import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.Category;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

import java.util.List;

/**
 * Service class responsible for Category operations.
 */
public class CategoryService {

    /**
     * Category DAO.
     */
    private final CategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Adds a new Category.
     */
    public void addCategory() {

        System.out.println("\n========== ADD CATEGORY ==========");

        while (true) {

            try {

                String categoryName =
                        InputUtil.readString("Enter Category Name: ");

                ValidationUtil.validateCategoryName(categoryName);

                if (categoryDAO.findCategoryByName(categoryName) != null) {

                    DisplayUtil.printError(
                            "Category already exists.");

                    return;

                }

                String description =
                        InputUtil.readString(
                                "Enter Category Description: ");

                ValidationUtil.validateCategoryDescription(
                        description);

                Category category = new Category(
                        IdGenerator.generateId("CAT"),
                        categoryName,
                        description);

                categoryDAO.insertCategory(category);

                DisplayUtil.printSuccess(
                        "Category Added Successfully.");

                System.out.println(
                        "Category ID : "
                                + category.getCategoryId());

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printError(
                        exception.getMessage());

            }

        }

    }

    /**
     * Displays all Categories.
     */
    public void viewAllCategories() {

        List<Category> categories =
                categoryDAO.findAllCategories();

        if (categories.isEmpty()) {

            DisplayUtil.printWarning(
                    "No Categories Available.");

            return;

        }

        String[] headers = {
                "Category ID",
                "Category Name",
                "Description"
        };

        List<String[]> rows =
                categories.stream()
                        .map(category -> new String[]{
                                category.getCategoryId(),
                                category.getCategoryName(),
                                category.getCategoryDescription()
                        })
                        .toList();

        DisplayUtil.printTable(
                "AVAILABLE CATEGORIES",
                headers,
                rows);

    }

    /**
     * Searches a Category by Category Name.
     */
    public void searchCategory() {

        List<Category> categories =
                categoryDAO.findAllCategories();

        if (categories.isEmpty()) {

            DisplayUtil.printWarning(
                    "No Categories Available.");

            return;

        }

        String categoryName =
                InputUtil.readString(
                        "Enter Category Name : ");

        Category category =
                categoryDAO.findCategoryByName(categoryName);

        if (category == null) {

            DisplayUtil.printError(
                    "Category Not Found.");

            return;

        }

        String[] headers = {
                "Field",
                "Value"
        };

        DisplayUtil.printTable(
                "CATEGORY DETAILS",
                headers,
                category.getTableRows());

    }

    /**
     * Finds Category by Category Name.
     *
     * @param categoryName Category Name
     * @return Category
     */
    public Category findCategoryByName(String categoryName) {

        return categoryDAO.findCategoryByName(categoryName);

    }

    /**
     * Updates Category.
     */
    public void updateCategory() {

        List<Category> categories =
                categoryDAO.findAllCategories();

        if (categories.isEmpty()) {

            DisplayUtil.printWarning(
                    "No Categories Available.");

            return;

        }

        viewAllCategories();

        String categoryId =
                InputUtil.readString(
                        "Enter Category ID : ");

        Category category =
                categoryDAO.findCategoryById(categoryId);

        if (category == null) {

            DisplayUtil.printError(
                    "Category Not Found.");

            return;

        }

        while (true) {

            try {

                String categoryName =
                        InputUtil.readString(
                                "Enter New Category Name : ");

                ValidationUtil.validateCategoryName(
                        categoryName);

                Category existing =
                        categoryDAO.findCategoryByName(categoryName);

                if (existing != null &&
                        !existing.getCategoryId()
                                .equals(categoryId)) {

                    DisplayUtil.printError(
                            "Category already exists.");

                    continue;

                }

                String description =
                        InputUtil.readString(
                                "Enter New Description : ");

                ValidationUtil.validateCategoryDescription(
                        description);

                category.setCategoryName(categoryName);

                category.setCategoryDescription(description);

                categoryDAO.updateCategory(category);

                DisplayUtil.printSuccess(
                        "Category Updated Successfully.");

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printError(
                        exception.getMessage());

            }

        }

    }

    /**
     * Deletes Category.
     */
    public void deleteCategory() {

        List<Category> categories =
                categoryDAO.findAllCategories();

        if (categories.isEmpty()) {

            DisplayUtil.printWarning(
                    "No Categories Available.");

            return;

        }

        viewAllCategories();

        String categoryId =
                InputUtil.readString(
                        "Enter Category ID : ");

        Category category =
                categoryDAO.findCategoryById(categoryId);

        if (category == null) {

            DisplayUtil.printError(
                    "Category Not Found.");

            return;

        }

        categoryDAO.deleteCategory(categoryId);

        DisplayUtil.printSuccess(
                "Category Deleted Successfully.");

    }

}