package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.CartDAO;
import com.crimsonlogic.ecommerce.dao.InventoryDAO;
import com.crimsonlogic.ecommerce.enums.ProductStatus;
import com.crimsonlogic.ecommerce.exceptionhandling.ValidationException;
import com.crimsonlogic.ecommerce.model.Cart;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Inventory;
import com.crimsonlogic.ecommerce.model.Product;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

import java.util.List;

/**
 * Service class responsible for Cart operations.
 */
public class CartService {

    private final ProductService productService =
            new ProductService();

    private final InventoryService inventoryService =
            new InventoryService();

    private final CartDAO cartDAO =
            new CartDAO();

    private final InventoryDAO inventoryDAO =
            new InventoryDAO();

    private static final String[] CART_HEADERS = {

            "Cart ID",

            "Product Name",

            "Category",

            "Quantity",

            "Price",

            "Total"

    };

    /**
     * Adds Product To Cart.
     *
     * @param customer Customer
     */
    public void addToCart(Customer customer) {

        if (!validateAvailableProducts()) {
            return;
        }

        List<Product> products =
                productService.getAvailableProductsForCart();

        if (products.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Products Available.");

            return;
        }

        displayProductsForCart(products);

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        if (productName.isEmpty()) {

            DisplayUtil.printMessage(
                    "Product Name Cannot Be Empty.");

            return;
        }

        Product product =
                products.stream()
                        .filter(currentProduct ->
                                currentProduct.getProductName()
                                        .equalsIgnoreCase(
                                                productName))
                        .findFirst()
                        .orElse(null);

        if (product == null) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

            return;
        }

