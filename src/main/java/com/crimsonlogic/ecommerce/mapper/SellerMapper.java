package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.model.Seller;

import java.util.List;

public interface SellerMapper {

    /**
     * Inserts Seller.
     *
     * @param seller Seller
     */
    void insertSeller(Seller seller);

    /**
     * Updates Seller.
     *
     * @param seller Seller
     */
    void updateSeller(Seller seller);

    /**
     * Deletes Seller.
     *
     * @param sellerId Seller ID
     */
    void deleteSeller(String sellerId);

    /**
     * Finds Seller by ID.
     *
     * @param sellerId Seller ID
     * @return Seller
     */
    Seller findSellerById(String sellerId);

    /**
     * Finds Seller by Email.
     *
     * @param email Seller Email
     * @return Seller
     */
    Seller findSellerByEmail(String email);

    /**
     * Finds Seller by Phone Number.
     *
     * @param phone Seller Phone Number
     * @return Seller
     */
    Seller findSellerByPhone(String phone);

    /**
     * Returns all Sellers.
     *
     * @return Seller List
     */
    List<Seller> findAllSellers();

}