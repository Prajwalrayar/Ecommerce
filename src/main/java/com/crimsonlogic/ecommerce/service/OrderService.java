package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.*;
import com.crimsonlogic.ecommerce.enums.OrderStatus;
import com.crimsonlogic.ecommerce.enums.PaymentMethod;
import com.crimsonlogic.ecommerce.enums.PaymentStatus;
import com.crimsonlogic.ecommerce.enums.ProductStatus;
import com.crimsonlogic.ecommerce.model.*;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class responsible for Order operations.
 */
public class OrderService {

    /**
     * Cart Service.
     */
    private final CartService cartService;

    /**
     * Inventory Service.
     */
    private final InventoryService inventoryService;

    /**
     * Cart DAO.
     */
    private final CartDAO cartDAO;

    /**
     * Order DAO.
     */
    private final OrderDAO orderDAO;

   // Inventory DAO.

    private final InventoryDAO inventoryDAO;


    /**
     * Payment DAO.
     */
    private final PaymentDAO paymentDAO;

    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ProductDAO productDAO = new ProductDAO();

    // Customer Order Table Headers.

    private static final String[] CUSTOMER_HEADERS = {
            "Tracking No",
            "Product",
            "Quantity",
            "Amount",
            "Status",
            "Order Date"
    };

    // Seller Order Table Headers.

    private static final String[] SELLER_HEADERS = {
            "Tracking No",
            "Customer",
            "Product",
            "Quantity",
            "Amount",
            "Status"
    };

    /**
     * Admin Order Table Headers.
     */
    private static final String[] ADMIN_HEADERS = {
            "Tracking No",
            "Customer",
            "Seller",
            "Product",
            "Quantity",
            "Amount",
            "Status"
    };

    /**
     * Default Constructor.
     */
    public OrderService() {

        this.cartService =
                new CartService();

        this.inventoryService =
                new InventoryService();

        this.cartDAO =
                new CartDAO();

        this.orderDAO =
                new OrderDAO();

        this.inventoryDAO =
                new InventoryDAO();

        this.paymentDAO =
                new PaymentDAO();

    }

    /**
     * Places Order.
     *
     * @param customer Customer
     */
    public void placeOrder(Customer customer) {

        if (!validateCustomerCart(customer)) {

            return;

        }

        cartService.viewCart(customer);

        Cart cart =
                selectCustomerCart(customer);

        if (cart == null) {

            return;

        }

        createOrder(cart);

    }

    // Displays Customer Orders.

    public void viewOrders(Customer customer) {

        displayCustomerOrders(

                orderDAO.findOrdersByCustomer(
                        customer.getUserId()),

                "MY ORDERS"

        );

    }

    /**
     * Tracks Customer Order.
     *
     * @param customer Customer
     */
    public void trackOrder(Customer customer) {

        if (!validateCustomerOrders(customer)) {

            return;

        }

        trackOrderDetails(

                getCustomerOrderOrNull(customer)

        );

    }
    /**
     * Cancels Order.
     *
     * @param customer Customer
     */
    public void cancelOrder(Customer customer) {

        if (!validateCustomerOrders(customer)) {

            return;

        }

        viewCancelableOrders(customer);

        Order order =
                getCustomerOrderOrNull(customer);

        if (order == null) {

            return;

        }

        cancelExistingOrder(order);

    }

    /**
     * Displays Seller Orders.
     *
     * @param seller Seller
     */
    public void viewSellerOrders(Seller seller) {

        displaySellerOrders(

                orderDAO.findOrdersBySeller(
                        seller.getUserId()),

                "MY ORDERS"

        );

    }

