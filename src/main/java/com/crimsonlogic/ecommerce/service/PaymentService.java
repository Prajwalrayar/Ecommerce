package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.CustomerDAO;
import com.crimsonlogic.ecommerce.dao.OrderDAO;
import com.crimsonlogic.ecommerce.dao.PaymentDAO;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.enums.OrderStatus;
import com.crimsonlogic.ecommerce.enums.PaymentMethod;
import com.crimsonlogic.ecommerce.enums.PaymentStatus;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Order;
import com.crimsonlogic.ecommerce.model.Payment;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * Handles Payment operations.
 */
public class PaymentService {
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    // Default Constructor.

    public PaymentService() {

    }

    // Makes Payment.
    public void makePayment(Customer customer) {

        Order order =
                getPendingOrder(customer);

        if (order == null) {

            return;

        }

        if (paymentAlreadyExists(order)) {

            DisplayUtil.printMessage(
                    "Payment Already Completed.");

            return;

        }

        showPaymentMethods();

        String choice =
                InputUtil.readString(
                                "Enter Payment Method : ")
                        .trim()
                        .toLowerCase();

        switch (choice) {

            case "wallet":

                walletPayment(
                        customer,
                        order);

                break;

            case "upi":

                upiPayment(
                        customer,
                        order);

                break;

            case "debit card":

                cardPayment(
                        customer,
                        order,
                        PaymentMethod.DEBIT_CARD);

                break;

            case "net banking":

                netBankingPayment(
                        customer,
                        order);

                break;

            case "cash on delivery":

                cashOnDelivery(
                        customer,
                        order);

                break;

            default:

                DisplayUtil.printInvalidChoice();

        }

    }

    // Displays Customer Payments.

    public void viewPayments(Customer customer) {

        if (!validateCustomerPayments(customer)) {

            DisplayUtil.printMessage("No Payments Found.");
            return;
        }
        List<Payment> payments = paymentDAO.findPaymentsByCustomer(customer.getUserId());

        displayCustomerPayments(payments, "PAYMENT HISTORY");

    }

    /**
     * Searches Payment.
     *
     * @param customer Logged-in Customer
     */
    public void searchPayment(Customer customer) {

        if (!validateCustomerPayments(customer)) {

            DisplayUtil.printMessage(
                    "No Payments Found.");

            return;

        }
        String keyword =
                InputUtil.readString(
                                "Enter Product Name / UTR Number : ")
                        .trim();

        List<Payment> payments =
                paymentDAO.findPaymentsByCustomerAndKeyword(

                        customer.getUserId(),

                        keyword

                );

        if (payments.isEmpty()) {

            DisplayUtil.printMessage(
                    "Payment Not Found.");

            return;

        }
        displayCustomerPayments(

                payments,

                "SEARCH RESULT"

        );
    }
    /**
     * Wallet Payment.
     */
    private void walletPayment(Customer customer, Order order) {

        if (customer.getWalletBalance() < order.getTotalPrice()) {

            DisplayUtil.printMessage(
                    "Insufficient Wallet Balance.");

            return;

        }

        customer.setWalletBalance(

                customer.getWalletBalance()

                        - order.getTotalPrice()

        );

        customerDAO.updateCustomer(
                customer);

        createPayment(

                customer,

                order,

                PaymentMethod.WALLET,

                PaymentStatus.SUCCESS,

                null

        );

    }

    /**
     * UPI Payment.
     */
    private void upiPayment(Customer customer, Order order) {

        String upiId =
                InputUtil.readString(
                                "Enter UPI ID : ")
                        .trim();

        while (!isValidUpi(upiId)) {

            DisplayUtil.printMessage(
                    "Invalid UPI ID.");

            upiId = InputUtil.readString(
                    "Enter UPI ID : ");

        }

        createPayment(

                customer,

                order,

                PaymentMethod.UPI,

                PaymentStatus.SUCCESS,

                upiId);

    }

