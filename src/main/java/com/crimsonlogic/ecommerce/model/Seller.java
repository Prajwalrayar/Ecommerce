package com.crimsonlogic.ecommerce.model;

import com.crimsonlogic.ecommerce.enums.Role;
import com.crimsonlogic.ecommerce.model.abstraction.User;

import java.util.List;

/**
 * Represents a Seller in the E-Commerce Marketplace.
 *
 * <p>
 * A Seller can list and manage products.
 * This class extends the common User class and
 * contains Seller-specific information.
 * </p>
 */
public class Seller extends User {

    /* Shop Name */
    private String shopName;

    /* Shop Address */
    private String shopAddress;

    /**
     * Default Constructor.
     * Required by MyBatis.
     */
    public Seller() {
        setRole(Role.SELLER);
    }

    /**
     * Parameterized Constructor.
     *
     * @param userId Seller ID
     * @param userName Seller Name
     * @param userEmail Seller Email
     * @param userPhNo Seller Phone Number
     * @param userPassword Seller Password
     * @param address Seller Address
     * @param shopName Shop Name
     * @param shopAddress Shop Address
     */
    public Seller(String userId,
                  String userName,
                  String userEmail,
                  String userPhNo,
                  String userPassword,
                  Address address,
                  String shopName,
                  String shopAddress) {

        super(userId,
                userName,
                userEmail,
                userPhNo,
                userPassword,
                address);

        setRole(Role.SELLER);

        this.shopName = shopName;
        this.shopAddress = shopAddress;
    }

    /**
     * Returns Shop Name.
     *
     * @return Shop Name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * Sets Shop Name.
     *
     * @param shopName Shop Name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * Returns Shop Address.
     *
     * @return Shop Address
     */
    public String getShopAddress() {
        return shopAddress;
    }

    /**
     * Sets Shop Address.
     *
     * @param shopAddress Shop Address
     */
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    /**
     * Returns Seller details in table format.
     *
     * @return Seller profile rows
     */
    @Override
    public List<String[]> getTableRows() {

        List<String[]> rows = super.getTableRows();

        rows.add(new String[]{
                "Shop Name",
                getShopName()
        });

        rows.add(new String[]{
                "Shop Address",
                getShopAddress()
        });

        return rows;
    }

}