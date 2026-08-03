package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.model.Payment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PaymentMapper {

    /**
     * Inserts Payment.
     *
     * @param payment Payment
     */
    void insertPayment(Payment payment);

    /**
     * Updates Payment.
     *
     * @param payment Payment
     */
    void updatePayment(Payment payment);

    /**
     * Deletes Payment.
     *
     * @param paymentId Payment ID
     */
    void deletePayment(@Param("paymentId") String paymentId);

    /**
     * Finds Payment by ID.
     *
     * @param paymentId Payment ID
     * @return Payment
     */
    Payment findPaymentById(@Param("paymentId") String paymentId);

    /**
     * Finds Payment by UTR Number.
     *
     * @param utrNumber UTR Number
     * @return Payment
     */
    Payment findPaymentByUtr(@Param("utrNumber") String utrNumber);

    /**
     * Returns Customer Payments.
     *
     * @param customerId Customer ID
     * @return Payment List
     */
    List<Payment> findPaymentsByCustomer(@Param("paymentId") String customerId);

    /**
     * Returns Seller Payments.
     *
     * @param sellerId Seller ID
     * @return Payment List
     */
    List<Payment> findPaymentsBySeller(@Param("paymentId") String sellerId);

    /**
     * Returns All Payments.
     *
     * @return Payment List
     */
    List<Payment> findAllPayments();

    /**
     * Updates Payment Status.
     *
     * @param payment Payment
     */
    void updatePaymentStatus(@Param("paymentId") Payment payment);

    Payment findPaymentByOrder(
            String orderId);

    List<Payment> findPaymentsByCustomerAndKeyword(

            @Param("customerId")
            String customerId,

            @Param("keyword")
            String keyword

    );

    List<Payment> findPaymentsBySellerAndKeyword(

            @Param("sellerId")
            String sellerId,

            @Param("keyword")
            String keyword

    );

    List<Payment> findPaymentsByKeyword(

            @Param("keyword")
            String keyword

    );

}