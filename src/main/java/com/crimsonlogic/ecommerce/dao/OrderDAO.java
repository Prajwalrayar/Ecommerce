package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.enums.OrderStatus;
import com.crimsonlogic.ecommerce.mapper.OrderMapper;
import com.crimsonlogic.ecommerce.model.Order;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class OrderDAO {

    /**
     * Inserts Order.
     *
     * @param order Order
     */
    public void insertOrder(Order order) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            mapper.insertOrder(order);

            session.commit();

        }

    }

    /**
     * Updates Order.
     *
     * @param order Order
     */
    public void updateOrder(Order order) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            mapper.updateOrder(order);

            session.commit();

        }

    }

    /**
     * Updates Order Status.
     *
     * @param order Order
     */
    public void updateOrderStatus(Order order) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            mapper.updateOrderStatus(order);

            session.commit();

        }

    }

    /**
     * Deletes Order.
     *
     * @param orderId Order ID
     */
    public void deleteOrder(String orderId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            mapper.deleteOrder(orderId);

            session.commit();

        }

    }

    /**
     * Finds Order by ID.
     *
     * @param orderId Order ID
     * @return Order
     */
    public Order findOrderById(String orderId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            return mapper.findOrderById(orderId);

        }

    }

    /**
     * Returns Customer Orders.
     *
     * @param customerId Customer ID
     * @return Order List
     */
    public List<Order> findOrdersByCustomer(String customerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            return mapper.findOrdersByCustomer(customerId);

        }

    }

    /**
     * Returns Seller Orders.
     *
     * @param sellerId Seller ID
     * @return Order List
     */
    public List<Order> findOrdersBySeller(String sellerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            return mapper.findOrdersBySeller(sellerId);

        }

    }

    /**
     * Returns Orders without Payment.
     *
     * @param customerId Customer ID
     * @return Order List
     */
    public List<Order> findOrdersWithoutPayment(String customerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            return mapper.findOrdersWithoutPayment(customerId);

        }

    }

    /**
     * Returns Orders by Status.
     *
     * @param status Order Status
     * @return Order List
     */
    public List<Order> findOrdersByStatus(OrderStatus status) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            return mapper.findOrdersByStatus(status);

        }

    }

    // Finds Customer Order by Tracking Number.

    // Returns All Orders.

    public List<Order> findAllOrders() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper = session.getMapper(OrderMapper.class);

            return mapper.findAllOrders();

        }

    }

    // Returns Customer Orders by Product.
    public List<Order> findOrdersByCustomerAndProduct(String customerId,
                                                      String productName) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper =
                    session.getMapper(OrderMapper.class);

            return mapper.findOrdersByCustomerAndProduct(

                    customerId,

                    productName

            );

        }

    }

    public List<Order> findCancelableOrders(String customerId) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper =
                    session.getMapper(OrderMapper.class);

            return mapper.findCancelableOrders(customerId);

        }

    }


    /**
     * Returns Pending Approval Orders of Seller.
     *
     * @param sellerId Seller ID
     * @return Pending Approval Orders
     */
    public List<Order> findPendingApprovalOrdersBySeller(String sellerId) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper =
                    session.getMapper(OrderMapper.class);

            return mapper.findPendingApprovalOrdersBySeller(
                    sellerId);

        }

    }
    // Returns Seller Orders by Product.
    public List<Order> findOrdersBySellerAndProduct(String sellerId,
                                                    String productName) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper =
                    session.getMapper(OrderMapper.class);

            return mapper.findOrdersBySellerAndProduct(

                    sellerId,

                    productName

            );

        }

    }


    public Order findOrderByIdAndCustomer(String orderId, String customerId) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper =
                    session.getMapper(OrderMapper.class);

            return mapper.findOrderByIdAndCustomer(
                    orderId,
                    customerId);

        }

    }

    // Finds Seller Order by Tracking Number.

    public Order findOrderByIdAndSeller(String orderId, String sellerId) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper =
                    session.getMapper(OrderMapper.class);

            return mapper.findOrderByIdAndSeller(
                    orderId,
                    sellerId);

        }

    }

    // Searches Orders.

    public List<Order> findOrdersByKeyword(String keyword) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            OrderMapper mapper =
                    session.getMapper(OrderMapper.class);

            return mapper.findOrdersByKeyword(
                    keyword);

        }

    }
}