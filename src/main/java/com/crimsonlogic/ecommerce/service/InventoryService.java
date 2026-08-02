package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.enums.ProductStatus;
import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.Inventory;
import com.crimsonlogic.ecommerce.model.Product;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.repository.DataStore;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

import java.util.List;

/**
 * Service class responsible for Inventory operations.
 */
public class InventoryService {

    private final ProductService productService;

    /**
     * Default Constructor.
     */
    public InventoryService() {

        productService = new ProductService();

    }

    /**
     * Adds Stock.
     *
     * @param seller Logged-in Seller
     */
    public void addStock(Seller seller) {

        productService.viewSellerProducts(seller);

        Product product = getSellerProductOrNull(seller);

        if (product != null) {

            createInventory(product);

        }

    }

    /**
     * Adds Stock.
     * Used by Admin.
     */
    public void addStock() {

        productService.viewAllProducts();

        Product product = getProductOrNull();

        if (product != null) {

            createInventory(product);

        }

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

                    DisplayUtil.printSuccess(
                            "Inventory Already Exists.");

                    return;

                }

                int quantity =
                        InputUtil.readInt(
                                "Enter Quantity : ");

                ValidationUtil.validateQuantity(quantity);

                Inventory inventory =
                        new Inventory(
                                generateInventoryId(),
                                product,
                                quantity);

