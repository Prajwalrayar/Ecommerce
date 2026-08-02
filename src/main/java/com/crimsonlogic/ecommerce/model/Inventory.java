package com.crimsonlogic.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents Inventory details of a Product.
 */
public class Inventory {

    /**
     * Inventory ID.
     */
    private String inventoryId;

    /**
     * Product.
     */
    private Product product;

    /**
     * Available Quantity.
     */
    private int quantity;

    /**
     * Default Constructor.
     */
    public Inventory() {

    }

    /**
     * Parameterized Constructor.
     *
     * @param inventoryId Inventory ID
     * @param product Product
     * @param quantity Quantity
     */
    public Inventory(String inventoryId,
                     Product product,
                     int quantity) {

        this.inventoryId = inventoryId;
        this.product = product;
        this.quantity = quantity;

    }

    /**
     * Returns Inventory ID.
     *
     * @return Inventory ID
     */
    public String getInventoryId() {

        return inventoryId;

    }

    /**
     * Sets Inventory ID.
     *
     * @param inventoryId Inventory ID
     */
    public void setInventoryId(String inventoryId) {

        this.inventoryId = inventoryId;

    }

    /**
     * Returns Product.
     *
     * @return Product
     */
    public Product getProduct() {

        return product;

    }

    /**
     * Sets Product.
     *
     * @param product Product
     */
    public void setProduct(Product product) {

        this.product = product;

    }

    /**
     * Returns Quantity.
     *
     * @return Quantity
     */
    public int getQuantity() {

        return quantity;

    }

    /**
     * Sets Quantity.
     *
     * @param quantity Quantity
     */
    public void setQuantity(int quantity) {

        this.quantity = quantity;

    }

    /**
     * Returns Inventory Details.
     *
     * @return Table Rows
     */
    public List<String[]> getTableRows() {

        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{
                "Inventory ID",
                inventoryId
        });

        rows.add(new String[]{
                "Product ID",
                product.getProductId()
        });

        rows.add(new String[]{
                "Product Name",
                product.getProductName()
        });

        rows.add(new String[]{
                "Category",
                product.getCategory().getCategoryName()
        });

        rows.add(new String[]{
                "Seller",
                product.getSeller().getShopName()
        });

        rows.add(new String[]{
                "Quantity",
                String.valueOf(quantity)
        });

        return rows;

    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {

            return true;

        }

        if (!(object instanceof Inventory inventory)) {

            return false;

        }

        return Objects.equals(
                inventoryId,
                inventory.inventoryId);

    }

    @Override
    public int hashCode() {

        return Objects.hash(inventoryId);

    }

    @Override
    public String toString() {

        return "Inventory{" +
                "inventoryId='" + inventoryId + '\'' +
                ", product=" + product.getProductName() +
                ", quantity=" + quantity +
                '}';

    }

}