        createCart(customer, product);
    }

    /**
     * Displays Customer Cart.
     *
     * @param customer Customer
     */
    public void viewCart(Customer customer) {

        List<Cart> carts =
                getCustomerCart(customer);

        if (carts.isEmpty()) {

            DisplayUtil.printMessage(
                    "Your Cart is Empty.");

            return;

        }

        DisplayUtil.printTable(

                "MY CART",

                CART_HEADERS,

                buildCartRows(carts)

        );

        System.out.printf(

                "Grand Total : ₹%.2f%n",

                calculateGrandTotal(carts)

        );

    }

    /**
     * Updates Cart Quantity.
     *
     * @param customer Customer
     */
    public void updateQuantity(Customer customer) {

        Cart cart =
                selectCustomerCart(customer);

        if (cart == null) {

            return;

        }

        updateCart(cart);

    }

    /**
     * Removes Cart Item.
     *
     * @param customer Customer
     */
    public void removeItem(Customer customer) {

        Cart cart =
                selectCustomerCart(customer);

        if (cart == null) {

            return;

        }

        removeCart(cart);

    }

    /**
     * Clears Customer Cart.
     *
     * @param customer Customer
     */
    public void clearCart(Customer customer) {

        if (!validateCart(customer)) {

            return;

        }

        cartDAO.clearCart(customer.getUserId());

        DisplayUtil.printSuccess(
                "Cart Cleared Successfully.");

    }
    /**
     * Creates Cart.
     *
     * @param customer Customer
     * @param product Product
     */
    private void createCart(
            Customer customer,
            Product product) {

        while (true) {

            try {

                Inventory inventory =
                        getInventory(product);

                if (!validateInventory(
                        inventory,
                        product)) {

                    return;

                }

                int quantity =
                        InputUtil.readInt(
                                "Enter Quantity : ");

                ValidationUtil.validateQuantity(
                        quantity);

                Cart existingCart =
                        cartDAO.findCartItem(

                                customer.getUserId(),

                                product.getProductId()

                        );

                if (existingCart != null) {

                    saveExistingCart(

                            existingCart,

                            inventory,

                            quantity

                    );

                    return;

                }

                if (!validateStock(
                        inventory,
                        quantity)) {

                    return;

                }

                Cart cart =
                        new Cart(

                                generateCartId(),

                                customer,

                                product,

                                quantity

                        );

                cartDAO.insertCartItem(
                        cart);

                DisplayUtil.printSuccess(
                        "Product Added To Cart.");

                break;

            }

            catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

            }

        }

    }

    /**
     * Returns Customer Cart.
     *
     * @param customer Customer
     * @return Cart
     */
    private Cart selectCustomerCart(
            Customer customer) {

        if (!validateCart(customer)) {

            return null;

        }

        viewCart(customer);

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        Product product =
                productService.findProductByName(
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
     * Returns Customer Cart.
     *
     * @param customer Customer
     * @return Cart List
     */
    private List<Cart> getCustomerCart(
            Customer customer) {

        return cartDAO.findCartByCustomer(
                customer.getUserId());

    }

    /**
     * Returns Product.
     *
     * @return Product
     */
    private Product getProductOrNull() {

        String productName =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        Product product =
                productService.findProductByName(
                        productName);

        if (product == null) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

        }

        return product;

    }

    /**
     * Returns Inventory.
     *
     * @param product Product
     * @return Inventory
     */
    private Inventory getInventory(
            Product product) {

        return inventoryService
                .findInventoryByProduct(
                        product);

    }
    /**
     * Builds Cart Table Rows.
     *
     * @param carts Cart Items
     * @return Table Rows
     */
    private List<String[]> buildCartRows(
            List<Cart> carts) {

        return carts.stream()

                .map(cart -> new String[]{

                        cart.getCartId(),

                        cart.getProduct()
                                .getProductName(),

                        cart.getProduct()
                                .getCategory()
                                .getCategoryName(),

                        String.valueOf(
                                cart.getQuantity()),

                        String.format(
                                "%.2f",
                                cart.getProduct()
                                        .getProductPrice()),

                        String.format(
                                "%.2f",
                                cart.getTotalPrice())

                })

                .toList();

    }

    /**
     * Calculates Grand Total.
     *
     * @param carts Cart List
     * @return Grand Total
     */
    private double calculateGrandTotal(
            List<Cart> carts) {

        return carts.stream()

                .mapToDouble(
                        Cart::getTotalPrice)

                .sum();

    }

    /**
     * Updates Existing Cart.
     *
     * @param cart Cart
     * @param inventory Inventory
     * @param quantity Quantity
     */
    private void saveExistingCart(
            Cart cart,
            Inventory inventory,
            int quantity) {

        int totalQuantity =
                cart.getQuantity() + quantity;

        if (!validateStock(
                inventory,
                totalQuantity)) {

            return;

        }

        saveCartQuantity(
                cart,
                totalQuantity);

        DisplayUtil.printSuccess(
                "Cart Updated Successfully.");

    }

    /**
     * Updates Cart.
     *
     * @param cart Cart
     */
    private void updateCart(
            Cart cart) {

        while (true) {

            try {

                Inventory inventory =
                        getInventory(
                                cart.getProduct());

                int quantity =
                        InputUtil.readInt(
                                "Enter New Quantity : ");

                ValidationUtil.validateQuantity(
                        quantity);

                if (!validateStock(
                        inventory,
                        quantity)) {

                    return;

                }

                saveCartQuantity(
                        cart,
                        quantity);

                DisplayUtil.printSuccess(
                        "Cart Updated Successfully.");

                break;

            }

            catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

            }

        }

    }

    /**
     * Saves Cart Quantity.
     *
     * @param cart Cart
     * @param quantity Quantity
     */
    private void saveCartQuantity(
            Cart cart,
            int quantity) {

        cart.setQuantity(
                quantity);

        cartDAO.updateCartItem(
                cart);

    }

    /**
     * Removes Cart.
     *
     * @param cart Cart
     */
    private void removeCart(
            Cart cart) {

        cartDAO.deleteCartItem(
                cart.getCartId());

        DisplayUtil.printSuccess(
                "Cart Item Removed Successfully.");

    }

    /**
     * Validates Inventory.
     *
     * @param inventory Inventory
     * @param product Product
     * @return true if valid
     */
    private boolean validateInventory(
            Inventory inventory,
            Product product) {

        if (inventory == null) {

            DisplayUtil.printMessage(
                    "Inventory Not Found.");

            return false;

        }

        if (product.getProductStatus()
                == ProductStatus.OUT_OF_STOCK) {

            DisplayUtil.printMessage(
                    "Product is Out Of Stock.");

            return false;

        }

        return true;

    }
    /**
     * Validates Stock.
     *
     * @param inventory Inventory
     * @param quantity Quantity
     * @return true if valid
     */
    private boolean validateStock(
            Inventory inventory,
            int quantity) {

        if (quantity > inventory.getQuantity()) {

            DisplayUtil.printMessage(

                    "Only "

                            + inventory.getQuantity()

                            + " item(s) available."

            );

            return false;

        }

        return true;

    }

    /**
     * Validates Customer Cart.
     *
     * @param customer Customer
     * @return true if Cart exists
     */
    private boolean validateCart(
            Customer customer) {

        if (getCustomerCart(customer).isEmpty()) {

            DisplayUtil.printMessage(
                    "Your Cart is Empty.");

            return false;

        }

        return true;

    }

    /**
     * Validates Available Products.
     *
     * @return true if Products exist
     */
    private boolean validateAvailableProducts() {

        boolean available =

                inventoryDAO.findAllInventory()

                        .stream()

                        .anyMatch(inventory ->

                                inventory.getQuantity() > 0

                                        &&

                                        inventory.getProduct()
                                                .getProductStatus()
                                                == ProductStatus.AVAILABLE);

        if (!available) {

            DisplayUtil.printMessage(
                    "No Products Available.");

            return false;

        }

        return true;

    }

    /**
     * Finds Cart by Cart ID.
     *
     * @param cartId Cart ID
     * @return Cart
     */
    public Cart findCartById(
            String cartId) {

        return cartDAO.findCartItemById(
                cartId);

    }

    /**
     * Generates Cart ID.
     *
     * @return Cart ID
     */
    private String generateCartId() {

        String cartId;

        do {

            cartId =
                    IdGenerator.generateId("CRT");

        }

        while (findCartById(cartId) != null);

        return cartId;

    }

    private void displayProductsForCart(
            List<Product> products) {

        String[] headers = {

                "Product ID",
                "Product Name",
                "Brand",
                "Category",
                "Price (₹)",
                "Stock",
                "Seller",
                "Rating",
                "Status"
        };

        List<String[]> rows =
                products.stream()
                        .map(product -> {

                            Inventory inventory =
                                    inventoryService
                                            .findInventoryByProduct(
                                                    product);

                            int stock =
                                    inventory != null
                                            ? inventory.getQuantity()
                                            : 0;

                            return new String[]{

                                    product.getProductId(),

                                    product.getProductName(),

                                    product.getBrand(),

                                    product.getCategory()
                                            .getCategoryName(),

                                    String.format(
                                            "%.2f",
                                            product.getProductPrice()),

                                    String.valueOf(stock),

                                    product.getSeller()
                                            .getShopName(),

                                    String.format(
                                            "%.1f (%d)",
                                            product.getRating(),
                                            product.getReviewCount()),

                                    product.getProductStatus()
                                            .name()
                            };

                        })
                        .toList();

        DisplayUtil.printTable(
                "AVAILABLE PRODUCTS",
                headers,
                rows);
    }

}