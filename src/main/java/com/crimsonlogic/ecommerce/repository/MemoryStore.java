package com.crimsonlogic.ecommerce.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import com.crimsonlogic.ecommerce.model.Admin;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Seller;

/**
 * Temporary in-memory data store.
 *
 * This class simulates database storage until
 * MyBatis integration is completed.
 *
 * Later this class can be removed completely
 * without affecting the service layer.
 */
public final class MemoryStore {

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
     * Private constructor.
     */
    private MemoryStore() {

    }

}