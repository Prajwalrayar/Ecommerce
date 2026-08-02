package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.enums.ProductStatus;
import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.Category;
import com.crimsonlogic.ecommerce.model.Product;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.repository.DataStore;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

import java.util.Comparator;
import java.util.List;

/**
 * Service class responsible for Product operations.
 */
public class ProductService {

    private final CategoryService categoryService =
            new CategoryService();

    public ProductService(){

    }
    //  Adds Product.
    public void addProduct(Seller seller) {

        if (DataStore.CATEGORIES.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Categories Available.");

            return;

        }

        createProduct(seller);

    }

    //Displays all Products.
    public void viewAllProducts() {
        if (!hasProducts()) {
            DisplayUtil.printMessage(
                    "No Products Available.");

            return;
        }
        String[] headers = {"Product ID", "Product Name", "Category",
                "Price", "Seller", "Status"};

        DisplayUtil.printTable(
                "AVAILABLE PRODUCTS",
                headers,
                buildProductRows(DataStore.PRODUCTS.values()
                                .stream()
                                .toList())
        );
    }

    // Displays Products added by the Seller.
    public void viewSellerProducts(Seller seller) {

        List<Product> sellerProducts = DataStore.PRODUCTS.values()
                        .stream()
                        .filter(product -> product.getSeller()
                                        .equals(seller))
                        .toList();
        if (sellerProducts.isEmpty()) {
            DisplayUtil.printMessage("No Products Available.");
            return;
        }

        String[] headers = {

                "Product ID",

                "Product Name",

                "Category",

                "Price",

                "Status"

        };

        List<String[]> rows =

                sellerProducts.stream()

                        .map(product ->

                                new String[]{

                                        product.getProductId(),

                                        product.getProductName(),

                                        product.getCategory()

                                                .getCategoryName(),

                                        String.valueOf(

                                                product.getProductPrice()),

                                        product.getProductStatus()

                                                .name()

                                })

                        .toList();

        DisplayUtil.printTable(

                "MY PRODUCTS",

                headers,

                rows);

    }

    /**
     * Searches Product.
     */
    public void searchProduct() {

        if (!hasProducts()) {
            DisplayUtil.printMessage(
                    "No Products Available.");

            return;

        }

        viewAllProducts();

        Product product =
                getProductOrNull();

        if (product != null) {

            displayProduct(product);

        }

    }

    //Updates a Product.
    public void updateProduct(Seller seller) {

        viewSellerProducts(seller);

        Product product =
                getSellerProductOrNull(seller);

        if (product != null) {

            updateExistingProduct(product);

        }

    }

    // Deletes a Product.
    public void deleteProduct(Seller seller) {

        viewSellerProducts(seller);

        Product product =
                getSellerProductOrNull(seller);

        if (product != null) {

            removeProduct(product);

        }

    }
    public void deleteProductByAdmin() {

        viewAllProducts();

        Product product =
                getProductOrNull();

        if (product != null) {

            removeProduct(product);

        }

    }

    // Displays all available Products.
    /**
     * Displays Products for Customers.
     */
    public void browseProducts() {
        if (!hasProducts()) {
            return;
        }
        viewAllProducts();
    }

    //  Displays Products belonging to a Category.
    public void filterProductsByCategory() {

        if (!hasProducts()) {
            DisplayUtil.printMessage(
                    "No Products Available.");

            return;
        }
        categoryService.viewAllCategories();

        String categoryId =
                InputUtil.readString(
                                "Enter Category ID : ")
                        .trim()
                        .toUpperCase();

        List<Product> products =
                DataStore.PRODUCTS.values()
                        .stream()
                        .filter(product ->
                                product.getCategory()
                                        .getCategoryId()
                                        .equalsIgnoreCase(categoryId))
                        .toList();

        if (products.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Products Found.");

            return;

        }

        String[] headers = {
                "Product ID",
                "Product Name",
                "Category",
                "Price (₹)",
                "Seller",
                "Status"
        };

        List<String[]> rows =
                products.stream()
                        .map(product -> new String[]{
                                product.getProductId(),
                                product.getProductName(),
                                product.getCategory()
                                        .getCategoryName(),
                                String.format("%.2f",
                                        product.getProductPrice()),
                                product.getSeller()
                                        .getShopName(),
                                product.getProductStatus()
                                        .name()
                        })
                        .toList();

        DisplayUtil.printTable(
                "CATEGORY PRODUCTS",
                headers,
                rows);

    }

    // Displays Products sorted by Price.
    public void sortProductsByPrice() {

        if (!hasProducts()) {
            DisplayUtil.printMessage(
                    "No Products Available.");

            return;
        }

        String[] headers = {
                "Product ID",
                "Product Name",
                "Category",
                "Price (₹)",
                "Seller",
                "Status"
        };

        List<String[]> rows =
                DataStore.PRODUCTS.values()
                        .stream()
                        .sorted(Comparator.comparing(Product::getProductPrice))
                        .map(product -> new String[]{
                                product.getProductId(),
                                product.getProductName(),
                                product.getCategory()
                                        .getCategoryName(),
                                String.format("%.2f",
                                        product.getProductPrice()),
                                product.getSeller()
                                        .getShopName(),
                                product.getProductStatus()
                                        .name()
                        })
                        .toList();

        DisplayUtil.printTable(
                "PRODUCTS SORTED BY PRICE",
                headers,
                rows);

    }

