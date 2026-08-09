package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.enums.ProductStatus;
import com.crimsonlogic.ecommerce.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    /**
     * Inserts Product.
     *
     * @param product Product
     */
    void insertProduct(Product product);

    /**
     * Updates Product.
     *
     * @param product Product
     */
    void updateProduct(Product product);

    /**
     * Deletes Product.
     *
     * @param productId Product ID
     */
    void deleteProduct(@Param("productId") String productId);

    /**
     * Finds Product by ID.
     *
     * @param productId Product ID
     * @return Product
     */
    Product findProductById(
            @Param("productId")
            String productId);

    /**
     * Finds Product by Name.
     *
     * @param productName Product Name
     * @return Product
     */
    Product findProductByName(
            @Param("productName")
            String productName);

    /**
     * Returns all Products.
     *
     * @return Product List
     */
    List<Product> findAllProducts();

    /**
     * Returns Products of a Seller.
     *
     * @param sellerId Seller ID
     * @return Product List
     */
    List<Product> findProductsBySeller(
            @Param("sellerId")
            String sellerId);

    /**
     * Returns Products by Category.
     *
     * @param categoryId Category ID
     * @return Product List
     */
    List<Product> findProductsByCategory(
            @Param("categoryId")
            String categoryId);

    /**
     * Returns Available Products.
     *
     * @return Product List
     */
    List<Product> findAvailableProducts();

    /**
     * Updates Product Status.
     *
     * @param product Product
     */
    void updateProductStatus(Product product);

    Product findProductByIdAndSeller(

            @Param("productId")
            String productId,

            @Param("sellerId")
            String sellerId

    );

    List<Product> findProductsByStatus(

            @Param("productStatus")
            ProductStatus productStatus

    );

    Double findAverageRating(
            @Param("productId") String productId);

    int countReviews(
            @Param("productId") String productId);

}