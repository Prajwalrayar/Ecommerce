package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.mapper.InventoryMapper;
import com.crimsonlogic.ecommerce.model.Inventory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class InventoryDAO {

    /**
     * Inserts Inventory.
     *
     * @param inventory Inventory
     */
    public void insertInventory(Inventory inventory) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            InventoryMapper mapper = session.getMapper(InventoryMapper.class);

            mapper.insertInventory(inventory);

            session.commit();

        }

    }

    /**
     * Updates Inventory.
     *
     * @param inventory Inventory
     */
    public void updateInventory(Inventory inventory) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            InventoryMapper mapper = session.getMapper(InventoryMapper.class);

            mapper.updateInventory(inventory);

            session.commit();

        }

    }

    /**
     * Updates Inventory Quantity.
     *
     * @param inventory Inventory
     */
    public void updateQuantity(Inventory inventory) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            InventoryMapper mapper = session.getMapper(InventoryMapper.class);

            mapper.updateQuantity(inventory);

            session.commit();

        }

    }

    /**
     * Deletes Inventory.
     *
     * @param inventoryId Inventory ID
     */
    public void deleteInventory(String inventoryId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            InventoryMapper mapper = session.getMapper(InventoryMapper.class);

            mapper.deleteInventory(inventoryId);

            session.commit();

        }

    }

    /**
     * Finds Inventory by ID.
     *
     * @param inventoryId Inventory ID
     * @return Inventory
     */
    public Inventory findInventoryById(String inventoryId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            InventoryMapper mapper = session.getMapper(InventoryMapper.class);

            return mapper.findInventoryById(inventoryId);

        }

    }

    /**
     * Finds Inventory by Product.
     *
     * @param productId Product ID
     * @return Inventory
     */
    public Inventory findInventoryByProduct(String productId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            InventoryMapper mapper = session.getMapper(InventoryMapper.class);

            return mapper.findInventoryByProduct(productId);

        }

    }

    /**
     * Returns Inventory of a Seller.
     *
     * @param sellerId Seller ID
     * @return Inventory List
     */
    public List<Inventory> findInventoryBySeller(String sellerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            InventoryMapper mapper = session.getMapper(InventoryMapper.class);

            return mapper.findInventoryBySeller(sellerId);

        }

    }

    /**
     * Returns All Inventory.
     *
     * @return Inventory List
     */
    public List<Inventory> findAllInventory() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            InventoryMapper mapper = session.getMapper(InventoryMapper.class);

            return mapper.findAllInventory();

        }

    }

}