package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.OrderDAO;
import com.crimsonlogic.ecommerce.dao.ReviewDAO;
import com.crimsonlogic.ecommerce.enums.OrderStatus;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Order;
import com.crimsonlogic.ecommerce.model.Review;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewService {

    private final OrderDAO orderDAO =
            new OrderDAO();

    private final ReviewDAO reviewDAO =
            new ReviewDAO();


    public void addReview(
            Customer customer) {

        List<Order> deliveredOrders =
                orderDAO
                        .findOrdersByCustomer(
                                customer.getUserId())
                        .stream()
                        .filter(order ->
                                order.getOrderStatus()
                                        == OrderStatus.DELIVERED)
                        .toList();

        if (deliveredOrders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Delivered Orders Available For Review.");

            return;
        }

        DisplayUtil.printTable(
                "DELIVERED PRODUCTS",

                new String[]{
                        "Tracking No",
                        "Product",
                        "Quantity",
                        "Status"
                },

                buildOrderRows(
                        deliveredOrders));


        String trackingNumber =
                InputUtil.readString(
                                "Enter Tracking Number : ")
                        .trim();


        Order order =
                deliveredOrders.stream()
                        .filter(currentOrder ->
                                currentOrder
                                        .getOrderId()
                                        .equalsIgnoreCase(
                                                trackingNumber))
                        .findFirst()
                        .orElse(null);


        if (order == null) {

            DisplayUtil.printMessage(
                    "Delivered Order Not Found.");

            return;
        }


        Review existingReview =
                reviewDAO
                        .findReviewByCustomerAndOrder(
                                customer.getUserId(),
                                order.getOrderId());


        if (existingReview != null) {

            DisplayUtil.printMessage(
                    "You Have Already Reviewed This Order.");

            return;
        }


        int rating =
                readRating();


        String reviewText =
                readReview();


        Review review =
                createReview(
                        customer,
                        order,
                        rating,
                        reviewText);


        reviewDAO.insertReview(review);


        DisplayUtil.printSuccess(
                "Review Submitted Successfully.");
    }


    private List<String[]> buildOrderRows(
            List<Order> orders) {

        return orders.stream()
                .map(order ->
                        new String[]{
                                order.getOrderId(),

                                order.getProduct()
                                        .getProductName(),

                                String.valueOf(
                                        order.getQuantity()),

                                order.getOrderStatus()
                                        .name()
                        })
                .toList();
    }


    private int readRating() {

        while (true) {

            int rating =
                    InputUtil.readInt(
                            "Enter Rating (1-5) : ");

            if (rating >= 1
                    && rating <= 5) {

                return rating;
            }

            DisplayUtil.printMessage(
                    "Rating Must Be Between 1 And 5.");
        }
    }


    private String readReview() {

        while (true) {

            String review =
                    InputUtil.readString(
                                    "Enter Review : ")
                            .trim();

            if (!review.isEmpty()) {
                return review;
            }

            DisplayUtil.printMessage(
                    "Review Cannot Be Empty.");
        }
    }


    private Review createReview(
            Customer customer,
            Order order,
            int rating,
            String reviewText) {

        Review review =
                new Review();

        review.setReviewId(
                IdGenerator.generateId("REV"));

        review.setOrder(order);

        review.setProduct(
                order.getProduct());

        review.setCustomer(customer);

        review.setRating(rating);

        review.setReviewText(
                reviewText);

        review.setReviewDate(
                LocalDateTime.now());

        return review;
    }
}