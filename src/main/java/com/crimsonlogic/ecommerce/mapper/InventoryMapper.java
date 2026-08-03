package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.model.Inventory;

import java.util.List;

public interface InventoryMapper {

    /**
     * Inserts Inventory.
     *
     * @param inventory Inventory
     */
    void insertInventory(Inventory inventory);

    /**
     * Updates Inventory.
     *
     * @param inventory Inventory
     */
    void updateInventory(Inventory inventory);

    /**
     * Deletes Inventory.
     *
     * @param inventoryId Inventory ID
     */
    void deleteInventory(String inventoryId);

    /**
     * Finds Inventory by ID.
     *
     * @param inventoryId Inventory ID
     * @return Inventory
     */
    Inventory findInventoryById(String inventoryId);

    /**
     * Finds Inventory by Product ID.
     *
     * @param productId Product ID
     * @return Inventory
     */
    Inventory findInventoryByProduct(String productId);

    /**
     * Returns all Inventory.
     *
     * @return Inventory List
     */
    List<Inventory> findAllInventory();

    /**
     * Updates Product Quantity.
     *
     * @param inventory Inventory
     */
    void updateQuantity(Inventory inventory);

    List<Inventory> findInventoryBySeller(
            String sellerId);

}