    /**
     * Card Payment.
     */
    private void cardPayment(Customer customer, Order order,
            PaymentMethod paymentMethod) {

        String cardNumber =
                InputUtil.readString(
                                "Enter Card Number : ")
                        .trim();

        String expiry =
                InputUtil.readString(
                                "Enter Expiry (MM/YY) : ")
                        .trim();

        String cvv =
                InputUtil.readString(
                                "Enter CVV : ")
                        .trim();

        while (!isValidCard(cardNumber)) {

            DisplayUtil.printMessage(
                    "Invalid Card Number.");

            cardNumber =
                    InputUtil.readString(
                            "Enter Card Number : ");

        }

        if (!isValidExpiry(expiry)) {

            DisplayUtil.printMessage(
                    "Invalid Expiry Date.");

            return;

        }

        if (!isValidCvv(cvv)) {

            DisplayUtil.printMessage(
                    "Invalid CVV.");

            return;

        }

        createPayment(

                customer,

                order,

                paymentMethod,

                PaymentStatus.SUCCESS,

                null);

    }

    /**
     * Net Banking Payment.
     */
    private void netBankingPayment(Customer customer, Order order) {

        InputUtil.readString(
                "Enter Bank Name : ");

        InputUtil.readString(
                "Enter Username : ");

        InputUtil.readString(
                "Enter Password : ");

        createPayment(

                customer,

                order,

                PaymentMethod.NET_BANKING,

                PaymentStatus.SUCCESS,

                null);

    }

