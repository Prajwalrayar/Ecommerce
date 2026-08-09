package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.model.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper {

    /**
     * Inserts Customer.
     *
     * @param customer Customer
     */
    void insertCustomer(Customer customer);

    /**
     * Updates Customer.
     *
     * @param customer Customer
     */
    void updateCustomer(Customer customer);

    void updatePassword(
            @Param("userId") String userId,
            @Param("userPassword") String userPassword);

    /**
     * Deletes Customer.
     *
     * @param customerId Customer ID
     */
    void deleteCustomer(String customerId);

    /**
     * Finds Customer by ID.
     *
     * @param customerId Customer ID
     * @return Customer
     */
    Customer findCustomerById(String customerId);

    /**
     * Finds Customer by Email.
     *
     * @param email Customer Email
     * @return Customer
     */
    Customer findCustomerByEmail(String email);

    /**
     * Finds Customer by Phone Number.
     *
     * @param phone Customer Phone Number
     * @return Customer
     */
    Customer findCustomerByPhone(String phone);

    /**
     * Returns all Customers.
     *
     * @return Customer List
     */
    List<Customer> findAllCustomers();

    /**
     * Updates Customer Wallet Balance.
     *
     * @param customer Customer
     */
    void updateWalletBalance(Customer customer);

}