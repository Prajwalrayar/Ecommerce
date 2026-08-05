package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.InventoryDAO;
import com.crimsonlogic.ecommerce.dao.ProductDAO;
import com.crimsonlogic.ecommerce.enums.ProductStatus;
import com.crimsonlogic.ecommerce.exceptionhandling.ValidationException;
import com.crimsonlogic.ecommerce.model.Inventory;
import com.crimsonlogic.ecommerce.model.Product;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

import java.util.List;

/**
 * Service class responsible for Inventory operations.
 */
public class InventoryService {


    /**
     * Inventory DAO.
     */
    private final InventoryDAO inventoryDAO =
            new InventoryDAO();

    /**
     * Product DAO.
     */
    private final ProductDAO productDAO =
            new ProductDAO();

    /**
     * Seller Inventory Headers.
     */
    private static final String[] SELLER_HEADERS = {

            "Inventory ID",

            "Product ID",

            "Product Name",

            "Category",

            "Quantity"

    };

    /**
     * Admin Inventory Headers.
     */
    private static final String[] ADMIN_HEADERS = {

            "Inventory ID",

            "Product ID",

            "Product Name",

            "Category",

            "Seller",

            "Quantity"

    };

    /**
     * Default Constructor.
     */
    public InventoryService() {

    }

    /**
     * Adds Stock.
     *
     * @param seller Logged-in Seller
     */
    public void addStock(Seller seller) {

        if (!validateProductsAvailable()) {

            return;

        }

        DisplayUtil.printTable(

                "MY PRODUCTS",

                new String[]{

                        "Product ID",
                        "Product Name",
                        "Category",
                        "Price (₹)",
                        "Status"

                },

                productDAO.findProductsBySeller(
                                seller.getUserId())
                        .stream()
                        .map(product -> new String[]{

                                product.getProductId(),

                                product.getProductName(),

                                product.getCategory()
                                        .getCategoryName(),

                                String.format(
                                        "%.2f",
                                        product.getProductPrice()),

                                product.getProductStatus()
                                        .name()

                        })
                        .toList()

        );

        addStockToProduct(
                getSellerProductOrNull(seller));

    }

    /**
     * Adds Stock.
     * Used by Admin.
     */
    public void addStock() {

        if (!validateProductsAvailable()) {

            return;

        }

        DisplayUtil.printTable(

                "AVAILABLE PRODUCTS",

                new String[]{

                        "Product ID",
                        "Product Name",
                        "Category",
                        "Price (₹)",
                        "Seller",
                        "Status"

                },

                productDAO.findAllProducts()
                        .stream()
                        .map(product -> new String[]{

                                product.getProductId(),

                                product.getProductName(),

                                product.getCategory()
                                        .getCategoryName(),

                                String.format(
                                        "%.2f",
                                        product.getProductPrice()),

                                product.getSeller()
                                        .getShopName(),

                                product.getProductStatus()
                                        .name()

                        })
                        .toList()

        );

        addStockToProduct(
                getProductOrNull());

    }

    /**
     * Displays Seller Inventory.
     *
     * @param seller Seller
     */
    public void viewSellerInventory(
            Seller seller) {

        displayInventories(

                inventoryDAO.findInventoryBySeller(
                        seller.getUserId()),

                "MY INVENTORY",

                false

        );

    }

    /**
     * Displays All Inventory.
     */
    public void viewAllInventory() {

        displayInventories(

                inventoryDAO.findAllInventory(),

                "ALL INVENTORY",

                true

        );

    }
    /**
     * Searches Seller Inventory.
     *
     * @param seller Seller
     */
    public void searchInventory(
            Seller seller) {

        if (!validateInventoryAvailable()) {

            return;

        }

        viewSellerInventory(seller);

        searchInventoryDetails(
                getSellerInventoryOrNull(seller));

    }

    /**
     * Searches Inventory.
     * Used by Admin.
     */
    public void searchInventory() {

        if (!validateInventoryAvailable()) {

            return;

        }

        viewAllInventory();

        searchInventoryDetails(
                getInventoryOrNull());

    }

    /**
     * Updates Seller Stock.
     *
     * @param seller Logged-in Seller
     */
    public void updateStock(
            Seller seller) {

        if (!validateInventoryAvailable()) {

            return;

        }

        viewSellerInventory(seller);

        updateInventoryStock(
                getSellerInventoryOrNull(seller));

    }

    /**
     * Updates Stock.
     * Used by Admin.
     */
    public void updateStock() {

        if (!validateInventoryAvailable()) {

            return;

        }

        viewAllInventory();

        updateInventoryStock(
                getInventoryOrNull());

    }