    /**
     * Cash On Delivery Payment.
     */
    private void cashOnDelivery(Customer customer, Order order) {

        createPayment(

                customer,

                order,

                PaymentMethod.CASH_ON_DELIVERY,

                PaymentStatus.PENDING,

                null);

    }
    /**
     * Creates Payment.
     *
     * @param customer Customer
     * @param order Order
     * @param paymentMethod Payment Method
     * @param paymentStatus Payment Status
     * @param upiId UPI ID
     */
    private void createPayment(Customer customer, Order order,
            PaymentMethod paymentMethod, PaymentStatus paymentStatus, String upiId) {

        Payment payment = new Payment(IdGenerator.generateId("PAY"),
                generateUtrNumber(), customer, order, paymentMethod,
                paymentStatus, order.getTotalPrice(), upiId, LocalDateTime.now());

        paymentDAO.insertPayment(payment);

        if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY) {

            DisplayUtil.printSuccess(
                    "Cash On Delivery Selected.");

            DisplayUtil.printMessage(
                    "Payment Status : "
                            + payment.getPaymentStatus());

        } else {

            DisplayUtil.printSuccess("Payment Successful.");

            displayPayment(payment);

            if (paymentMethod == PaymentMethod.WALLET) {

                DisplayUtil.printMessage(
                        "Remaining Wallet Balance : ₹"
                                + String.format(
                                "%.2f",
                                customer.getWalletBalance()));

            }

        }

    }

    /**
     * Displays All Payments.
     */
    public void viewAllPayments() {

        if (!validatePayments()) {
            return;
        }

        displayAdminPayments(

                paymentDAO.findAllPayments(),

                "PAYMENT HISTORY"

        );

    }

    /**
     * Searches Payments.
     */
    public void searchPayment() {
        if (!validatePayments()) {
            return;
        }

        String keyword = InputUtil.readString("Enter Customer/Product : ").trim();

        List<Payment> payments =
                paymentDAO.findPaymentsByKeyword(keyword);

        if (payments.isEmpty()) {
            DisplayUtil.printMessage("Payment Not Found.");
            return;
        }
        displayAdminPayments(payments, "SEARCH RESULT");
    }

    /**
     * Returns Payment using UTR Number.
     *
     * @return Payment
     */
    private Payment getPaymentOrNull() {

        if (!validatePayments()) {

            return null;

        }

        String transactionId =
                InputUtil.readString(
                                "Enter UTR Number : ")
                        .trim();

        Payment payment =
                paymentDAO.findPaymentByUtr(
                        transactionId);

        if (payment == null) {

            DisplayUtil.printMessage(
                    "Payment Not Found.");

        }

        return payment;

    }

    /**
     * Refunds Payment.
     */
    public void refundPayment() {

        Payment payment =
                getPaymentOrNull();

        if (payment == null) {

            return;

        }

        if (payment.getOrder()
                .getOrderStatus()
                != OrderStatus.DELIVERED) {

            DisplayUtil.printMessage(
                    "Refund Allowed Only For Delivered Orders.");

            return;

        }

        if (payment.getPaymentStatus()
                == PaymentStatus.REFUNDED) {

            DisplayUtil.printMessage(
                    "Payment Already Refunded.");

            return;

        }

        payment.setPaymentStatus(
                PaymentStatus.REFUNDED);

        paymentDAO.updatePaymentStatus(
                payment);

        if (payment.getPaymentMethod()
                == PaymentMethod.WALLET) {

            Customer customer =
                    payment.getCustomer();

            customer.setWalletBalance(

                    customer.getWalletBalance()

                            + payment.getAmount());

            customerDAO.updateCustomer(
                    customer);

            DisplayUtil.printSuccess(
                    "Amount Credited To Wallet.");

        }

        DisplayUtil.printSuccess(
                "Refund Successful.");

    }

    // Checks whether Customer has Payments.
    private boolean validateCustomerPayments(Customer customer) {
        return !paymentDAO.findPaymentsByCustomer(customer.getUserId()).isEmpty();
    }

    /**
     * Returns Pending Order.
     *
     * @param customer Logged-in Customer
     * @return Order
     */
    private Order getPendingOrder(Customer customer) {

        List<Order> orders = orderDAO.findOrdersWithoutPayment(
                        customer.getUserId());

        if (orders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Pending Orders.");

            return null;

        }

        DisplayUtil.printTable(

                "PENDING ORDERS",

                new String[]{

                        "Tracking No",

                        "Product",

                        "Quantity",

                        "Amount"

                },

                buildPendingOrderRows(orders));

        String trackingNumber =
                InputUtil.readString(
                                "Enter Tracking Number : ")
                        .trim();

        return orders.stream()

                .filter(order ->
                        order.getOrderId()
                                .equals(trackingNumber))

                .findFirst()

                .orElse(null);

    }

    /**
     * Checks whether Payment already exists.
     *
     * @param order Order
     * @return true if Payment exists
     */
    private boolean paymentAlreadyExists(Order order) {

        return paymentDAO.findPaymentByOrder(order.getOrderId()) != null;

    }

    /**
     * Generates UTR Number.
     *
     * @return UTR Number
     */
    private String generateUtrNumber() {

        String utr;

        do {

            long number =
                    ThreadLocalRandom.current()
                            .nextLong(
                                    100000000000L,
                                    1000000000000L);

            utr = "UTR" + number;

        } while (paymentDAO.findPaymentByUtr(utr) != null);

        return utr;

    }

    /**
     * Displays Seller Payments.
     *
     * @param seller Logged-in Seller
     */
    public void viewSellerPayments(Seller seller) {

        List<Payment> payments = paymentDAO.findPaymentsBySeller(seller.getUserId());

        if (payments.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Payments Found.");

            return;

        }
        displaySellerPayments(

                payments,

                "SELLER PAYMENT HISTORY"

        );

    }

    /**
     * Searches Seller Payment.
     *
     * @param seller Logged-in Seller
     */
    public void searchSellerPayment(Seller seller) {

        String keyword = InputUtil.readString(
                "Enter Customer Name / Product / UTR Number : ").trim();

        List<Payment> payments =
                paymentDAO.findPaymentsBySellerAndKeyword(
                        seller.getUserId(),keyword
                );

        if (payments.isEmpty()) {
            DisplayUtil.printMessage("Payment Not Found.");
            return;
        }
        displaySellerPayments(payments, "SEARCH RESULT");

    }

    /**
     * Validates Payments.
     *
     * @return true if payments exist
     */
    private boolean validatePayments() {

        if (paymentDAO.findAllPayments().isEmpty()) {

            DisplayUtil.printMessage(
                    "No Payments Found.");

            return false;

        }

        return true;

    }

    /**
     * Builds Seller Payment Rows.
     *
     * @param payments Payments
     * @return Table Rows
     */
    private List<String[]> buildSellerPaymentRows(
            List<Payment> payments) {

        return payments.stream()

                .map(payment -> new String[]{

                        payment.getTransactionId(),

                        payment.getCustomer()
                                .getUserName(),

                        payment.getOrder()
                                .getProduct()
                                .getProductName(),

                        payment.getPaymentMethod()
                                .name(),

                        String.format(
                                "%.2f",
                                payment.getAmount()),

                        payment.getPaymentStatus()
                                .name()

                })

                .toList();

    }

    /**
     * Validates UPI ID.
     *
     * @param upiId UPI ID
     * @return true if valid
     */
    private boolean isValidUpi(
            String upiId) {

        return Pattern.matches(

                "^[a-zA-Z0-9._-]{2,}@(ybl|ibl|okhdfcbank|okaxis|oksbi|okicici|paytm|apl)$",

                upiId);

    }

    /**
     * Validates Card Number.
     *
     * @param cardNumber Card Number
     * @return true if valid
     */
    private boolean isValidCard(
            String cardNumber) {

        return cardNumber.matches(
                "^\\d{16}$");

    }

    /**
     * Validates CVV.
     *
     * @param cvv CVV
     * @return true if valid
     */
    private boolean isValidCvv(
            String cvv) {

        return cvv.matches(
                "^\\d{3}$");

    }

    /**
     * Validates Expiry.
     *
     * @param expiry Expiry
     * @return true if valid
     */
    private boolean isValidExpiry(
            String expiry) {

        return expiry.matches(
                "^(0[1-9]|1[0-2])/\\d{2}$");

    }
    /**
     * Builds Pending Order Table Rows.
     *
     * @param orders Orders
     * @return Table Rows
     */
    private List<String[]> buildPendingOrderRows(
            List<Order> orders) {

        return orders.stream()

                .map(order -> new String[]{

                        order.getOrderId(),

                        order.getProduct()
                                .getProductName(),

                        String.valueOf(
                                order.getQuantity()),

                        String.format(
                                "%.2f",
                                order.getTotalPrice())

                })

                .toList();

    }

    /**
     * Builds Customer Payment Rows.
     *
     * @param payments Payments
     * @return Table Rows
     */
    private List<String[]> buildPaymentRows(
            List<Payment> payments) {

        return payments.stream()

                .map(payment -> new String[]{

                        payment.getTransactionId(),

                        payment.getOrder()
                                .getProduct()
                                .getProductName(),

                        payment.getPaymentMethod()
                                .name(),

                        String.format(
                                "%.2f",
                                payment.getAmount()),

                        payment.getPaymentStatus()
                                .name()

                })

                .toList();

    }

    /**
     * Builds Admin Payment Rows.
     *
     * @param payments Payments
     * @return Table Rows
     */
    private List<String[]> buildAdminPaymentRows(
            List<Payment> payments) {

        return payments.stream()

                .map(payment -> new String[]{

                        payment.getTransactionId(),

                        payment.getCustomer()
                                .getUserName(),

                        payment.getOrder()
                                .getProduct()
                                .getProductName(),

                        payment.getPaymentMethod()
                                .name(),

                        String.format(
                                "%.2f",
                                payment.getAmount()),

                        payment.getPaymentStatus()
                                .name()

                })

                .toList();

    }

    /**
     * Displays Customer Payments.
     *
     * @param payments Payments
     * @param title Table Title
     */
    private void displayCustomerPayments(List<Payment> payments,
            String title) {

        if (payments.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Payments Found.");

            return;

        }

        DisplayUtil.printTable(

                title,

                new String[]{

                        "UTR Number",

                        "Product",

                        "Method",

                        "Amount",

                        "Status"

                },

                buildPaymentRows(
                        payments)

        );

    }

    /**
     * Displays Seller Payments.
     *
     * @param payments Payments
     * @param title Table Title
     */
    private void displaySellerPayments(List<Payment> payments, String title) {

        if (payments.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Payments Found.");

            return;

        }

        DisplayUtil.printTable(

                title,

                new String[]{

                        "UTR Number",

                        "Customer",

                        "Product",

                        "Method",

                        "Amount",

                        "Status"

                },

                buildSellerPaymentRows(
                        payments)

        );

    }

    /**
     * Displays Admin Payments.
     *
     * @param payments Payments
     * @param title Table Title
     */
    private void displayAdminPayments(List<Payment> payments, String title) {

        if (payments.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Payments Found.");

            return;

        }

        DisplayUtil.printTable(

                title,

                new String[]{

                        "UTR Number",

                        "Customer",

                        "Product",

                        "Method",

                        "Amount",

                        "Status"

                },

                buildAdminPaymentRows(
                        payments)

        );

    }

    /**
     * Displays Payment Details.
     *
     * @param payment Payment
     */
    private void displayPayment(
            Payment payment) {

        DisplayUtil.printTable(

                "PAYMENT DETAILS",

                new String[]{

                        "Field",

                        "Value"

                },

                payment.getTableRows());

    }

    /**
     * Displays Payment Methods.
     */
    private void showPaymentMethods() {

        System.out.println("\n==========================================");
        System.out.println("            PAYMENT METHODS");
        System.out.println("==========================================");
        System.out.println("WALLET");
        System.out.println("UPI");
        System.out.println("DEBIT CARD");
        System.out.println("NET BANKING");
        System.out.println("CASH ON DELIVERY");
        System.out.println("==========================================");

    }

}