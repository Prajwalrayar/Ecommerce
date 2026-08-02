package com.crimsonlogic.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Customer Cart Item.
 */
public class Cart {

    /**
     * Cart ID.
     */
    private String cartId;

    /**
     * Customer.
     */
    private Customer customer;

    /**
     * Product.
     */
    private Product product;

    /**
     * Quantity.
     */
    private int quantity;

    /**
     * Default Constructor.
     */
    public Cart() {

    }

    /**
     * Parameterized Constructor.
     *
     * @param cartId Cart ID
     * @param customer Customer
     * @param product Product
     * @param quantity Quantity
     */
    public Cart(String cartId,
                Customer customer,
                Product product,
                int quantity) {

        this.cartId = cartId;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;

    }

    /**
     * Returns Cart ID.
     *
     * @return Cart ID
     */
    public String getCartId() {

        return cartId;

    }

    /**
     * Sets Cart ID.
     *
     * @param cartId Cart ID
     */
    public void setCartId(String cartId) {

        this.cartId = cartId;

    }

    /**
     * Returns Customer.
     *
     * @return Customer
     */
    public Customer getCustomer() {

        return customer;

    }

    /**
     * Sets Customer.
     *
     * @param customer Customer
     */
    public void setCustomer(Customer customer) {

        this.customer = customer;

    }

    /**
     * Returns Product.
     *
     * @return Product
     */
    public Product getProduct() {

        return product;

    }

    /**
     * Sets Product.
     *
     * @param product Product
     */
    public void setProduct(Product product) {

        this.product = product;

    }

    /**
     * Returns Quantity.
     *
     * @return Quantity
     */
    public int getQuantity() {

        return quantity;

    }

    /**
     * Sets Quantity.
     *
     * @param quantity Quantity
     */
    public void setQuantity(int quantity) {

        this.quantity = quantity;

    }

    /**
     * Returns Total Price.
     *
     * @return Total Price
     */
    public double getTotalPrice() {

        return quantity * product.getProductPrice();

    }

    /**
     * Returns Table Rows.
     *
     * @return Table Rows
     */
    public List<String[]> getTableRows() {

        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{
                "Cart ID",
                cartId
        });

        rows.add(new String[]{
                "Customer",
                customer.getUserName()
        });

        rows.add(new String[]{
                "Product ID",
                product.getProductId()
        });

        rows.add(new String[]{
                "Product Name",
                product.getProductName()
        });

        rows.add(new String[]{
                "Quantity",
                String.valueOf(quantity)
        });

        rows.add(new String[]{
                "Price",
                String.format("%.2f",
                        product.getProductPrice())
        });

        rows.add(new String[]{
                "Total",
                String.format("%.2f",
                        getTotalPrice())
        });

        return rows;

    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {

            return true;

        }

        if (!(object instanceof Cart cart)) {

            return false;

        }

        return Objects.equals(
                cartId,
                cart.cartId);

    }

    @Override
    public int hashCode() {

        return Objects.hash(cartId);

    }

}