package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.enums.PaymentStatus;
import com.crimsonlogic.ecommerce.mapper.PaymentMapper;
import com.crimsonlogic.ecommerce.model.Payment;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class PaymentDAO {

    /**
     * Inserts Payment.
     *
     * @param payment Payment
     */
    public void insertPayment(Payment payment) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            mapper.insertPayment(payment);

            session.commit();

        }

    }

    /**
     * Updates Payment.
     *
     * @param payment Payment
     */
    public void updatePayment(Payment payment) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            mapper.updatePayment(payment);

            session.commit();

        }

    }

    /**
     * Updates Payment Status.
     *
     * @param payment Payment
     */
    public void updatePaymentStatus(Payment payment) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            mapper.updatePaymentStatus(payment);

            session.commit();

        }

    }

    /**
     * Deletes Payment.
     *
     * @param paymentId Payment ID
     */
    public void deletePayment(String paymentId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            mapper.deletePayment(paymentId);

            session.commit();

        }

    }

    /**
     * Finds Payment by ID.
     *
     * @param paymentId Payment ID
     * @return Payment
     */
    public Payment findPaymentById(String paymentId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            return mapper.findPaymentById(paymentId);

        }

    }

    /**
     * Finds Payment by UTR Number.
     *
     * @param transactionId UTR Number
     * @return Payment
     */
    public Payment findPaymentByUtr(String transactionId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            return mapper.findPaymentByUtr(transactionId);

        }

    }

    /**
     * Finds Payment by Order.
     *
     * @param orderId Order ID
     * @return Payment
     */
    public Payment findPaymentByOrder(String orderId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            return mapper.findPaymentByOrder(orderId);

        }

    }

    /**
     * Returns Payments by Payment Status.
     *
     * @param paymentStatus Payment Status
     * @return Payment List
     */
    public List<Payment> findPaymentsByStatus(
            PaymentStatus paymentStatus) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper =
                    session.getMapper(PaymentMapper.class);

            return mapper.findPaymentsByStatus(
                    paymentStatus);
        }
    }

    /**
     * Returns Customer Payments.
     *
     * @param customerId Customer ID
     * @return Payment List
     */
    public List<Payment> findPaymentsByCustomer(String customerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            return mapper.findPaymentsByCustomer(customerId);

        }

    }

    /**
     * Returns Seller Payments.
     *
     * @param sellerId Seller ID
     * @return Payment List
     */
    public List<Payment> findPaymentsBySeller(String sellerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            return mapper.findPaymentsBySeller(sellerId);

        }

    }

    /**
     * Returns All Payments.
     *
     * @return Payment List
     */
    public List<Payment> findAllPayments() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper = session.getMapper(PaymentMapper.class);

            return mapper.findAllPayments();

        }

    }

    /**
     * Returns Customer Payments by Keyword.
     *
     * @param customerId Customer ID
     * @param keyword Keyword
     * @return Payment List
     */
    public List<Payment> findPaymentsByCustomerAndKeyword(
            String customerId,
            String keyword) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper =
                    session.getMapper(PaymentMapper.class);

            return mapper.findPaymentsByCustomerAndKeyword(
                    customerId,
                    keyword);

        }

    }

    /**
     * Returns Seller Payments by Keyword.
     *
     * @param sellerId Seller ID
     * @param keyword Keyword
     * @return Payment List
     */
    public List<Payment> findPaymentsBySellerAndKeyword(
            String sellerId,
            String keyword) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper =
                    session.getMapper(PaymentMapper.class);

            return mapper.findPaymentsBySellerAndKeyword(
                    sellerId,
                    keyword);

        }

    }

    /**
     * Returns Payments by Keyword.
     *
     * @param keyword Keyword
     * @return Payment List
     */
    public List<Payment> findPaymentsByKeyword(
            String keyword) {

        try (SqlSession session =
                     MyBatisUtil.getFactory().openSession()) {

            PaymentMapper mapper =
                    session.getMapper(PaymentMapper.class);

            return mapper.findPaymentsByKeyword(
                    keyword);

        }

    }

}