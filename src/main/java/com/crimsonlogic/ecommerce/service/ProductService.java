package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.CategoryDAO;
import com.crimsonlogic.ecommerce.dao.InventoryDAO;
import com.crimsonlogic.ecommerce.dao.ProductDAO;
import com.crimsonlogic.ecommerce.enums.ProductStatus;
import com.crimsonlogic.ecommerce.exceptionhandling.ValidationException;
import com.crimsonlogic.ecommerce.model.Category;
import com.crimsonlogic.ecommerce.model.Inventory;
import com.crimsonlogic.ecommerce.model.Product;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

import java.util.Comparator;
import java.util.List;

import static com.crimsonlogic.ecommerce.util.ValidationUtil.isSameProductName;

/**
 * Service class responsible for Product operations.
 */
public class ProductService {

    /**
     * Category Service.
     */
    private final CategoryService categoryService =
            new CategoryService();

    /**
     * Category DAO.
     */
    private final CategoryDAO categoryDAO =
            new CategoryDAO();

    /**
     * Product DAO.
     */
    private final ProductDAO productDAO =
            new ProductDAO();

    private final InventoryDAO inventoryDAO =
            new InventoryDAO();
    private final InventoryService inventoryService =
            new InventoryService();

    /**
     * Product Table Headers.
     */
    private static final String[] PRODUCT_HEADERS = {

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

    /**
     * Default Constructor.
     */
    public ProductService() {

    }

    /**
     * Adds Product.
     *
     * @param seller Seller
     */
    public void addProduct(Seller seller) {

        if (!validateCategoriesAvailable()) {

            return;

        }

        Product product = createProduct(seller);

        if (product == null) {

            return;

        }

        inventoryService.addInitialInventory(product);

    }

    /**
     * Displays all Products.
     */
    public void viewAllProducts() {

        displayProducts(

                productDAO.findAllProducts(),

                "AVAILABLE PRODUCTS"

        );

    }

    /**
     * Displays Seller Products.
     *
     * @param seller Seller
     */
    public void viewSellerProducts(Seller seller) {

        displayProducts(

                productDAO.findProductsBySeller(
                        seller.getUserId()),

                "MY PRODUCTS"

        );

    }

    /**
     * Displays Products for Customers.
     */
    public Product browseProducts() {

        while (true) {

            System.out.println("\n==========================================");
            System.out.println("          BROWSE PRODUCTS");
            System.out.println("==========================================");
            System.out.println("SEARCH");
            System.out.println("FILTER");
            System.out.println("SORT");
            System.out.println("BACK");
            System.out.println("==========================================");

            String choice =
                    InputUtil.readString("Enter Choice : ")
                            .trim()
                            .toLowerCase();

            switch (choice) {

                case "search":

                    Product searchedProduct =
                            searchProductForCart();

                    if (searchedProduct != null) {
                        return searchedProduct;
                    }

                    break;

                case "filter":

                    filterProductsByCategory();

                    break;

                case "sort":

                    sortProductsByPrice();

                    break;

                case "back":

                    return null;

                default:

                    DisplayUtil.printInvalidChoice();
            }
        }
    }
    /**
     * Searches Products by Product Name.
     */
    public void searchProduct() {

        if (!validateProductsAvailable()) {
            return;
        }

        String keyword = InputUtil.readString("Enter Product Name : ").trim();

        if (keyword.isEmpty()) {

            DisplayUtil.printMessage(
                    "Product Name Cannot Be Empty.");

            return;
        }

        List<Product> products =
                productDAO.findAllProducts()
                        .stream()
                        .filter(product ->
                                product.getProductName()
                                        .toLowerCase()
                                        .contains(
                                                keyword.toLowerCase()))
                        .toList();

        if (products.isEmpty()) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

            return;
        }

        displayProducts(
                products,
                "SEARCH RESULT");
    }

    /**
     * Updates Product.
     *
     * @param seller Seller
     */
    public void updateProduct(Seller seller) {

        if (!validateProductsAvailable()) {

            return;

        }

        viewSellerProducts(seller);

        Product product =
                getSellerProductOrNull(seller);

        if (product != null) {

            updateExistingProduct(product);

        }

    }

    /**
     * Deletes Product.
     *
     * @param seller Seller
     */
    public void deleteProduct(Seller seller) {

        if (!validateProductsAvailable()) {

            return;

        }

        viewSellerProducts(seller);

        Product product =
                getSellerProductOrNull(seller);

        if (product != null) {

            removeProduct(product);

        }

    }

