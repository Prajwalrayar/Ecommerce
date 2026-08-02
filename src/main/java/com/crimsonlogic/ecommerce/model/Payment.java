package com.crimsonlogic.ecommerce.model;

import com.crimsonlogic.ecommerce.enums.PaymentMethod;
import com.crimsonlogic.ecommerce.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Payment made by a Customer.
 */
public class Payment {

    /**
     * Internal Payment ID.
     */
    private String paymentId;

    /**
     * UTR Number.
     */
    private String utrNumber;

    /**
     * Customer.
     */
    private Customer customer;

    /**
     * Order.
     */
    private Order order;

    /**
     * Payment Method.
     */
    private PaymentMethod paymentMethod;

    /**
     * Payment Status.
     */
    private PaymentStatus paymentStatus;

    /**
     * Amount Paid.
     */
    private double amount;

    /**
     * UPI ID.
     */
    private String upiId;

    /**
     * Payment Date.
     */
    private LocalDateTime paymentDate;

    /**
     * Parameterized Constructor.
     *
     * @param paymentId Payment ID
     * @param utrNumber UTR Number
     * @param customer Customer
     * @param order Order
     * @param paymentMethod Payment Method
     * @param paymentStatus Payment Status
     * @param amount Amount
     * @param upiId UPI ID
     * @param paymentDate Payment Date
     */
    public Payment(
            String paymentId,
            String utrNumber,
            Customer customer,
            Order order,
            PaymentMethod paymentMethod,
            PaymentStatus paymentStatus,
            double amount,
            String upiId,
            LocalDateTime paymentDate) {

        this.paymentId = paymentId;
        this.utrNumber = utrNumber;
        this.customer = customer;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
        this.upiId = upiId;
        this.paymentDate = paymentDate;

    }

    public String getPaymentId() {

        return paymentId;

    }

    public void setPaymentId(String paymentId) {

        this.paymentId = paymentId;

    }

    public String getUtrNumber() {

        return utrNumber;

    }

    public void setUtrNumber(String utrNumber) {

        this.utrNumber = utrNumber;

    }

    public Customer getCustomer() {

        return customer;

    }

    public void setCustomer(Customer customer) {

        this.customer = customer;

    }

    public Order getOrder() {

        return order;

    }

    public void setOrder(Order order) {

        this.order = order;

    }

    public PaymentMethod getPaymentMethod() {

        return paymentMethod;

    }

    public void setPaymentMethod(
            PaymentMethod paymentMethod) {

        this.paymentMethod = paymentMethod;

    }

    public PaymentStatus getPaymentStatus() {

        return paymentStatus;

    }

    public void setPaymentStatus(
            PaymentStatus paymentStatus) {

        this.paymentStatus = paymentStatus;

    }

    public double getAmount() {

        return amount;

    }

    public void setAmount(double amount) {

        this.amount = amount;

    }

    public String getUpiId() {

        return upiId;

    }

    public void setUpiId(String upiId) {

        this.upiId = upiId;

    }

    public LocalDateTime getPaymentDate() {

        return paymentDate;

    }

    public void setPaymentDate(
            LocalDateTime paymentDate) {

        this.paymentDate = paymentDate;

    }

    /**
     * Returns Payment Details.
     *
     * @return Payment Details
     */
    public List<String[]> getTableRows() {

        return List.of(

                new String[]{
                        "UTR Number",
                        utrNumber
                },

                new String[]{
                        "Customer",
                        customer.getUserName()
                },

                new String[]{
                        "Product",
                        order.getProduct().getProductName()
                },

                new String[]{
                        "Payment Method",
                        paymentMethod.name()
                },

                new String[]{
                        "Payment Status",
                        paymentStatus.name()
                },

                new String[]{
                        "Amount",
                        String.format("%.2f", amount)
                },

                new String[]{
                        "UPI ID",
                        upiId == null || upiId.isBlank()
                                ? "N/A"
                                : upiId
                },

                new String[]{
                        "Payment Date",
                        paymentDate.toString()
                }

        );

    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {

            return true;

        }

        if (!(object instanceof Payment payment)) {

            return false;

        }

        return Objects.equals(
                paymentId,
                payment.paymentId);

    }

    @Override
    public int hashCode() {

        return Objects.hash(paymentId);

    }

    @Override
    public String toString() {

        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", utrNumber='" + utrNumber + '\'' +
                ", customer=" + customer.getUserName() +
                ", order=" + order.getOrderId() +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                '}';

    }

}