    /**
     * Creates Order.
     *
     * @param cart Customer Cart
     */
    private void createOrder(Cart cart) {

        Inventory inventory =
                inventoryService.findInventoryByProduct(
                        cart.getProduct());

        if (!validateInventory(
                inventory,
                cart.getQuantity())) {

            return;

        }

        Order order =
                new Order(

                        generateTrackingNumber(),

                        cart.getCustomer(),

                        cart.getProduct(),

                        cart.getQuantity(),

                        cart.getTotalPrice(),

                        OrderStatus.PENDING_APPROVAL,

                        LocalDateTime.now()

                );

        orderDAO.insertOrder(order);

        Payment payment =
                createPayment(order);

        if (payment == null) {

            orderDAO.deleteOrder(
                    order.getOrderId());

            DisplayUtil.printMessage(
                    "Payment Cancelled.");

            return;

        }

        cartDAO.deleteCartItem(
                cart.getCartId());

        DisplayUtil.printSuccess(
                "Order Placed Successfully.");

        System.out.println(
                "Tracking Number : "
                        + order.getOrderId());

        if (payment.getPaymentMethod()
                == PaymentMethod.CASH_ON_DELIVERY) {

            DisplayUtil.printMessage(
                    "Order Waiting For Seller/Admin Approval.");

        } else {

            DisplayUtil.printSuccess(
                    "Payment Successful.");

            DisplayUtil.printMessage(
                    "Order Waiting For Seller/Admin Approval.");

        }

    }

    /**
     * Creates Payment.
     *
     * @param order Order
     * @return Payment
     */
    private Payment createPayment(Order order) {

        Customer customer =
                order.getCustomer();

        PaymentMethod paymentMethod =
                choosePaymentMethod();

        if (paymentMethod == null) {

            return null;

        }

        double amount =
                order.getTotalPrice();

        PaymentStatus paymentStatus;

        String transactionId =
                IdGenerator.generateId("TXN");

        String upi = null;

        //----------------------------------------------------
        // WALLET
        //----------------------------------------------------

        if (paymentMethod == PaymentMethod.WALLET) {

            if (customer.getWalletBalance() >= amount) {

                customer.setWalletBalance(

                        customer.getWalletBalance()
                                - amount

                );

                customerDAO.updateWalletBalance(customer);

                paymentStatus =
                        PaymentStatus.SUCCESS;

                DisplayUtil.printSuccess(
                        "Wallet Payment Successful.");

            }

            else {

                double remaining =
                        amount -
                                customer.getWalletBalance();

                System.out.println();

                DisplayUtil.printMessage(
                        "Insufficient Wallet Balance.");

                System.out.println(
                        "Wallet Balance : ₹"
                                + customer.getWalletBalance());

                System.out.println(
                        "Required : ₹"
                                + amount);

                System.out.println(
                        "Short Amount : ₹"
                                + remaining);

                System.out.println();
                System.out.println("1. Add Money To Wallet");
                System.out.println("2. Choose Another Payment");
                System.out.println("3. Cancel");

                int option =
                        InputUtil.readInt(
                                "Enter Choice : ");

                switch (option) {

                    case 1:

                        rechargeWallet(
                                customer,
                                remaining);

                        return createPayment(order);

                    case 2:

                        return createPayment(order);

                    default:

                        return null;

                }

            }

        }

        //----------------------------------------------------
        // COD
        //----------------------------------------------------

        else if (paymentMethod
                == PaymentMethod.CASH_ON_DELIVERY) {

            paymentStatus =
                    PaymentStatus.PENDING;

        }

        //----------------------------------------------------
        // UPI / CARD / NET BANKING
        //----------------------------------------------------

        else {

            if (paymentMethod
                    == PaymentMethod.UPI) {

                upi =
                        InputUtil.readString(
                                "Enter UPI ID : ");

            }

            transactionId =
                    IdGenerator.generateId("TXN");

            paymentStatus =
                    PaymentStatus.SUCCESS;

            DisplayUtil.printSuccess(
                    "Payment Successful.");

        }

        Payment payment =
                new Payment(

                        IdGenerator.generateId("PAY"),

                        transactionId,

                        customer,

                        order,

                        paymentMethod,

                        paymentStatus,

                        amount,

                        upi,

                        LocalDateTime.now()

                );

        paymentDAO.insertPayment(payment);

        return payment;

    }
    /**
     * Cancels Existing Order.
     *
     * @param order Order
     */
    private void cancelExistingOrder(Order order) {

        if (order == null) {

            return;

        }

        if (order.getOrderStatus()
                == OrderStatus.DELIVERED) {

            DisplayUtil.printMessage(
                    "Delivered Order Cannot Be Cancelled.");

            return;

        }

        if (order.getOrderStatus()
                == OrderStatus.CANCELLED) {

            DisplayUtil.printMessage(
                    "Order Already Cancelled.");

            return;

        }

        if (order.getOrderStatus()
                == OrderStatus.CONFIRMED) {

            Inventory inventory =
                    inventoryService.findInventoryByProduct(
                            order.getProduct());

            updateInventory(
                    inventory,
                    order.getQuantity());

        }

        order.setOrderStatus(
                OrderStatus.CANCELLED);

        orderDAO.updateOrderStatus(
                order);

        Payment payment =
                getPaymentByOrder(order);

        if (payment != null
                &&
                payment.getPaymentStatus()
                        == PaymentStatus.SUCCESS) {

            payment.setPaymentStatus(
                    PaymentStatus.REFUNDED);

            paymentDAO.updatePaymentStatus(
                    payment);

        }

        DisplayUtil.printSuccess(
                "Order Cancelled Successfully.");

    }

