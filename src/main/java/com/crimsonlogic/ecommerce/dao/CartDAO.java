package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.mapper.CartMapper;
import com.crimsonlogic.ecommerce.model.Cart;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CartDAO {

    /**
     * Inserts Cart Item.
     *
     * @param cart Cart
     */
    public void insertCartItem(Cart cart) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CartMapper mapper = session.getMapper(CartMapper.class);

            mapper.insertCartItem(cart);

            session.commit();

        }

    }

    /**
     * Updates Cart Item.
     *
     * @param cart Cart
     */
    public void updateCartItem(Cart cart) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CartMapper mapper = session.getMapper(CartMapper.class);

            mapper.updateCartItem(cart);

            session.commit();

        }

    }

    /**
     * Deletes Cart Item.
     *
     * @param cartId Cart ID
     */
    public void deleteCartItem(String cartId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CartMapper mapper = session.getMapper(CartMapper.class);

            mapper.deleteCartItem(cartId);

            session.commit();

        }

    }

    /**
     * Clears Customer Cart.
     *
     * @param customerId Customer ID
     */
    public void clearCart(String customerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CartMapper mapper = session.getMapper(CartMapper.class);

            mapper.clearCart(customerId);

            session.commit();

        }

    }

    /**
     * Finds Cart Item by ID.
     *
     * @param cartId Cart ID
     * @return Cart
     */
    public Cart findCartItemById(String cartId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CartMapper mapper = session.getMapper(CartMapper.class);

            return mapper.findCartItemById(cartId);

        }

    }

    /**
     * Finds Cart Item by Customer and Product.
     *
     * @param customerId Customer ID
     * @param productId Product ID
     * @return Cart
     */
    public Cart findCartItem(String customerId,
                             String productId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CartMapper mapper = session.getMapper(CartMapper.class);

            return mapper.findCartItem(customerId, productId);

        }

    }

    /**
     * Returns Customer Cart.
     *
     * @param customerId Customer ID
     * @return Cart List
     */
    public List<Cart> findCartByCustomer(String customerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CartMapper mapper = session.getMapper(CartMapper.class);

            return mapper.findCartByCustomer(customerId);

        }

    }

    /**
     * Returns All Cart Items.
     *
     * @return Cart List
     */
    public List<Cart> findAllCartItems() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CartMapper mapper = session.getMapper(CartMapper.class);

            return mapper.findAllCartItems();

        }

    }

}