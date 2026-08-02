package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.Category;
import com.crimsonlogic.ecommerce.repository.DataStore;
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
     * Adds a new Category.
     */
    public void addCategory() {

        System.out.println("\n========== ADD CATEGORY ==========");

        while (true) {

            try {

                String categoryName =
                        InputUtil.readString("Enter Category Name: ");

                ValidationUtil.validateCategoryName(categoryName);

                boolean exists = DataStore.CATEGORIES.values()
                        .stream()
                        .anyMatch(category ->
                                category.getCategoryName()
                                        .equalsIgnoreCase(categoryName));

                if (exists) {

                    DisplayUtil.printError(
                            "Category already exists.");

                    return;

                }

                String description =
                        InputUtil.readString(
                                "Enter Category Description: ");

                ValidationUtil.validateCategoryDescription(
                        description);

                String categoryId;

                do {

                    categoryId = IdGenerator.generateId("CAT");

                } while (DataStore.CATEGORIES.containsKey(categoryId));

                Category category = new Category(
                        categoryId,
                        categoryName,
                        description);

                DataStore.CATEGORIES.put(
                        category.getCategoryId(),
                        category);

                DisplayUtil.printSuccess(
                        "Category Added Successfully.");

                System.out.println(
                        "Category ID : " +
                                category.getCategoryId());

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

        if (DataStore.CATEGORIES.isEmpty()) {

            DisplayUtil.printWarning("No Categories Available.");

            return;

        }

        String[] headers = {"Category ID", "Category Name", "Description"};

        List<String[]> rows = DataStore.CATEGORIES.values().stream().map(category -> new String[]{category.getCategoryId(), category.getCategoryName(), category.getCategoryDescription()}).toList();

        DisplayUtil.printTable("AVAILABLE CATEGORIES", headers, rows);

    }

    /**
     * Searches a Category by Category Name.
     */
    public void searchCategory() {

        if (DataStore.CATEGORIES.isEmpty()) {

            DisplayUtil.printWarning("No Categories Available.");

            return;

        }

        String categoryName = InputUtil.readString("Enter Category Name : ");

        Category category = findCategoryByName(categoryName);

        if (category == null) {

            DisplayUtil.printError("Category Not Found.");

            return;

        }

        String[] headers = {"Field", "Value"};

        DisplayUtil.printTable("CATEGORY DETAILS", headers, category.getTableRows());

    }

    /**
     * Finds Category by Category Name.
     *
     * @param categoryName Category Name
     * @return Category if found, otherwise null
     */
    public Category findCategoryByName(String categoryName) {

        return DataStore.CATEGORIES.values().stream().filter(category -> category.getCategoryName().equalsIgnoreCase(categoryName)).findFirst().orElse(null);

    }

    /**
     * Updates Category.
     */
    public void updateCategory() {

        if (DataStore.CATEGORIES.isEmpty()) {

            DisplayUtil.printWarning("No Categories Available.");

            return;

        }

        viewAllCategories();

        String categoryId = InputUtil.readString("Enter Category ID : ");

        Category category = DataStore.CATEGORIES.get(categoryId);

        if (category == null) {

            DisplayUtil.printError("Category Not Found.");

            return;

        }

        while (true) {

            try {

                String categoryName = InputUtil.readString("Enter New Category Name : ");

                ValidationUtil.validateCategoryName(categoryName);

                boolean exists = DataStore.CATEGORIES.values().stream().anyMatch(c -> !c.getCategoryId().equals(categoryId) && c.getCategoryName().equalsIgnoreCase(categoryName));

                if (exists) {

                    DisplayUtil.printError("Category already exists.");

                    continue;

                }

                String description = InputUtil.readString("Enter New Description : ");

                ValidationUtil.validateCategoryDescription(description);

                category.setCategoryName(categoryName);
                category.setCategoryDescription(description);

                DisplayUtil.printSuccess("Category Updated Successfully.");

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printError(exception.getMessage());

            }

        }

    }

    /**
     * Deletes Category.
     */
    public void deleteCategory() {

        if (DataStore.CATEGORIES.isEmpty()) {

            DisplayUtil.printWarning("No Categories Available.");

            return;

        }

        viewAllCategories();

        String categoryId = InputUtil.readString("Enter Category ID : ");

        Category category = DataStore.CATEGORIES.remove(categoryId);

        if (category == null) {

            DisplayUtil.printError("Category Not Found.");

            return;

        }

        DisplayUtil.printSuccess("Category Deleted Successfully.");

    }

}