    /**
     * Tracks Seller Order.
     *
     * @param seller Seller
     */
    public void trackOrder(Seller seller) {

        if (!validateSellerOrders(
                seller)) {

            return;

        }

        viewSellerOrders(
                seller);

        displayOrderDetails(

                getSellerOrderOrNull(
                        seller)

        );

    }

    /**
     * Displays Order Details.
     *
     * @param order Order
     */
    private void displayOrderDetails(Order order) {

        if (order == null) {

            return;

        }

        displayOrder(order);

    }

    /**
     * Validates Inventory.
     *
     * @param inventory Inventory
     * @param quantity Quantity
     * @return true if valid
     */
    private boolean validateInventory(Inventory inventory, int quantity) {

        if (inventory == null) {

            DisplayUtil.printMessage(
                    "Inventory Not Available.");

            return false;

        }

        if (inventory.getQuantity()
                < quantity) {

            DisplayUtil.printMessage(
                    "Insufficient Stock.");

            return false;

        }

        return true;

    }
    /**
     * Updates Inventory.
     *
     * @param inventory Inventory
     * @param quantity Quantity
     */
    private void updateInventory(Inventory inventory, int quantity) {

        inventory.setQuantity(
                inventory.getQuantity() + quantity);

        if (inventory.getQuantity() < 0) {

            inventory.setQuantity(0);

        }

        inventoryDAO.updateQuantity(
                inventory);

        Product product =
                inventory.getProduct();

        if (inventory.getQuantity() == 0) {

            product.setProductStatus(
                    ProductStatus.OUT_OF_STOCK);

        } else {

            product.setProductStatus(
                    ProductStatus.AVAILABLE);

        }

        productDAO.updateProductStatus(
                product);

    }

    /**
     * Validates Order Cancellation.
     *
     * @param order Order
     * @return true if order can be cancelled
     */
    private boolean validateCancellation(Order order) {

        switch (order.getOrderStatus()) {

            case SHIPPED:

            case IN_TRANSIT:

            case OUT_FOR_DELIVERY:

            case DELIVERED:

                DisplayUtil.printMessage("Order Cannot Be Cancelled.");

                return false;

            case CANCELLED:

                DisplayUtil.printMessage("Order Already Cancelled.");

                return false;

            default:

                return true;

        }

    }

    /**
     * Returns Customer Cart.
     *
     * @param customer Customer
     * @return Cart
     */
    private Cart selectCustomerCart(Customer customer) {

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        Product product =
                productDAO.findProductByName(
                        productName);

        if (product == null) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

            return null;

        }

        Cart cart =
                cartDAO.findCartItem(

                        customer.getUserId(),

                        product.getProductId()

                );

        if (cart == null) {

            DisplayUtil.printMessage(
                    "Cart Item Not Found.");

        }

