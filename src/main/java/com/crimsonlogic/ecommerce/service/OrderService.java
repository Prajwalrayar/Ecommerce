package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.enums.OrderStatus;
import com.crimsonlogic.ecommerce.model.Cart;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Inventory;
import com.crimsonlogic.ecommerce.model.Order;
import com.crimsonlogic.ecommerce.model.Product;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.repository.DataStore;
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
     * Default Constructor.
     */
    public OrderService(
            CartService cartService,
            InventoryService inventoryService) {

        this.cartService = cartService;
        this.inventoryService = inventoryService;

    }

    /**
     * Places Order.
     *
     * @param customer Logged-in Customer
     */
    public void placeOrder(Customer customer) {

        if (!hasCart(customer)) {

            DisplayUtil.printMessage(
                    "Your Cart is Empty.");

            return;

        }

        cartService.viewCart(customer);

        Cart cart =
                getCustomerCartOrNull(customer);

        if (cart == null) {

            return;

        }

        createOrder(cart);

    }

    /**
     * Displays Customer Orders.
     *
     * @param customer Logged-in Customer
     */
    /**
     * Displays Customer Orders.
     *
     * @param customer Logged-in Customer
     */
    public void viewOrders(Customer customer) {

        List<Order> orders =
                DataStore.ORDERS.values()

                        .stream()

                        .filter(order ->
                                order.getCustomer()
                                        .equals(customer))

                        .toList();

        if (orders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        String[] headers = {

                "Tracking No",

                "Product",

                "Quantity",

                "Amount",

                "Status",

                "Order Date"

        };

        DisplayUtil.printTable(

                "MY ORDERS",

                headers,

                buildCustomerOrderRows(orders));

    }

    /**
     * Tracks Customer Order.
     *
     * @param customer Logged-in Customer
     */
    public void trackOrder(Customer customer) {

        if (!hasOrders(customer)) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        Order order =
                getCustomerOrderOrNull(customer);

        if (order != null) {

            displayOrder(order);

        }

    }

    /**
     * Cancels Order.
     *
     * @param customer Logged-in Customer
     */
    public void cancelOrder(Customer customer) {

        if (!hasOrders(customer)) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        viewOrders(customer);

        Order order =
                getCustomerOrderOrNull(customer);

        if (order == null) {

            return;

        }

        cancelExistingOrder(order);

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

        if (inventory == null) {

            DisplayUtil.printMessage(
                    "Inventory Not Available.");

            return;

        }

        if (inventory.getQuantity()
                < cart.getQuantity()) {

            DisplayUtil.printMessage(
                    "Insufficient Stock.");

            return;

        }

        Order order =
                new Order(

                        generateTrackingNumber(),

                        cart.getCustomer(),

                        cart.getProduct(),

                        cart.getQuantity(),

                        cart.getTotalPrice(),

                        OrderStatus.PLACED,

                        LocalDateTime.now());

        DataStore.ORDERS.put(

                order.getOrderId(),

                order);

        inventory.setQuantity(

                inventory.getQuantity()

                        - cart.getQuantity());

        DataStore.CARTS.remove(

                cart.getCartId());

        DisplayUtil.printSuccess(
                "Order Placed Successfully.");

        System.out.println(
                "Tracking Number : "
                        + order.getOrderId());

    }

    /**
     * Cancels Existing Order.
     *
     * @param order Order
     */
    private void cancelExistingOrder(Order order) {

        if (order.getOrderStatus()
                == OrderStatus.SHIPPED

                ||

                order.getOrderStatus()
                        == OrderStatus.IN_TRANSIT

                ||

                order.getOrderStatus()
                        == OrderStatus.OUT_FOR_DELIVERY

                ||

                order.getOrderStatus()
                        == OrderStatus.DELIVERED) {

            DisplayUtil.printMessage(
                    "Order Cannot Be Cancelled.");

            return;

        }

        if (order.getOrderStatus()
                == OrderStatus.CANCELLED) {

            DisplayUtil.printMessage(
                    "Order Already Cancelled.");

            return;

        }

        order.setOrderStatus(
                OrderStatus.CANCELLED);

        Inventory inventory =
                inventoryService.findInventoryByProduct(
                        order.getProduct());

        if (inventory != null) {

            inventory.setQuantity(

                    inventory.getQuantity()

                            + order.getQuantity());

        }

        DisplayUtil.printSuccess(
                "Order Cancelled Successfully.");

    }


    /**
     * Displays Seller Orders.
     *
     * @param seller Logged-in Seller
     */
    /**
     * Displays Seller Orders.
     *
     * @param seller Logged-in Seller
     */
    public void viewSellerOrders(Seller seller) {

        List<Order> orders = DataStore.ORDERS.values()

                .stream()

                .filter(order ->
                        order.getProduct()
                                .getSeller()
                                .equals(seller))

                .toList();

        if (orders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        DisplayUtil.printTable(

                "MY ORDERS",

                new String[]{

                        "Tracking No",

                        "Customer",

                        "Product",

                        "Quantity",

                        "Amount",

                        "Status"

                },

                buildSellerOrderRows(orders));

    }

    /**
     * Tracks Seller Order.
     *
     * @param seller Logged-in Seller
     */
    public void trackOrder(Seller seller) {

        if (!hasSellerOrders(seller)) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        viewSellerOrders(seller);

        Order order =
                getSellerOrderOrNull(seller);

        if (order != null) {

            displayOrder(order);

        }

    }

    /**
     * Confirms Order.
     *
     * @param seller Logged-in Seller
     */
    public void confirmOrder(Seller seller) {

        if (!hasSellerOrders(seller)) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        viewSellerOrders(seller);

        Order order =
                getSellerOrderOrNull(seller);

        if (order == null) {

            return;

        }

        confirmExistingOrder(order);

    }

    /**
     * Updates Order Status.
     *
     * @param seller Logged-in Seller
     */
    public void updateOrderStatus(Seller seller) {

        if (!hasSellerOrders(seller)) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        viewSellerOrders(seller);

        Order order =
                getSellerOrderOrNull(seller);

        if (order == null) {

            return;

        }

        updateExistingOrderStatus(order);

    }
    /**
     * Displays All Orders.
     */
    /**
     * Displays All Orders.
     */
    public void viewAllOrders() {

        if (DataStore.ORDERS.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        DisplayUtil.printTable(

                "ALL ORDERS",

                new String[]{

                        "Tracking No",

                        "Customer",

                        "Seller",

                        "Product",

                        "Quantity",

                        "Amount",

                        "Status"

                },

                buildAdminOrderRows(

                        DataStore.ORDERS.values()

                                .stream()

                                .toList()));

    }

    /**
     * Tracks Order.
     */
    public void trackOrder() {

        if (DataStore.ORDERS.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        viewAllOrders();

        Order order = getOrderOrNull();

        if (order != null) {

            displayOrder(order);

        }

    }

    /**
     * Confirms Order.
     */
    public void confirmOrder() {

        if (DataStore.ORDERS.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        viewAllOrders();

        Order order = getOrderOrNull();

        if (order == null) {

            return;

        }

        confirmExistingOrder(order);

    }

    /**
     * Updates Order Status.
     */
    public void updateOrderStatus() {

        if (DataStore.ORDERS.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        viewAllOrders();

        Order order = getOrderOrNull();

        if (order == null) {

            return;

        }

        updateExistingOrderStatus(order);

    }

    /**
     * Deletes Order.
     */
    public void deleteOrder() {

        if (DataStore.ORDERS.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        viewAllOrders();

        Order order = getOrderOrNull();

        if (order == null) {

            return;

        }

        DataStore.ORDERS.remove(
                order.getOrderId());

        DisplayUtil.printSuccess(
                "Order Deleted Successfully.");

    }

    /**
     * Confirms Existing Order.
     *
     * @param order Order
     */
    private void confirmExistingOrder(Order order) {

        if (order.getOrderStatus()
                != OrderStatus.PLACED) {

            DisplayUtil.printMessage(
                    "Only Placed Orders Can Be Confirmed.");

            return;

        }

        order.setOrderStatus(
                OrderStatus.CONFIRMED);

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

        DisplayUtil.printSuccess(
                "Order Status Updated Successfully.");

    }
    /**
     * Checks whether Customer has Cart.
     *
     * @param customer Logged-in Customer
     * @return true if Cart exists
     */
    private boolean hasCart(Customer customer) {

        return DataStore.CARTS.values()

                .stream()

                .anyMatch(cart ->
                        cart.getCustomer()
                                .equals(customer));

    }

    /**
     * Checks whether Customer has Orders.
     *
     * @param customer Logged-in Customer
     * @return true if Orders exist
     */
    private boolean hasOrders(Customer customer) {

        return DataStore.ORDERS.values()

                .stream()

                .anyMatch(order ->
                        order.getCustomer()
                                .equals(customer));

    }

    /**
     * Checks whether Seller has Orders.
     *
     * @param seller Logged-in Seller
     * @return true if Orders exist
     */
    private boolean hasSellerOrders(Seller seller) {

        return DataStore.ORDERS.values()

                .stream()

                .anyMatch(order ->
                        order.getProduct()
                                .getSeller()
                                .equals(seller));

    }

    /**
     * Returns Customer Cart.
     *
     * @param customer Logged-in Customer
     * @return Cart
     */
    private Cart getCustomerCartOrNull(Customer customer) {

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        Cart cart = DataStore.CARTS.values()

                .stream()

                .filter(item ->
                        item.getCustomer()
                                .equals(customer))

                .filter(item ->
                        item.getProduct()
                                .getProductName()
                                .equalsIgnoreCase(productName))

                .findFirst()

                .orElse(null);

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
    private Order getCustomerOrderOrNull(
            Customer customer) {

        String trackingNumber =
                InputUtil.readString(
                                "Enter Tracking Number : ")
                        .trim();

        Order order = DataStore.ORDERS.values()

                .stream()

                .filter(item ->
                        item.getOrderId()
                                .equals(trackingNumber))

                .filter(item ->
                        item.getCustomer()
                                .equals(customer))

                .findFirst()

                .orElse(null);

        if (order == null) {

            DisplayUtil.printMessage(
                    "Order Not Found.");

        }

        return order;

    }

    /**
     * Returns Seller Order.
     *
     * @param seller Logged-in Seller
     * @return Order
     */
    private Order getSellerOrderOrNull(Seller seller) {

        String trackingNumber =
                InputUtil.readString(
                                "Enter Tracking Number : ")
                        .trim();

        Order order = DataStore.ORDERS.values()

                .stream()

                .filter(item ->
                        item.getOrderId()
                                .equals(trackingNumber))

                .filter(item ->
                        item.getProduct()
                                .getSeller()
                                .equals(seller))

                .findFirst()

                .orElse(null);

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
                DataStore.ORDERS.get(
                        trackingNumber);

        if (order == null) {

            DisplayUtil.printMessage(
                    "Order Not Found.");

        }

        return order;

    }

    // Searches Customer Orders.

    public void searchOrder(Customer customer) {

        if (!hasOrders(customer)) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        List<Order> orders =
                DataStore.ORDERS.values()

                        .stream()

                        .filter(order ->
                                order.getCustomer()
                                        .equals(customer))

                        .filter(order ->
                                order.getProduct()
                                        .getProductName()
                                        .equalsIgnoreCase(productName))

                        .toList();

        if (orders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        DisplayUtil.printTable(

                "SEARCH RESULT",

                new String[]{

                        "Tracking No",

                        "Product",

                        "Quantity",

                        "Amount",

                        "Status"

                },

                buildCustomerOrderRows(orders));

    }

    // Searches Seller Orders.
    public void searchOrder(Seller seller) {

        if (!hasSellerOrders(seller)) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        List<Order> orders =
                DataStore.ORDERS.values()

                        .stream()

                        .filter(order ->
                                order.getProduct()
                                        .getSeller()
                                        .equals(seller))

                        .filter(order ->
                                order.getProduct()
                                        .getProductName()
                                        .equalsIgnoreCase(productName))

                        .toList();

        if (orders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        DisplayUtil.printTable(

                "SEARCH RESULT",

                new String[]{

                        "Tracking No",

                        "Customer",

                        "Product",

                        "Quantity",

                        "Amount",

                        "Status"

                },

                buildSellerOrderRows(orders));

    }

    // Searches Orders.
    public void searchOrder() {

        if (DataStore.ORDERS.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        String keyword =
                InputUtil.readString(
                                "Enter Customer/Product Name : ")
                        .trim();

        List<Order> orders =
                DataStore.ORDERS.values()

                        .stream()

                        .filter(order ->

                                order.getCustomer()
                                        .getUserName()
                                        .equalsIgnoreCase(keyword)

                                        ||

                                        order.getProduct()
                                                .getProductName()
                                                .equalsIgnoreCase(keyword))

                        .toList();

        if (orders.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Orders Found.");

            return;

        }

        DisplayUtil.printTable(

                "SEARCH RESULT",

                new String[]{

                        "Tracking No",

                        "Customer",

                        "Seller",

                        "Product",

                        "Quantity",

                        "Amount",

                        "Status"

                },

                buildAdminOrderRows(orders));

    }

    // Generates Tracking Number.

    private String generateTrackingNumber() {

        String trackingNumber;

        do {

            trackingNumber =
                    IdGenerator.generateId("");

        }

        while (DataStore.ORDERS.containsKey(
                trackingNumber));

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

}