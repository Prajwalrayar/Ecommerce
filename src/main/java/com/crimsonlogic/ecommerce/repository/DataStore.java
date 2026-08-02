package com.crimsonlogic.ecommerce.repository;

import com.crimsonlogic.ecommerce.model.*;
import com.crimsonlogic.ecommerce.util.IdGenerator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Acts as the temporary in-memory data source for the application.
 */
public final class DataStore {

    /**
     * Stores all Admins.
     */
    public static final Map<String, Admin> ADMINS =
            new LinkedHashMap<>();

    /**
     * Stores all Sellers.
     */
    public static final Map<String, Seller> SELLERS =
            new LinkedHashMap<>();

    /**
     * Stores all Customers.
     */
    public static final Map<String, Customer> CUSTOMERS =
            new LinkedHashMap<>();

    /**
     * Stores all Categories.
     */
    public static final Map<String, Category> CATEGORIES =
            new LinkedHashMap<>();

    /**
     * Stores all Products.
     */
    public static final Map<String, Product> PRODUCTS =
            new LinkedHashMap<>();

    /**
     * Stores all Inventories.
     */
    public static final Map<String, Inventory> INVENTORIES =
            new LinkedHashMap<>();

    /**
     * Stores all Cart Items.
     */
    public static final Map<String, Cart> CARTS =
            new LinkedHashMap<>();

    /**
     * Stores all Orders.
     */
    public static final Map<String, Order> ORDERS =
            new LinkedHashMap<>();

    /**
     * Stores all Payments.
     */
    public static final Map<String, Payment> PAYMENTS =
            new LinkedHashMap<>();

    /**
     * Loads default data.
     */
    static {

        Admin admin = new Admin(
                IdGenerator.generateId("ADM"),
                "Admin",
                "admin@gmail.com",
                "9876543210",
                "Admin@123",
                null);

        ADMINS.put(
                admin.getUserId(),
                admin);

    }

    /**
     * Private Constructor.
     */
    private DataStore() {

    }

}