        return cart;

    }

    /**
     * Displays Customer Orders.
     *
     * @param orders Order List
     * @param title Table Title
     */
    private void displayCustomerOrders(List<Order> orders,
            String title) {

        if (orders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        DisplayUtil.printTable(

                title,

                CUSTOMER_HEADERS,

                buildCustomerOrderRows(
                        orders)

        );

    }
    /**
     * Displays Seller Orders.
     *
     * @param orders Order List
     * @param title Table Title
     */
    private void displaySellerOrders(
            List<Order> orders, String title) {

        if (orders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        DisplayUtil.printTable(

                title,

                SELLER_HEADERS,

                buildSellerOrderRows(
                        orders)

        );

    }
    /**
     * Displays All Orders.
     *
     * @param orders Order List
     * @param title Table Title
     */
    private void displayAdminOrders(
            List<Order> orders, String title) {

        if (orders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        DisplayUtil.printTable(

                title,

                ADMIN_HEADERS,

                buildAdminOrderRows(
                        orders)

        );

    }
    /**
     * Displays Order Details.
     *
     * @param order Order
     */
    private void trackOrderDetails(Order order) {

        if (order == null) {

            return;

        }

        displayOrder(order);

    }

    /**
     * Validates Customer Cart.
     *
     * @param customer Customer
     * @return true if Cart exists
     */
    private boolean validateCustomerCart(
            Customer customer) {

        if (cartDAO.findCartByCustomer(
                customer.getUserId()).isEmpty()) {

            DisplayUtil.printMessage(
                    "Your Cart is Empty.");

            return false;

        }

        return true;

    }

    /**
     * Validates Customer Orders.
     *
     * @param customer Customer
     * @return true if Orders exist
     */
    private boolean validateCustomerOrders(
            Customer customer) {

        if (orderDAO.findOrdersByCustomer(
                customer.getUserId()).isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return false;

        }

        return true;

    }

    /**
     * Validates Seller Orders.
     *
     * @param seller Seller
     * @return true if Orders exist
     */
    private boolean validateSellerOrders(Seller seller) {

        if (orderDAO.findOrdersBySeller(
                seller.getUserId()).isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return false;

        }

        return true;

    }

    // Confirms Order.
    public void confirmOrder(Seller seller) {
        if (!validateSellerOrders(seller)) {
            return;
        }
        viewSellerOrders(seller);
        Order order = getSellerOrderOrNull(seller);
        if (order == null) {
            return;
        }
        confirmExistingOrder(order);
    }

    // Updates Order Status.

    public void updateOrderStatus(Seller seller) {
        if (!validateSellerOrders(seller)) {
            return;
        }
        viewSellerOrders(seller);
        Order order = getSellerOrderOrNull(seller);
        if (order == null) {
            return;
        }
        updateExistingOrderStatus(order);
    }
    // Displays All Orders.

    public void viewAllOrders() {

        displayAdminOrders(

                orderDAO.findAllOrders(),

                "ALL ORDERS"

        );

    }

    /**
     * Tracks Order.
     */
    public void trackOrder() {

        if (!validateOrders()) {

            return;

        }

        viewAllOrders();

        trackOrderDetails(

                getOrderOrNull()

        );

    }

    private void viewCancelableOrders(Customer customer) {

        List<Order> orders =
                orderDAO.findCancelableOrders(
                        customer.getUserId());

        displayCustomerOrders(orders,"MY ORDERS");

    }

    /**
     * Confirms Order.
     */
    public void confirmOrder() {

        if (!validateOrders()) {

            return;

        }

        viewAllOrders();

        confirmExistingOrder(

                getOrderOrNull()

        );

    }

    /**
     * Updates Order Status.
     */
    public void updateOrderStatus() {
        if (!validateOrders()) {
            return;
        }
        viewAllOrders();
        updateOrderStatusDetails(getOrderOrNull());
    }

    //* Deletes Order.

    public void deleteOrder() {

        if (!validateOrders()) {

            return;

        }

        viewAllOrders();

        deleteExistingOrder(

                getOrderOrNull()

        );

    }

    // Updates Order Status.

    private void updateOrderStatusDetails(Order order) {

        if (order == null) {

            return;

        }

        updateExistingOrderStatus(order);

    }

    // Deletes Existing Order.

    private void deleteExistingOrder(Order order) {

        if (order == null) {

            return;

        }

        orderDAO.deleteOrder(
                order.getOrderId());

        DisplayUtil.printSuccess(
                "Order Deleted Successfully.");

    }

    // Validates Orders.

    private boolean validateOrders() {

        if (orderDAO.findAllOrders()
                .isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return false;

        }

        return true;

    }

    //Confirms Existing Order.

    private void confirmExistingOrder(Order order) {

        if (order.getOrderStatus()
                != OrderStatus.PENDING_APPROVAL) {

            DisplayUtil.printMessage(
                    "Only Pending Approval Orders Can Be Confirmed.");

            return;

        }

        Payment payment =
                getPaymentByOrder(order);

        if (payment == null) {

            DisplayUtil.printMessage(
                    "Payment Record Not Found.");

            return;

        }

        if (payment.getPaymentMethod()
                != PaymentMethod.CASH_ON_DELIVERY

                &&

                payment.getPaymentStatus()
                        != PaymentStatus.SUCCESS) {

            DisplayUtil.printMessage(
                    "Payment Not Completed.");

            return;

        }

        order.setOrderStatus(
                OrderStatus.CONFIRMED);

        orderDAO.updateOrderStatus(order);

        Inventory inventory =
                inventoryService.findInventoryByProduct(
                        order.getProduct());

        updateInventory(
                inventory,
                -order.getQuantity());

        DisplayUtil.printSuccess(
                "Order Confirmed Successfully.");

    }

    /**
     * Updates Existing Order Status.
     *
     * @param order Order
     */
    private void updateExistingOrderStatus(Order order) {

        switch (order.getOrderStatus()) {

            case CONFIRMED:

                order.setOrderStatus(
                        OrderStatus.SHIPPED);

                break;

            case SHIPPED:

                order.setOrderStatus(
                        OrderStatus.IN_TRANSIT);

                break;

            case IN_TRANSIT:

                order.setOrderStatus(
                        OrderStatus.OUT_FOR_DELIVERY);

                break;

            case OUT_FOR_DELIVERY:

                order.setOrderStatus(
                        OrderStatus.DELIVERED);

                break;

            case DELIVERED:

                DisplayUtil.printMessage(
                        "Order Already Delivered.");


                return;

            case CANCELLED:

                DisplayUtil.printMessage(
                        "Cancelled Order Cannot Be Updated.");

                return;

            default:

                DisplayUtil.printMessage(
                        "Order Must Be Confirmed First.");

                return;

        }

        orderDAO.updateOrderStatus(order);

        DisplayUtil.printSuccess(
                "Order Status Updated Successfully.");

    }

    // Returns Customer Cart.
    private Cart getCustomerCartOrNull(Customer customer) {

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        Product product =
                productDAO.findProductByName(
                        productName);

        if (product == null) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

            return null;

        }

        Cart cart =
                cartDAO.findCartItem(
                        customer.getUserId(),
                        product.getProductId());

        if (cart == null) {

            DisplayUtil.printMessage(
                    "Cart Item Not Found.");

        }

        return cart;

    }

    /**
     * Returns Customer Order.
     *
     * @param customer Logged-in Customer
     * @return Order
     */
    private Order getCustomerOrderOrNull(Customer customer) {

        String trackingNumber =
                InputUtil.readString(
                                "Enter Tracking Number : ")
                        .trim();

        Order order =
                orderDAO.findOrderByIdAndCustomer(

                        trackingNumber,

                        customer.getUserId()

                );

        if (order == null) {

            DisplayUtil.printMessage(
                    "Order Not Found.");

        }

        return order;

    }

    // Returns Seller Order.
    private Order getSellerOrderOrNull(Seller seller) {

        String trackingNumber =
                InputUtil.readString(
                                "Enter Tracking Number : ")
                        .trim();

        Order order =
                orderDAO.findOrderByIdAndSeller(

                        trackingNumber,

                        seller.getUserId()

                );

        if (order == null) {

            DisplayUtil.printMessage(
                    "Order Not Found.");

        }

        return order;

    }

    // Returns Order.

    private Order getOrderOrNull() {

        String trackingNumber =
                InputUtil.readString(
                                "Enter Tracking Number : ")
                        .trim();

        Order order =
                orderDAO.findOrderById(
                        trackingNumber);

        if (order == null) {

            DisplayUtil.printMessage(
                    "Order Not Found.");

        }

        return order;

    }

    // Searches Customer Orders.
    public void searchOrder(Customer customer) {

        if (!validateCustomerOrders(customer)) {
            return;
        }

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        displayCustomerOrders(

                orderDAO.findOrdersByCustomerAndProduct(

                        customer.getUserId(),

                        productName

                ),

                "SEARCH RESULT"

        );

    }

    // Searches Seller Orders.
    public void searchOrder(Seller seller) {

        if (!validateSellerOrders(seller)) {

            return;

        }

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        displaySellerOrders(

                orderDAO.findOrdersBySellerAndProduct(

                        seller.getUserId(),

                        productName

                ),

                "SEARCH RESULT"

        );

    }

    // Searches Orders.
    public void searchOrder() {

        if (!validateOrders()) {

            return;

        }

        String keyword =
                InputUtil.readString(
                                "Enter Customer/Product Name : ")
                        .trim();

        displayAdminOrders(

                orderDAO.findOrdersByKeyword(
                        keyword),

                "SEARCH RESULT"

        );

    }

    // Returns Payment of an Order.

    private Payment getPaymentByOrder(Order order) {

        return paymentDAO.findPaymentByOrder(
                order.getOrderId());

    }

    // Generates Tracking Number.

    private String generateTrackingNumber() {

        String trackingNumber;

        do {

            trackingNumber =
                    IdGenerator.generateId("TRK");

        }

        while(orderDAO.findOrderById(trackingNumber)!=null);

        return trackingNumber;

    }

    // Displays Order Details.

    private void displayOrder(Order order) {

        DisplayUtil.printTable(

                "ORDER DETAILS",

                new String[]{

                        "Field",

                        "Value"

                },

                order.getTableRows());

    }

    private void rechargeWallet(

            Customer customer,

            double requiredAmount) {

        System.out.println();

        System.out.println("Recharge Wallet");

        System.out.println("1. UPI");
        System.out.println("2. DEBIT CARD");
        System.out.println("3. NET BANKING");

        int choice =
                InputUtil.readInt(
                        "Choose Method : ");

        if (choice < 1 || choice > 3) {

            DisplayUtil.printInvalidChoice();

            return;

        }

        double recharge =
                InputUtil.readDouble(
                        "Enter Amount : ");

        while (recharge < requiredAmount) {

            DisplayUtil.printMessage(

                    "Minimum Recharge Amount : ₹"

                            + requiredAmount

            );

            recharge =
                    InputUtil.readDouble(
                            "Enter Amount : ");

        }

        customer.setWalletBalance(

                customer.getWalletBalance()
                        + recharge

        );

        customerDAO.updateWalletBalance(customer);

        DisplayUtil.printSuccess(

                "Wallet Recharged Successfully."

        );

    }

    // Builds Customer Order Table Rows.
    private List<String[]> buildCustomerOrderRows(
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
                                order.getTotalPrice()),

                        order.getOrderStatus()
                                .name(),

                        order.getOrderDate()
                                .toLocalDate()
                                .toString()

                })

                .toList();

    }
    //  Builds Seller Order Table Rows.

    private List<String[]> buildSellerOrderRows(
            List<Order> orders) {

        return orders.stream()

                .map(order -> new String[]{

                        order.getOrderId(),

                        order.getCustomer()
                                .getUserName(),

                        order.getProduct()
                                .getProductName(),

                        String.valueOf(
                                order.getQuantity()),

                        String.format(
                                "%.2f",
                                order.getTotalPrice()),

                        order.getOrderStatus()
                                .name()

                })

                .toList();

    }
    //Builds Admin Order Table Rows.
    private List<String[]> buildAdminOrderRows(
            List<Order> orders) {

        return orders.stream()

                .map(order -> new String[]{

                        order.getOrderId(),

                        order.getCustomer()
                                .getUserName(),

                        order.getProduct()
                                .getSeller()
                                .getShopName(),

                        order.getProduct()
                                .getProductName(),

                        String.valueOf(
                                order.getQuantity()),

                        String.format(
                                "%.2f",
                                order.getTotalPrice()),

                        order.getOrderStatus()
                                .name()

                })

                .toList();

    }


    /**
     * Chooses Payment Method.
     *
     * @return Payment Method
     */
    private PaymentMethod choosePaymentMethod() {

        while (true) {

            System.out.println("\n==========================================");
            System.out.println("             PAYMENT MENU");
            System.out.println("==========================================");
            System.out.println("1. WALLET");
            System.out.println("2. UPI");
            System.out.println("3. DEBIT CARD");
            System.out.println("4. NET BANKING");
            System.out.println("5. CASH ON DELIVERY");
            System.out.println("==========================================");

            int choice =
                    InputUtil.readInt("Enter Choice : ");

            switch (choice) {

                case 1:
                    return PaymentMethod.WALLET;

                case 2:
                    return PaymentMethod.UPI;

                case 3:
                    return PaymentMethod.DEBIT_CARD;

                case 4:
                    return PaymentMethod.NET_BANKING;

                case 5:
                    return PaymentMethod.CASH_ON_DELIVERY;

                default:
                    DisplayUtil.printInvalidChoice();

            }

        }

    }

}