package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.model.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {

    /**
     * Inserts Cart Item.
     *
     * @param cart Cart
     */
    void insertCartItem(Cart cart);

    /**
     * Updates Cart Item.
     *
     * @param cart Cart
     */
    void updateCartItem(Cart cart);

    /**
     * Deletes Cart Item.
     *
     * @param cartId Cart ID
     */
    void deleteCartItem(
            @Param("cartId")
            String cartId);

    /**
     * Clears Customer Cart.
     *
     * @param customerId Customer ID
     */
    void clearCart(
            @Param("customerId")
            String customerId);

    /**
     * Finds Cart Item by ID.
     *
     * @param cartId Cart ID
     * @return Cart
     */
    Cart findCartItemById(
            @Param("cartId")
            String cartId);

    /**
     * Finds Cart Item by Customer and Product.
     *
     * @param customerId Customer ID
     * @param productId Product ID
     * @return Cart
     */
    Cart findCartItem(

            @Param("customerId")
            String customerId,

            @Param("productId")
            String productId

    );

    /**
     * Returns Customer Cart.
     *
     * @param customerId Customer ID
     * @return Cart List
     */
    List<Cart> findCartByCustomer(
            @Param("customerId")
            String customerId);

    /**
     * Returns All Cart Items.
     *
     * @return Cart List
     */
    List<Cart> findAllCartItems();



}