    /**
     * Deletes Product by Admin.
     */
    public void deleteProductByAdmin() {

        if (!validateProductsAvailable()) {

            return;

        }

        viewAllProducts();

        Product product =
                getProductOrNull();

        if (product != null) {

            removeProduct(product);

        }

    }
    /**
     * Filters Products by Category.
     */
    public void filterProductsByCategory() {

        if (!validateProductsAvailable()) {

            return;

        }

        categoryService.viewAllCategories();

        String categoryId =
                InputUtil.readString(
                                "Enter Category ID : ")
                        .trim()
                        .toUpperCase();

        List<Product> products =
                productDAO.findProductsByCategory(categoryId);

        if (products.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Products Found.");

            return;

        }

        displayProducts(
                products,
                "CATEGORY PRODUCTS");

    }

    /**
     * Displays Products sorted by Price.
     */
    public void sortProductsByPrice() {

        if (!validateProductsAvailable()) {

            return;

        }

        List<Product> products =
                productDAO.findAllProducts()
                        .stream()
                        .sorted(Comparator.comparing(
                                Product::getProductPrice))
                        .toList();

        displayProducts(
                products,
                "PRODUCTS SORTED BY PRICE");

    }

    /**
     * Creates Product.
     *
     * @param seller Seller
     */
    private Product createProduct(Seller seller) {

        while (true) {

            try {

                System.out.println(
                        "\n========== ADD PRODUCT ==========");

                String productName =
                        InputUtil.readString(
                                "Enter Product Name : ");

                ValidationUtil.validateProductName(
                        productName);

                if (isDuplicateProduct(
                        productName,
                        seller)) {

                    DisplayUtil.printMessage(
                            "Product Already Exists.");

                    return null;

                }

                String brand = InputUtil.readString("Enter Product brand: ");

                String description =
                        InputUtil.readString(
                                "Enter Product Description : ");

                ValidationUtil.validateProductDescription(
                        description);

                Category category =
                        getCategoryOrNull();

                if (category == null) {

                    continue;

                }

                double price =
                        InputUtil.readDouble(
                                "Enter Product Price : ");

                ValidationUtil.validateProductPrice(
                        price);

                Product product =
                        new Product(

                                generateProductId(),

                                productName,
                                brand,

                                description,

                                price,

                                category,

                                seller,

                                ProductStatus.AVAILABLE

                        );

                productDAO.insertProduct(
                        product);

                DisplayUtil.printSuccess(
                        "Product Added Successfully.");

                System.out.println(
                        "Product ID : "
                                + product.getProductId());


                return product;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

            }

        }

    }

    /**
     * Displays Product List.
     *
     * @param products Product List
     * @param title    Table Title
     */
    private void displayProducts(
            List<Product> products,
            String title) {

        if (products.isEmpty()) {

            DisplayUtil.printMessage(
                    "No Products Available.");

            return;

        }

        DisplayUtil.printTable(

                title,

                PRODUCT_HEADERS,

                buildProductRows(products)

        );

    }

