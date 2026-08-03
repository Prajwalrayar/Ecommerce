package com.crimsonlogic.ecommerce.model;

import com.crimsonlogic.ecommerce.enums.Role;
import com.crimsonlogic.ecommerce.model.abstraction.User;

import java.util.List;

/**
 * Represents a Customer in the E-Commerce Marketplace.
 *
 * <p>
 * A Customer can browse products, add products to cart,
 * place orders and make payments.
 * </p>
 */
public class Customer extends User {

    private double walletBalance;

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }


    /**
     * Default Constructor.
     * Required by MyBatis.
     */
    public Customer() {
        setRole(Role.CUSTOMER);
    }

    /**
     * Parameterized Constructor.
     *
     * @param userId Customer ID
     * @param userName Customer Name
     * @param userEmail Customer Email
     * @param userPhNo Customer Phone Number
     * @param userPassword Customer Password
     * @param address Customer Address
     */
    public Customer(String userId,
                    String userName,
                    String userEmail,
                    String userPhNo,
                    String userPassword,
                    Address address,
                    double walletBalance) {

        super(userId,
                userName,
                userEmail,
                userPhNo,
                userPassword,
                address);

        setRole(Role.CUSTOMER);
        this.walletBalance = walletBalance;

    }

    /**
     * Returns Customer details in table format.
     *
     * @return Customer profile rows
     */
    @Override
    public List<String[]> getTableRows() {

        List<String[]> rows = super.getTableRows();

        rows.add(new String[]{

                "Wallet Balance",

                String.format(
                        "%.2f",
                        walletBalance)

        });

        return rows;

    }

}