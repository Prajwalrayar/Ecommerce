package com.crimsonlogic.ecommerce.model;

import com.crimsonlogic.ecommerce.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents an Order placed by a Customer.
 */
public class Order {

    /**
     * Order ID.
     */
    private String orderId;

    /**
     * Customer who placed the Order.
     */
    private Customer customer;

    /**
     * Ordered Product.
     */
    private Product product;

    /**
     * Quantity Ordered.
     */
    private int quantity;

    /**
     * Total Amount.
     */
    private double totalPrice;

    /**
     * Current Order Status.
     */
    private OrderStatus orderStatus;

    /**
     * Order Date and Time.
     */
    private LocalDateTime orderDate;

    /**
     * Parameterized Constructor.
     *
     * @param orderId Order ID
     * @param customer Customer
     * @param product Product
     * @param quantity Quantity
     * @param totalPrice Total Price
     * @param orderStatus Order Status
     * @param orderDate Order Date
     */
    public Order(
            String orderId,
            Customer customer,
            Product product,
            int quantity,
            double totalPrice,
            OrderStatus orderStatus,
            LocalDateTime orderDate) {

        this.orderId = orderId;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;

    }

    /**
     * Returns Order ID.
     *
     * @return Order ID
     */
    public String getOrderId() {

        return orderId;

    }

    /**
     * Sets Order ID.
     *
     * @param orderId Order ID
     */
    public void setOrderId(String orderId) {

        this.orderId = orderId;

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

        return totalPrice;

    }

    /**
     * Sets Total Price.
     *
     * @param totalPrice Total Price
     */
    public void setTotalPrice(double totalPrice) {

        this.totalPrice = totalPrice;

    }

    /**
     * Returns Order Status.
     *
     * @return Order Status
     */
    public OrderStatus getOrderStatus() {

        return orderStatus;

    }

    /**
     * Sets Order Status.
     *
     * @param orderStatus Order Status
     */
    public void setOrderStatus(OrderStatus orderStatus) {

        this.orderStatus = orderStatus;

    }

    /**
     * Returns Order Date.
     *
     * @return Order Date
     */
    public LocalDateTime getOrderDate() {

        return orderDate;

    }

    /**
     * Sets Order Date.
     *
     * @param orderDate Order Date
     */
    public void setOrderDate(LocalDateTime orderDate) {

        this.orderDate = orderDate;

    }
    /**
     * Returns Order Details.
     *
     * @return Order Details
     */
    public List<String[]> getTableRows() {

        return List.of(

                new String[]{
                        "Order ID",
                        orderId
                },

                new String[]{
                        "Customer",
                        customer.getUserName()
                },

                new String[]{
                        "Product",
                        product.getProductName()
                },

                new String[]{
                        "Quantity",
                        String.valueOf(quantity)
                },

                new String[]{
                        "Total Price",
                        String.format("%.2f", totalPrice)
                },

                new String[]{
                        "Order Status",
                        orderStatus.name()
                },

                new String[]{
                        "Order Date",
                        orderDate.toString()
                }

        );

    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof Order order)) {
            return false;
        }

        return Objects.equals(orderId, order.orderId);

    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId);

    }

    @Override
    public String toString() {

        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customer=" + customer.getUserName() +
                ", product=" + product.getProductName() +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", orderDate=" + orderDate +
                '}';

    }

}