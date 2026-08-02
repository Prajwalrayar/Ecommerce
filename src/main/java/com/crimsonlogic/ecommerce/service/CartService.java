package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.enums.ProductStatus;
import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.Cart;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Inventory;
import com.crimsonlogic.ecommerce.model.Product;
import com.crimsonlogic.ecommerce.repository.DataStore;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

import java.util.List;

/**
 * Service class responsible for Cart operations.
 */
public class CartService {

    private final ProductService productService;

    private final InventoryService inventoryService;

    /**
     * Default Constructor.
     */
    public CartService() {

        productService = new ProductService();
        inventoryService = new InventoryService();

    }

    /**
     * Adds Product to Cart.
     *
     * @param customer Logged-in Customer
     */
    public void addToCart(Customer customer) {

        boolean available = DataStore.INVENTORIES.values()
                .stream()
                .anyMatch(inventory -> inventory.getQuantity() > 0);

        if (!available) {

            DisplayUtil.printMessage(
                    "No Products Available.");

            return;

        }
        productService.browseProducts();

        Product product = getProductOrNull();

        if (product != null) {

            createCart(customer, product);

        }

    }

    /**
     * Creates Cart.
     *
     * @param customer Customer
     * @param product  Product
     */
    private void createCart(Customer customer, Product product) {

        while (true) {

            try {

                Inventory inventory = inventoryService.findInventoryByProduct(product);

                if (inventory == null) {

                    DisplayUtil.printMessage("Inventory Not Found.");

                    return;

                }

                if (product.getProductStatus() == ProductStatus.OUT_OF_STOCK) {

                    DisplayUtil.printMessage("Product is Out of Stock.");

                    return;

                }

                int quantity = InputUtil.readInt("Enter Quantity : ");

                ValidationUtil.validateQuantity(quantity);

                if (quantity > inventory.getQuantity()) {

                    DisplayUtil.printMessage("Only " + inventory.getQuantity() + " item(s) available.");

                    return;

                }

                Cart cart = isProductAlreadyInCart(customer, product);

                if (cart != null) {

                    int totalQuantity = cart.getQuantity() + quantity;

                    if (totalQuantity > inventory.getQuantity()) {

                        DisplayUtil.printMessage("Only " + inventory.getQuantity() + " item(s) available.");

                        return;

                    }

                    cart.setQuantity(totalQuantity);

                    DisplayUtil.printSuccess("Cart Updated Successfully.");

                    return;

                }

                Cart newCart = new Cart(generateCartId(), customer, product, quantity);

                DataStore.CARTS.put(newCart.getCartId(), newCart);

                DisplayUtil.printSuccess("Product Added To Cart.");

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(exception.getMessage());

            }

        }

    }

    /**
     * Checks whether Product already exists in Customer Cart.
     *
     * @param customer Customer
     * @param product  Product
     * @return Cart if exists otherwise null
     */
    private Cart isProductAlreadyInCart(Customer customer, Product product) {

        return DataStore.CARTS.values()

                .stream()

                .filter(cart ->

                        cart.getCustomer().equals(customer)

                                &&

                                cart.getProduct().equals(product))

                .findFirst()

                .orElse(null);

    }

    /**
     * Generates Cart ID.
     *
     * @return Cart ID
     */
    private String generateCartId() {

        String cartId;

        do {

            cartId = IdGenerator.generateId("CRT");

        }

        while (DataStore.CARTS.containsKey(cartId));

        return cartId;

    }

    /**
     * Displays Customer Cart.
     *
     * @param customer Logged-in Customer
     */
    public void viewCart(Customer customer) {

        List<Cart> carts =

                DataStore.CARTS.values()

                        .stream()

                        .filter(cart -> cart.getCustomer().equals(customer))

                        .toList();

        if (carts.isEmpty()) {

            DisplayUtil.printMessage("Your Cart is Empty.");

            return;

        }

        String[] headers = {

                "Cart ID",

                "Product Name",

                "Category",

                "Quantity",

                "Price",

                "Total"

        };

        DisplayUtil.printTable(

                "MY CART",

                headers,

                buildCartRows(carts));

        System.out.printf("Grand Total : ₹%.2f%n", calculateGrandTotal(carts));

    }

    /**
     * Builds Cart Table Rows.
     *
     * @param carts Cart Items
     * @return Cart Table Rows
     */
    private List<String[]> buildCartRows(List<Cart> carts) {
        return carts.stream()

                .map(cart -> new String[]{

                        cart.getCartId(),

                        cart.getProduct().getProductName(),

                        cart.getProduct()
                                .getCategory()
                                .getCategoryName(),

                        String.valueOf(
                                cart.getQuantity()),

                        String.format("%.2f",
                                cart.getProduct()
                                        .getProductPrice()),

                        String.format("%.2f",
                                cart.getTotalPrice())

                })

                .toList();

    }

