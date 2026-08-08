package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.enums.OrderStatus;
import com.crimsonlogic.ecommerce.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {

    /**
     * Inserts Order.
     *
     * @param order Order
     */
    void insertOrder(Order order);

    /**
     * Updates Order.
     *
     * @param order Order
     */
    void updateOrder(Order order);

    /**
     * Deletes Order.
     *
     * @param orderId Order ID
     */
    void deleteOrder(@Param("orderId")String orderId);

    /**
     * Finds Order by ID.
     *
     * @param orderId Order ID
     * @return Order
     */
    Order findOrderById(@Param("orderId")String orderId);

    /**
     * Returns Customer Orders.
     *
     * @param customerId Customer ID
     * @return Order List
     */
    List<Order> findOrdersByCustomer(@Param("customerId")String customerId);

    /**
     * Returns Seller Orders.
     *
     * @param sellerId Seller ID
     * @return Order List
     */
    List<Order> findOrdersBySeller(@Param("sellerId") String sellerId);

    /**
     * Returns All Orders.
     *
     * @return Order List
     */
    List<Order> findAllOrders();

    /**
     * Updates Order Status.
     *
     * @param order Order
     */
    void updateOrderStatus(Order order);

    /**
     * Returns Seller Pending Approval Orders.
     *
     * @param sellerId Seller ID
     * @return Pending Approval Orders
     */
    List<Order> findPendingApprovalOrdersBySeller(

            @Param("sellerId")
            String sellerId

    );

    // Returns Orders that are not yet paid.

    List<Order> findOrdersWithoutPayment(@Param("customerId") String customerId);

    List<Order> findOrdersByStatus(@Param("status")
            OrderStatus status);

    List<Order> findOrdersByCustomerAndProduct(
            @Param("customerId") String customerId,
            @Param("productName") String productName);

    List<Order> findOrdersBySellerAndProduct(
            @Param("sellerId") String sellerId,
            @Param("productName") String productName);

    List<Order> findOrdersByKeyword(@Param("keyword") String keyword);

    Order findOrderByIdAndCustomer(@Param("orderId") String orderId,
            @Param("customerId") String customerId

    );


    List<Order> findCancelableOrders(String customerId);

    // Finds Seller Order by Tracking Number.
    Order findOrderByIdAndSeller(
            @Param("orderId") String orderId,
            @Param("sellerId") String sellerId );

}