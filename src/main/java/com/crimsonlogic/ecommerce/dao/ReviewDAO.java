package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.mapper.ReviewMapper;
import com.crimsonlogic.ecommerce.model.Review;
import org.apache.ibatis.session.SqlSession;

public class ReviewDAO {

    public void insertReview(
            Review review) {

        try (SqlSession session =
                     MyBatisUtil.getFactory()
                             .openSession()) {

            ReviewMapper mapper =
                    session.getMapper(
                            ReviewMapper.class);

            mapper.insertReview(review);

            session.commit();
        }
    }


    public Review findReviewByCustomerAndOrder(
            String customerId,
            String orderId) {

        try (SqlSession session =
                     MyBatisUtil.getFactory()
                             .openSession()) {

            ReviewMapper mapper =
                    session.getMapper(
                            ReviewMapper.class);

            return mapper
                    .findReviewByCustomerAndOrder(
                            customerId,
                            orderId);
        }
    }
}