    /**
     * Deletes Seller Stock.
     *
     * @param seller Logged-in Seller
     */
    public void deleteStock(
            Seller seller) {

        if (!validateInventoryAvailable()) {

            return;

        }

        viewSellerInventory(seller);

        deleteInventoryStock(
                getSellerInventoryOrNull(seller));

    }

    /**
     * Deletes Inventory.
     * Used by Admin.
     */
    public void deleteStock() {

        if (!validateInventoryAvailable()) {

            return;

        }

        viewAllInventory();

        deleteInventoryStock(
                getInventoryOrNull());

    }

    /**
     * Creates Inventory.
     *
     * @param product Product
     */
    private void createInventory(Product product) {

        while (true) {

            try {

                if (isInventoryExists(product)) {

                    DisplayUtil.printMessage(
                            "Inventory Already Exists.");

                    return;

                }

                int quantity =
                        InputUtil.readInt(
                                "Enter Quantity : ");

                ValidationUtil.validateQuantity(
                        quantity);

                Inventory inventory =
                        new Inventory(

                                generateInventoryId(),

                                product,

                                quantity

                        );

                inventoryDAO.insertInventory(
                        inventory);

                updateProductStatus(inventory);

                DisplayUtil.printSuccess(
                        "Stock Added Successfully.");

                System.out.println(
                        "Inventory ID : "
                                + inventory.getInventoryId());

                break;

            }

            catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

            }

        }

    }

    public void addInitialInventory(Product product) {

        createInventory(product);

    }

    /**
     * Displays Inventories.
     *
     * @param inventories Inventory List
     * @param title Table Title
     * @param includeSeller Include Seller Column
     */
    private void displayInventories(List<Inventory> inventories,
            String title, boolean includeSeller) {

        if (inventories.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Inventory Available.");

            return;

        }

        DisplayUtil.printTable(

                title,

                includeSeller
                        ? ADMIN_HEADERS
                        : SELLER_HEADERS,

                buildInventoryRows(
                        inventories,
                        includeSeller)

        );

    }

    /**
     * Builds Inventory Table Rows.
     *
     * @param inventories Inventory List
     * @param includeSeller Include Seller Column
     * @return Table Rows
     */
    private List<String[]> buildInventoryRows(
            List<Inventory> inventories,
            boolean includeSeller) {

        return inventories.stream()

                .map(inventory -> {

                    if (includeSeller) {

                        return new String[]{

                                inventory.getInventoryId(),

                                inventory.getProduct()
                                        .getProductId(),

                                inventory.getProduct()
                                        .getProductName(),

                                inventory.getProduct()
                                        .getCategory()
                                        .getCategoryName(),

                                inventory.getProduct()
                                        .getSeller()
                                        .getShopName(),

                                String.valueOf(
                                        inventory.getQuantity())

                        };

                    }

                    return new String[]{

                            inventory.getInventoryId(),

                            inventory.getProduct()
                                    .getProductId(),

                            inventory.getProduct()
                                    .getProductName(),

                            inventory.getProduct()
                                    .getCategory()
                                    .getCategoryName(),

                            String.valueOf(
                                    inventory.getQuantity())

                    };

                })

                .toList();

    }

    /**
     * Displays Inventory Details.
     *
     * @param inventory Inventory
     */
    private void displayInventory(
            Inventory inventory) {

        DisplayUtil.printTable(

                "INVENTORY DETAILS",

                new String[]{
                        "Field",
                        "Value"
                },

                inventory.getTableRows()

        );

    }
    /**
     * Finds Inventory by Inventory ID.
     *
     * @param inventoryId Inventory ID
     * @return Inventory
     */
    public Inventory findInventoryById(
            String inventoryId) {

        return inventoryDAO.findInventoryById(
                inventoryId);

    }

    /**
     * Finds Inventory by Product.
     *
     * @param product Product
     * @return Inventory
     */
    public Inventory findInventoryByProduct(
            Product product) {

        return inventoryDAO.findInventoryByProduct(
                product.getProductId());

    }

    /**
     * Returns Inventory.
     *
     * @return Inventory
     */
    private Inventory getInventoryOrNull() {

        String inventoryId =
                InputUtil.readString(
                                "Enter Inventory ID : ")
                        .trim()
                        .toUpperCase();

        Inventory inventory =
                findInventoryById(inventoryId);

        if (inventory == null) {

            DisplayUtil.printMessage(
                    "Inventory Not Found.");

        }

        return inventory;

    }
    /**
     * Returns Seller Inventory.
     *
     * @param seller Seller
     * @return Inventory
     */
    private Inventory getSellerInventoryOrNull(
            Seller seller) {

        return validateSellerInventory(

                getInventoryOrNull(),

                seller

        );

    }

    /**
     * Returns Product.
     *
     * @return Product
     */
    private Product getProductOrNull() {

        String productId =
                InputUtil.readString(
                                "Enter Product ID : ")
                        .trim()
                        .toUpperCase();

        Product product =
                productDAO.findProductById(
                        productId);

        if (product == null) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

        }

        return product;

    }

    /**
     * Adds Inventory to Product.
     *
     * @param product Product
     */
    private void addStockToProduct(
            Product product) {

        if (product == null) {

            return;

        }

        createInventory(product);

    }

    /**
     * Displays Inventory Details.
     *
     * @param inventory Inventory
     */
    private void searchInventoryDetails(Inventory inventory) {

        if (inventory == null) {

            return;

        }

        displayInventory(inventory);

    }

    /**
     * Updates Inventory Stock.
     *
     * @param inventory Inventory
     */
    private void updateInventoryStock(
            Inventory inventory) {

        if (inventory == null) {

            return;

        }

        updateInventory(inventory);

    }

    /**
     * Deletes Inventory Stock.
     *
     * @param inventory Inventory
     */
    private void deleteInventoryStock(
            Inventory inventory) {

        if (inventory == null) {

            return;

        }

        removeInventory(inventory);

    }

    /**
     * Validates Seller Inventory.
     *
     * @param inventory Inventory
     * @param seller Seller
     * @return Inventory
     */
    private Inventory validateSellerInventory(Inventory inventory, Seller seller) {

        if (inventory == null) {

            return null;

        }

        if (!inventory.getProduct()
                .getSeller()
                .getUserId()
                .equals(seller.getUserId())) {

            DisplayUtil.printMessage(
                    "Inventory Not Found.");

            return null;

        }

        return inventory;

    }

    /**
     * Validates Seller Product.
     *
     * @param product Product
     * @param seller Seller
     * @return Product
     */
    private Product validateSellerProduct(Product product, Seller seller) {

        if (product == null) {

            return null;

        }

        if (!product.getSeller()
                .getUserId()
                .equals(seller.getUserId())) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

            return null;

        }

        return product;

    }
    /**
     * Returns Seller Product.
     *
     * @param seller Seller
     * @return Product
     */
    private Product getSellerProductOrNull(
            Seller seller) {

        return validateSellerProduct(

                getProductOrNull(),

                seller

        );

    }

    /**
     * Updates Inventory.
     *
     * @param inventory Inventory
     */
    private void updateInventory(
            Inventory inventory) {

        while (true) {

            try {

                int quantity =
                        InputUtil.readInt(
                                "Enter New Quantity : ");

                ValidationUtil.validateQuantity(
                        quantity);

                inventory.setQuantity(
                        quantity);

                inventoryDAO.updateQuantity(
                        inventory);

                updateProductStatus(
                        inventory);

                DisplayUtil.printSuccess(
                        "Stock Updated Successfully.");

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

            }

        }

    }

    /**
     * Removes Inventory.
     *
     * @param inventory Inventory
     */
    private void removeInventory(
            Inventory inventory) {

        inventoryDAO.deleteInventory(
                inventory.getInventoryId());

        inventory.getProduct()
                .setProductStatus(
                        ProductStatus.OUT_OF_STOCK);

        productDAO.updateProductStatus(
                inventory.getProduct());

        DisplayUtil.printSuccess(
                "Inventory Deleted Successfully.");

    }

    /**
     * Updates Product Status.
     *
     * @param inventory Inventory
     */
    private void updateProductStatus(
            Inventory inventory) {

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
     * Checks whether Inventory exists.
     *
     * @param product Product
     * @return true if exists
     */
    private boolean isInventoryExists(
            Product product) {

        return inventoryDAO.findInventoryByProduct(
                product.getProductId()) != null;

    }

    /**
     * Generates Inventory ID.
     *
     * @return Inventory ID
     */
    private String generateInventoryId() {

        String inventoryId;

        do {

            inventoryId =
                    IdGenerator.generateId("INV");

        } while (inventoryDAO.findInventoryById(
                inventoryId) != null);

        return inventoryId;

    }

    /**
     * Validates Inventory Availability.
     *
     * @return true if Inventory exists
     */
    private boolean validateInventoryAvailable() {

        if (inventoryDAO.findAllInventory()
                .isEmpty()) {

            DisplayUtil.printMessage(
                    "No Inventory Available.");

            return false;

        }

        return true;

    }

    /**
     * Validates Product Availability.
     *
     * @return true if Products exist
     */
    private boolean validateProductsAvailable() {

        if (productDAO.findAllProducts()
                .isEmpty()) {

            DisplayUtil.printMessage(
                    "No Products Available.");

            return false;

        }

        return true;

    }

}