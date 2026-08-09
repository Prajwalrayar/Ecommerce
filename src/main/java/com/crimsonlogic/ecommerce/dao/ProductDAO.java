package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.enums.ProductStatus;
import com.crimsonlogic.ecommerce.mapper.ProductMapper;
import com.crimsonlogic.ecommerce.model.Product;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ProductDAO {

    /**
     * Inserts Product.
     *
     * @param product Product
     */
    public void insertProduct(Product product) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            mapper.insertProduct(product);

            session.commit();

        }

    }

    /**
     * Updates Product.
     *
     * @param product Product
     */
    public void updateProduct(Product product) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            mapper.updateProduct(product);

            session.commit();

        }

    }

    /**
     * Deletes Product.
     *
     * @param productId Product ID
     */
    public void deleteProduct(String productId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            mapper.deleteProduct(productId);

            session.commit();

        }

    }

    /**
     * Finds Product by ID.
     *
     * @param productId Product ID
     * @return Product
     */
    public Product findProductById(String productId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.findProductById(productId);

        }

    }

    /**
     * Finds Product by Name.
     *
     * @param productName Product Name
     * @return Product
     */
    public Product findProductByName(String productName) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.findProductByName(productName);

        }

    }

    /**
     * Returns all Products.
     *
     * @return Product List
     */
    public List<Product> findAllProducts() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.findAllProducts();

        }

    }

    /**
     * Returns Products by Seller.
     *
     * @param sellerId Seller ID
     * @return Product List
     */
    public List<Product> findProductsBySeller(String sellerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.findProductsBySeller(sellerId);

        }

    }

    /**
     * Returns Products by Category.
     *
     * @param categoryId Category ID
     * @return Product List
     */
    public List<Product> findProductsByCategory(String categoryId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.findProductsByCategory(categoryId);

        }

    }

    /**
     * Returns Available Products.
     *
     * @return Product List
     */
    public List<Product> findAvailableProducts() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.findAvailableProducts();

        }

    }

    /**
     * Returns Products by Status.
     *
     * @param status Product Status
     * @return Product List
     */
    public List<Product> findProductsByStatus(ProductStatus status) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.findProductsByStatus(status);

        }

    }

    /**
     * Finds Product by Product ID and Seller ID.
     *
     * @param productId Product ID
     * @param sellerId Seller ID
     * @return Product
     */
    public Product findProductByIdAndSeller(String productId,
                                            String sellerId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.findProductByIdAndSeller(productId, sellerId);

        }

    }

    /**
     * Updates Product Status.
     *
     * @param product Product
     */
    public void updateProductStatus(Product product) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            mapper.updateProductStatus(product);

            session.commit();

        }

    }

    public Double findAverageRating(String productId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.findAverageRating(productId);
        }
    }

    public int countReviews(String productId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            ProductMapper mapper = session.getMapper(ProductMapper.class);

            return mapper.countReviews(productId);
        }
    }

}