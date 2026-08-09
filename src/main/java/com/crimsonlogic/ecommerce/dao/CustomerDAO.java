package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.mapper.CustomerMapper;
import com.crimsonlogic.ecommerce.model.Customer;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CustomerDAO {

    /**
     * Inserts Customer.
     *
     * @param customer Customer
     */
    public void insertCustomer(Customer customer) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CustomerMapper mapper = session.getMapper(CustomerMapper.class);

            mapper.insertCustomer(customer);

            session.commit();

        }

    }

    /**
     * Updates Customer.
     *
     * @param customer Customer
     */
    public void updateCustomer(Customer customer) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CustomerMapper mapper = session.getMapper(CustomerMapper.class);

            mapper.updateCustomer(customer);

            session.commit();

        }

    }

    public void updatePassword(
            String userId,
            String userPassword) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            CustomerMapper mapper =
                    session.getMapper(CustomerMapper.class);

            mapper.updatePassword(
                    userId,
                    userPassword);

            session.commit();
        }
    }

    /**
     * Updates Wallet Balance.
     *
     * @param customer Customer
     */
    public void updateWalletBalance(Customer customer) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CustomerMapper mapper = session.getMapper(CustomerMapper.class);

            mapper.updateWalletBalance(customer);

            session.commit();

        }

    }

    /**
     * Deletes Customer.
     *
     * @param userId Customer ID
     */
    public void deleteCustomer(String userId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CustomerMapper mapper = session.getMapper(CustomerMapper.class);

            mapper.deleteCustomer(userId);

            session.commit();

        }

    }

    /**
     * Finds Customer by ID.
     *
     * @param userId Customer ID
     * @return Customer
     */
    public Customer findCustomerById(String userId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CustomerMapper mapper = session.getMapper(CustomerMapper.class);

            return mapper.findCustomerById(userId);

        }

    }

    /**
     * Finds Customer by Email.
     *
     * @param email Customer Email
     * @return Customer
     */
    public Customer findCustomerByEmail(String email) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CustomerMapper mapper = session.getMapper(CustomerMapper.class);

            return mapper.findCustomerByEmail(email);

        }

    }

    /**
     * Finds Customer by Phone.
     *
     * @param phone Customer Phone Number
     * @return Customer
     */
    public Customer findCustomerByPhone(String phone) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CustomerMapper mapper = session.getMapper(CustomerMapper.class);

            return mapper.findCustomerByPhone(phone);

        }

    }

    /**
     * Returns All Customers.
     *
     * @return Customer List
     */
    public List<Customer> findAllCustomers() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CustomerMapper mapper = session.getMapper(CustomerMapper.class);

            return mapper.findAllCustomers();

        }

    }

}