    /**
     * Displays Cart Details.
     *
     * @param cart Cart
     */
    private void displayCart(Cart cart) {

        DisplayUtil.printTable(

                "CART DETAILS",

                new String[]{"Field", "Value"},

                cart.getTableRows());

    }

    /**
     * Calculates Grand Total.
     *
     * @param carts Cart Items
     * @return Grand Total
     */
    private double calculateGrandTotal(List<Cart> carts) {

        return carts.stream()

                .mapToDouble(Cart::getTotalPrice)

                .sum();

    }

    /**
     * Updates Cart Quantity.
     *
     * @param customer Logged-in Customer
     */
    public void updateQuantity(Customer customer) {
        if (!hasCart(customer)) {

            DisplayUtil.printMessage(
                    "Your Cart is Empty.");

            return;

        }

        viewCart(customer);

        Cart cart = getCustomerCartOrNull(customer);

        if (cart != null) {

            updateCart(cart);

        }

    }

    /**
     * Removes Cart Item.
     *
     * @param customer Logged-in Customer
     */
    public void removeItem(Customer customer) {
        if (!hasCart(customer)) {
            DisplayUtil.printMessage("Your Cart is Empty.");
            return;
        }

        viewCart(customer);

        Cart cart = getCustomerCartOrNull(customer);

        if (cart != null) {

            removeCart(cart);

        }

    }

    /**
     * Clears Customer Cart.
     *
     * @param customer Logged-in Customer
     */
    public void clearCart(Customer customer) {

        if (!hasCart(customer)) {

            DisplayUtil.printMessage(
                    "Your Cart is Empty.");

            return;

        }

        List<Cart> carts =
                DataStore.CARTS.values()
                        .stream()
                        .filter(cart ->
                                cart.getCustomer()
                                        .equals(customer))
                        .toList();

        for (Cart cart : carts) {

            removeCart(cart);

        }

        DisplayUtil.printSuccess(
                "Cart Cleared Successfully.");

    }

    /**
     * Finds Cart using Cart ID.
     *
     * @param cartId Cart ID
     * @return Cart if found otherwise null
     */
    public Cart findCartById(String cartId) {

        return DataStore.CARTS.get(cartId.toUpperCase());

    }

    /**
     * Returns Cart.
     *
     * @return Cart if found otherwise null
     */
    private Cart getCartOrNull() {

        String cartId = InputUtil.readString("Enter Cart ID : ").trim().toUpperCase();

        Cart cart = findCartById(cartId);

        if (cart == null) {

            DisplayUtil.printMessage("Cart Item Not Found.");

        }

        return cart;

    }

    /**
     * Checks whether Customer has Cart Items.
     *
     * @param customer Customer
     * @return true if Cart exists, otherwise false
     */
    private boolean hasCart(Customer customer) {

        return DataStore.CARTS.values()
                .stream()
                .anyMatch(cart ->
                        cart.getCustomer()
                                .equals(customer));

    }

    // Returns Customer Cart.
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

    //Returns Product if found.
    private Product getProductOrNull() {

        String productName = InputUtil.readString(
                        "Enter Product Name : ")
                .trim();

        Product product =
                productService.findProductByName(
                        productName);

        if (product == null) {

            DisplayUtil.printMessage("Product Not Found.");

        }

        return product;

    }


    /**
     * Updates Cart.
     *
     * @param cart Cart
     */
    private void updateCart(Cart cart) {

        while (true) {

            try {

                Inventory inventory = inventoryService.findInventoryByProduct(cart.getProduct());

                int quantity = InputUtil.readInt("Enter New Quantity : ");

                ValidationUtil.validateQuantity(quantity);

                if (quantity > inventory.getQuantity()) {

                    DisplayUtil.printMessage("Only " + inventory.getQuantity() + " item(s) available.");

                    return;

                }

                cart.setQuantity(quantity);

                DisplayUtil.printSuccess("Cart Updated Successfully.");

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(exception.getMessage());

            }

        }

    }

    /**
     * Removes Cart.
     *
     * @param cart Cart
     */
    private void removeCart(Cart cart) {

        DataStore.CARTS.remove(cart.getCartId());

        DisplayUtil.printSuccess("Cart Item Removed Successfully.");

    }
}
