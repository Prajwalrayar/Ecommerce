package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.model.Review;
import org.apache.ibatis.annotations.Param;

public interface ReviewMapper {

    /**
     * Inserts Review.
     *
     * @param review Review
     */
    void insertReview(Review review);


    /**
     * Finds review by customer and order.
     *
     * @param customerId Customer ID
     * @param orderId Order ID
     * @return Review
     */
    Review findReviewByCustomerAndOrder(
            @Param("customerId")
            String customerId,

            @Param("orderId")
            String orderId);
}