    /**
     * Builds Product Table Rows.
     *
     * @param products Product List
     * @return Table Rows
     */
    private List<String[]> buildProductRows(
            List<Product> products) {

        return products.stream()

                .map(product -> {

                    Inventory inventory =
                            inventoryDAO.findInventoryByProduct(
                                    product.getProductId());

                    String stock = inventory == null
                            ? "0"
                            : String.valueOf(
                            inventory.getQuantity());

                    return new String[]{

                            product.getProductId(),

                            product.getProductName(),
                            product.getBrand(),

                            product.getCategory()
                                    .getCategoryName(),

                            String.format(
                                    "%.2f",
                                    product.getProductPrice()),

                            stock,

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

    }
    /**
     * Returns Category if found.
     *
     * @return Category
     */
    private Category getCategoryOrNull() {

        categoryService.viewAllCategories();

        String categoryId =
                InputUtil.readString(
                                "Enter Category ID : ")
                        .trim()
                        .toUpperCase();

        Category category =
                categoryDAO.findCategoryById(categoryId);

        if (category == null) {

            DisplayUtil.printMessage(
                    "Category Not Found.");

        }

        return category;

    }

    /**
     * Returns Product if found.
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
                productDAO.findProductById(productId);

        if (product == null) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

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

        Product product = getProductOrNull();

        if (product == null) {

            return null;

        }

        if (!product.getSeller()
                .getUserId()
                .equals(seller.getUserId())) {

            DisplayUtil.printMessage(
                    "Product Does Not Belong To You.");

            return null;

        }

        return product;

    }

    /**
     * Displays Product Details.
     *
     * @param product Product
     */
    private void displayProduct(Product product) {

        DisplayUtil.printTable(

                "PRODUCT DETAILS",

                new String[]{
                        "Field",
                        "Value"
                },

                product.getTableRows()

        );

    }

    public List<Product> getAvailableProductsForCart() {

        return productDAO.findAvailableProducts();
    }

    /**
     * Finds Product by Product ID.
     *
     * @param productId Product ID
     * @return Product
     */
    public Product findProductById(
            String productId) {

        return productDAO.findProductById(
                productId);

    }

    /**
     * Finds Product by Product Name.
     *
     * @param productName Product Name
     * @return Product
     */
    public Product findProductByName(
            String productName) {

        if (productName == null
                || productName.isBlank()) {

            return null;
        }

        return productDAO.findAllProducts()
                .stream()
                .filter(product ->
                        isSameProductName(
                                product.getProductName(),
                                productName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks whether Products exist.
     *
     * @return true if Products exist
     */
    public boolean hasProducts() {

        return !productDAO.findAllProducts()
                .isEmpty();

    }

    /**
     * Validates Product Availability.
     *
     * @return true if Products exist
     */
    private boolean validateProductsAvailable() {

        if (!hasProducts()) {

            DisplayUtil.printMessage(
                    "No Products Available.");

            return false;

        }

        return true;

    }

    /**
     * Validates Category Availability.
     *
     * @return true if Categories exist
     */
    private boolean validateCategoriesAvailable() {

        if (categoryDAO.findAllCategories()
                .isEmpty()) {

            DisplayUtil.printMessage(
                    "No Categories Available.");

            return false;

        }

        return true;

    }

    /**
     * Checks Duplicate Product.
     *
     * @param productName Product Name
     * @param seller Seller
     * @return true if duplicate
     */
    private boolean isDuplicateProduct(
            String productName,
            Seller seller) {

        return productDAO.findProductsBySeller(
                        seller.getUserId())
                .stream()
                .anyMatch(product ->
                        isSameProductName(
                                product.getProductName(),
                                productName));

    }

    /**
     * Generates Product ID.
     *
     * @return Product ID
     */
    private String generateProductId() {

        String productId;

        do {

            productId =
                    IdGenerator.generateId("PRO");

        } while (productDAO.findProductById(productId)
                != null);

        return productId;

    }

    /**
     * Updates Existing Product.
     *
     * @param product Product
     */
    private void updateExistingProduct(Product product) {

        while (true) {
            try {

                String productName = InputUtil.readString("Enter Product Name: ");

                if (productName != null) {

                    ValidationUtil.validateProductName(productName);

                    product.setProductName(productName);

                }

                String description = InputUtil.readOptionalString("Enter Product Description: ");

                if (description != null) {

                    ValidationUtil.validateProductDescription(description);

                    product.setProductDescription(description);

                }

                Double price = InputUtil.readOptionalDouble("Enter Product Price: ");

                if (price != null) {
                    ValidationUtil.validateProductPrice(price);
                    product.setProductPrice(price);

                }

                productDAO.updateProduct(product);

                DisplayUtil.printSuccess("Product Updated Successfully.");
                break;

            } catch (ValidationException exception) {
                DisplayUtil.printMessage(exception.getMessage());
            }

        }

    }

    /**
     * Removes Product.
     *
     * @param product Product
     */
    private void removeProduct(
            Product product) {

        productDAO.deleteProduct(
                product.getProductId());

        DisplayUtil.printSuccess(
                "Product Deleted Successfully.");

    }

    public Product searchProductForCart() {

        if (!validateProductsAvailable()) {
            return null;
        }

        String keyword =
                InputUtil.readString(
                                "Enter Product Name : ")
                        .trim();

        if (keyword.isEmpty()) {

            DisplayUtil.printMessage(
                    "Product Name Cannot Be Empty.");

            return null;
        }

        List<Product> products =
                productDAO.findAllProducts()
                        .stream()
                        .filter(product ->
                                product.getProductName()
                                        .toLowerCase()
                                        .contains(
                                                keyword.toLowerCase()))
                        .toList();

        if (products.isEmpty()) {

            DisplayUtil.printMessage(
                    "Product Not Found.");

            return null;
        }

        displayProducts(
                products,
                "SEARCH RESULT");

        String productId =
                InputUtil.readString(
                                "Enter Product ID : ")
                        .trim()
                        .toUpperCase();

        Product product =
                products.stream()
                        .filter(currentProduct ->
                                currentProduct.getProductId()
                                        .equalsIgnoreCase(
                                                productId))
                        .findFirst()
                        .orElse(null);

        if (product == null) {

            DisplayUtil.printMessage(
                    "Invalid Product ID.");

            return null;
        }

        return product;
    }

}