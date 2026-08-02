package com.crimsonlogic.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Category {

    /* Category ID */
    private String categoryId;

    /* Category Name */
    private String categoryName;

    /* Category Description */
    private String categoryDescription;

    /**
     * Default Constructor.
     */
    public Category() {
    }

    /**
     * Parameterized Constructor.
     *
     * @param categoryId Category ID
     * @param categoryName Category Name
     * @param categoryDescription Category Description
     */
    public Category(String categoryId,
                    String categoryName,
                    String categoryDescription) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;

    }

    // =====================================================
    // Getters & Setters
    // =====================================================

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    // =====================================================
    // Object Methods
    // =====================================================

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Category category = (Category) object;

        return Objects.equals(categoryId,
                category.categoryId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }

    @Override
    public String toString() {

        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                '}';

    }

    /**
     * Returns Category details in table format.
     *
     * @return Table Rows
     */
    public List<String[]> getTableRows() {

        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{
                "Category ID",
                getCategoryId()
        });

        rows.add(new String[]{
                "Category Name",
                getCategoryName()
        });

        rows.add(new String[]{
                "Description",
                getCategoryDescription()
        });

        return rows;

    }
}
