package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.model.Category;

import java.util.List;

public interface CategoryMapper {

    /**
     * Inserts Category.
     *
     * @param category Category
     */
    void insertCategory(Category category);

    /**
     * Updates Category.
     *
     * @param category Category
     */
    void updateCategory(Category category);

    /**
     * Deletes Category.
     *
     * @param categoryId Category ID
     */
    void deleteCategory(String categoryId);

    /**
     * Finds Category by ID.
     *
     * @param categoryId Category ID
     * @return Category
     */
    Category findCategoryById(String categoryId);

    /**
     * Finds Category by Name.
     *
     * @param categoryName Category Name
     * @return Category
     */
    Category findCategoryByName(String categoryName);

    /**
     * Returns all Categories.
     *
     * @return Category List
     */
    List<Category> findAllCategories();

}