    // ---------------------------------------------------------

    // Creates Product.
    private void createProduct(Seller seller) {

        while (true) {
            try {
                System.out.println("\n========== ADD PRODUCT ==========");

                String productName = InputUtil.readString("Enter Product Name : ");
                ValidationUtil.validateProductName(productName);
                if (isDuplicateProduct(productName, seller)) {
                    DisplayUtil.printSuccess("Product Already Exists.");
                    return;
                }

                String description = InputUtil.readString("Enter Product Description : ");

                ValidationUtil.validateProductDescription(description);

                Category category =getCategoryOrNull();

                if (category == null) {
                    continue;
                }

                double price = InputUtil.readDouble("Enter Product Price : ");

                ValidationUtil.validateProductPrice(price);

                Product product = new Product(generateProductId(), productName,
                        description, price, category, seller, ProductStatus.AVAILABLE);

                DataStore.PRODUCTS.put(product.getProductId(), product);
                DisplayUtil.printSuccess("Product Added Successfully.");
                System.out.println("Product ID : " + product.getProductId());
                break;
            }

            catch (ValidationException exception) {
                DisplayUtil.printMessage(exception.getMessage());
            }
        }
    }

    // Checks whether Product already exists.
    private boolean isDuplicateProduct(String productName, Seller seller) {

        return DataStore.PRODUCTS.values()

                .stream()

                .anyMatch(product ->

                        product.getSeller().equals(seller)

                                &&

                                product.getProductName()

                                        .equalsIgnoreCase(productName));

    }

    // Generates Product ID.
    private String generateProductId() {String productId;

        do {

            productId =
                    IdGenerator.generateId("PRO");

        }

        while (DataStore.PRODUCTS.containsKey(productId));

        return productId;

    }

    // Returns Category if found.

    private Category getCategoryOrNull() {

        categoryService.viewAllCategories();

        String categoryId =
                InputUtil.readString("Enter Category ID : ")
                        .trim().toUpperCase();

        Category category = DataStore.CATEGORIES.get(categoryId);

        if (category == null) {
            DisplayUtil.printMessage("Category Not Found.");
        }

        return category;

    }
    // Builds Product Table Rows.

    private List<String[]> buildProductRows(List<Product> products) {

        return products.stream()

                .map(product -> new String[]{

        product.getProductId(),

        product.getProductName(),

        product.getCategory()
                                .getCategoryName(),

        String.valueOf(
        product.getProductPrice()),

        product.getSeller()
                                .getShopName(),

        product.getProductStatus()
                                .name()

                })

                .toList();

    }

    // Returns Product if found.

    private Product getProductOrNull() {

        String productId =
                InputUtil.readString(
                                "Enter Product ID : ")
                        .trim()
                        .toUpperCase();

        Product product =
                findProductById(productId);

        if (product == null) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

        }

        return product;

    }

    // Returns Seller Product.

    private Product getSellerProductOrNull(Seller seller) {
        Product product = getProductOrNull();
        if (product == null) {
            return null;
        }
        if (!product.getSeller().equals(seller)) {
            DisplayUtil.printMessage("Product Not Found.");
            return null;
        }
        return product;
    }

    // Displays Product Details.

    private void displayProduct(Product product) {

        DisplayUtil.printTable(

                "PRODUCT DETAILS",

                new String[]{
                        "Field",
                        "Value"
                },

                product.getTableRows());

    }

    // Finds Product by Product ID.
    public Product findProductById(String productId) {

        return DataStore.PRODUCTS.get(
                productId.toUpperCase());

    }
    /**
     * Finds Product using Product Name.
     *
     * @param productName Product Name
     * @return Product
     */
    public Product findProductByName(String productName) {

        return DataStore.PRODUCTS.values()
                .stream()
                .filter(product ->
                        product.getProductName()
                                .equalsIgnoreCase(productName))
                .findFirst()
                .orElse(null);

    }

    /**
     * Checks whether Products are available.
     *
     * @return true if Products exist, otherwise false
     */
    public boolean hasProducts() {

        return !DataStore.PRODUCTS.isEmpty();

    }

    // Updates Existing Product.
    private void updateExistingProduct(Product product) {
        while (true) {
            try {
                String productName = InputUtil.readOptionalString(
                                "Enter Product Name: ");
                if (productName != null) {
                    ValidationUtil.validateProductName(productName);
                    product.setProductName(productName);
                }
                String description = InputUtil.readOptionalString(
                                "Enter Product Description (Press Enter to Skip): ");
                if (description != null) {
                    ValidationUtil.validateProductDescription(description);
                    product.setProductDescription(description);
                }
                Double price = InputUtil.readOptionalDouble(
                                "Enter Product Price (Press Enter to Skip): ");
                if (price != null) {
                    ValidationUtil.validateProductPrice(price);
                    product.setProductPrice(price);
                }
                DisplayUtil.printSuccess("Product Updated Successfully.");
                break;
            }
            catch (ValidationException exception) {
                DisplayUtil.printMessage(exception.getMessage());
            }

        }

    }
    private void removeProduct(Product product) {
        DataStore.PRODUCTS.remove(product.getProductId());

        DisplayUtil.printSuccess("Product Deleted Successfully.");
    }
}