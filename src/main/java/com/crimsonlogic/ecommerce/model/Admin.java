package com.crimsonlogic.ecommerce.model;

import com.crimsonlogic.ecommerce.enums.Role;
import com.crimsonlogic.ecommerce.model.abstraction.User;

import java.util.List;

/**
 * Represents an Admin in the E-Commerce Marketplace.
 *
 * <p>
 * Admin is responsible for managing customers,
 * sellers, products and overall marketplace operations.
 * </p>
 */
public class Admin extends User {

    /**
     * Default Constructor.
     * Required by MyBatis.
     */
    public Admin() {
        setRole(Role.ADMIN);
    }

    /**
     * Parameterized Constructor.
     *
     * @param userId Admin ID
     * @param userName Admin Name
     * @param userEmail Admin Email
     * @param userPhNo Admin Phone Number
     * @param userPassword Admin Password
     * @param address Admin Address
     */
    public Admin(String userId,
                 String userName,
                 String userEmail,
                 String userPhNo,
                 String userPassword,
                 Address address) {

        super(userId,
                userName,
                userEmail,
                userPhNo,
                userPassword,
                address);

        setRole(Role.ADMIN);
    }

    /**
     * Returns Admin details in table format.
     *
     * @return Admin profile rows
     */
    @Override
    public List<String[]> getTableRows() {
        return super.getTableRows();
    }

}