                DataStore.INVENTORIES.put(
                        inventory.getInventoryId(),
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

                DisplayUtil.printSuccess(
                        exception.getMessage());

            }

        }

    }

    /**
     * Checks whether Inventory already exists.
     *
     * @param product Product
     * @return true if Inventory exists
     */
    private boolean isInventoryExists(Product product) {

        return DataStore.INVENTORIES.values()
                .stream()
                .anyMatch(inventory ->
                        inventory.getProduct()
                                .equals(product));

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

        }

        while (DataStore.INVENTORIES.containsKey(inventoryId));

        return inventoryId;

    }

    /**
     * Updates Product Status based on Quantity.
     *
     * @param inventory Inventory
     */
    private void updateProductStatus(Inventory inventory) {

        if (inventory.getQuantity() == 0) {

            inventory.getProduct()
                    .setProductStatus(
                            ProductStatus.OUT_OF_STOCK);

        } else {

            inventory.getProduct()
                    .setProductStatus(
                            ProductStatus.AVAILABLE);

        }

    }
    /**
     * Displays Seller Inventory.
     *
     * @param seller Logged-in Seller
     */
    public void viewSellerInventory(Seller seller) {

        List<Inventory> inventories =
                DataStore.INVENTORIES.values()
                        .stream()
                        .filter(inventory ->
                                inventory.getProduct()
                                        .getSeller()
                                        .equals(seller))
                        .toList();

        if (inventories.isEmpty()) {

            DisplayUtil.printSuccess(
                    "No Inventory Available.");

            return;

        }

        String[] headers = {
                "Inventory ID",
                "Product ID",
                "Product Name",
                "Category",
                "Quantity"
        };

        DisplayUtil.printTable(
                "MY INVENTORY",
                headers,
                buildInventoryRows(
                        inventories,
                        false));

    }

    /**
     * Displays All Inventory.
     */
    public void viewAllInventory() {

        if (DataStore.INVENTORIES.isEmpty()) {

            DisplayUtil.printSuccess(
                    "No Inventory Available.");

            return;

        }

        String[] headers = {
                "Inventory ID",
                "Product ID",
                "Product Name",
                "Category",
                "Seller",
                "Quantity"
        };

        DisplayUtil.printTable(
                "ALL INVENTORY",
                headers,
                buildInventoryRows(
                        DataStore.INVENTORIES.values()
                                .stream()
                                .toList(),
                        true));

    }

    /**
     * Builds Inventory Table Rows.
     *
     * @param inventories Inventories
     * @param includeSeller true to include Seller column
     * @return Inventory Table Rows
     */
    private List<String[]> buildInventoryRows(
            List<Inventory> inventories, boolean includeSeller) {

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
                inventory.getTableRows());

    }

    /**
     * Finds Inventory using Inventory ID.
     *
     * @param inventoryId Inventory ID
     * @return Inventory if found, otherwise null
     */
    public Inventory findInventoryById(
            String inventoryId) {

        return DataStore.INVENTORIES.get(
                inventoryId.toUpperCase());

    }

    /**
     * Finds Inventory using Product.
     *
     * @param product Product
     * @return Inventory if found, otherwise null
     */
    public Inventory findInventoryByProduct(
            Product product) {

        return DataStore.INVENTORIES.values()
                .stream()
                .filter(inventory ->
                        inventory.getProduct()
                                .equals(product))
                .findFirst()
                .orElse(null);

    }
    /**
     * Searches Seller Inventory.
     *
     * @param seller Logged-in Seller
     */
    public void searchInventory(Seller seller) {

        viewSellerInventory(seller);

        Inventory inventory =
                getSellerInventoryOrNull(seller);

        if (inventory != null) {

            displayInventory(inventory);

        }

    }

    /**
     * Searches Inventory.
     * Used by Admin.
     */
    public void searchInventory() {

        viewAllInventory();

        Inventory inventory =
                getInventoryOrNull();

        if (inventory != null) {

            displayInventory(inventory);

        }

    }

    /**
     * Updates Seller Stock.
     *
     * @param seller Logged-in Seller
     */
    public void updateStock(Seller seller) {

        viewSellerInventory(seller);

        Inventory inventory =
                getSellerInventoryOrNull(seller);

        if (inventory != null) {

            updateInventory(inventory);

        }

    }

    /**
     * Updates Stock.
     * Used by Admin.
     */
    public void updateStock() {

        viewAllInventory();

        Inventory inventory =
                getInventoryOrNull();

        if (inventory != null) {

            updateInventory(inventory);

        }

    }

    /**
     * Deletes Seller Stock.
     *
     * @param seller Logged-in Seller
     */
    public void deleteStock(Seller seller) {

        viewSellerInventory(seller);

        Inventory inventory =
                getSellerInventoryOrNull(seller);

        if (inventory != null) {

            removeInventory(inventory);

        }

    }

    /**
     * Deletes Inventory.
     * Used by Admin.
     */
    public void deleteStock() {

        viewAllInventory();

        Inventory inventory =
                getInventoryOrNull();

        if (inventory != null) {

            removeInventory(inventory);

        }

    }

    /**
     * Returns Inventory.
     *
     * @return Inventory if found, otherwise null
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

            DisplayUtil.printSuccess(
                    "Inventory Not Found.");

        }

        return inventory;

    }

    /**
     * Returns Seller Inventory.
     *
     * @param seller Logged-in Seller
     * @return Inventory if found, otherwise null
     */
    private Inventory getSellerInventoryOrNull(
            Seller seller) {

        Inventory inventory =
                getInventoryOrNull();

        if (inventory == null) {

            return null;

        }

        if (!inventory.getProduct()
                .getSeller()
                .equals(seller)) {

            DisplayUtil.printSuccess(
                    "Inventory Not Found.");

            return null;

        }

        return inventory;

    }

    /**
     * Returns Product.
     *
     * @return Product if found, otherwise null
     */
    private Product getProductOrNull() {

        String productId =
                InputUtil.readString(
                                "Enter Product ID : ")
                        .trim()
                        .toUpperCase();

        Product product =
                productService.findProductById(productId);

        if (product == null) {

            DisplayUtil.printSuccess(
                    "Product Not Found.");

        }

        return product;

    }

    /**
     * Returns Seller Product.
     *
     * @param seller Logged-in Seller
     * @return Product if found, otherwise null
     */
    private Product getSellerProductOrNull(
            Seller seller) {

        Product product =
                getProductOrNull();

        if (product == null) {

            return null;

        }

        if (!product.getSeller().equals(seller)) {

            DisplayUtil.printSuccess(
                    "Product Not Found.");

            return null;

        }

        return product;

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

                ValidationUtil.validateQuantity(quantity);

                inventory.setQuantity(quantity);

                updateProductStatus(inventory);

                DisplayUtil.printSuccess(
                        "Stock Updated Successfully.");

                break;

            }

            catch (ValidationException exception) {

                DisplayUtil.printSuccess(
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

        DataStore.INVENTORIES.remove(
                inventory.getInventoryId());

        inventory.getProduct()
                .setProductStatus(
                        ProductStatus.OUT_OF_STOCK);

        DisplayUtil.printSuccess(
                "Inventory Deleted Successfully.");

    }

}