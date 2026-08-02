package com.crimsonlogic.ecommerce.model;

import com.crimsonlogic.ecommerce.enums.ProductStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Product in the E-Commerce Marketplace.
 */
public class Product {

    /**
     * Product ID.
     */
    private String productId;

    /**
     * Product Name.
     */
    private String productName;

    /**
     * Product Description.
     */
    private String productDescription;

    /**
     * Product Price.
     */
    private double productPrice;

    /**
     * Product Category.
     */
    private Category category;

    /**
     * Seller who owns this Product.
     */
    private Seller seller;

    /**
     * Product Status.
     */
    private ProductStatus productStatus;

    /**
     * Default Constructor.
     */
    public Product() {

    }

    /**
     * Parameterized Constructor.
     *
     * @param productId          Product ID
     * @param productName        Product Name
     * @param productDescription Product Description
     * @param productPrice       Product Price
     * @param category           Product Category
     * @param seller             Seller
     * @param productStatus      Product Status
     */
    public Product(String productId, String productName, String productDescription, double productPrice, Category category, Seller seller, ProductStatus productStatus) {

        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.category = category;
        this.seller = seller;
        this.productStatus = productStatus;

    }

    // =====================================================
    // Getters & Setters
    // =====================================================

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    // =====================================================
    // Object Methods
    // =====================================================

    /**
     * Two Products are equal if their Product IDs are equal.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Product product = (Product) object;

        return Objects.equals(productId, product.productId);

    }

    /**
     * HashCode based on Product ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    /**
     * Returns Product information.
     */
    @Override
    public String toString() {

        return "Product{" + "productId='" + productId + '\'' + ", productName='" + productName + '\'' + ", category=" + category.getCategoryName() + ", seller=" + seller.getUserName() + ", price=" + productPrice + ", status=" + productStatus + '}';

    }

    /**
     * Returns Product Details for table display.
     *
     * @return Product Details
     */
    public List<String[]> getTableRows() {

        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{"Product ID", productId});

        rows.add(new String[]{"Product Name", productName});

        rows.add(new String[]{"Description", productDescription});

        rows.add(new String[]{"Category", category.getCategoryName()});

        rows.add(new String[]{"Seller", seller.getUserName()});

        rows.add(new String[]{"Price", String.format("%.2f", productPrice)});

        rows.add(new String[]{"Status", productStatus.name()});

        return rows;

    }

}