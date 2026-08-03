package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.mapper.CategoryMapper;
import com.crimsonlogic.ecommerce.model.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CategoryDAO {

    /**
     * Inserts Category.
     *
     * @param category Category
     */
    public void insertCategory(Category category) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CategoryMapper mapper = session.getMapper(CategoryMapper.class);

            mapper.insertCategory(category);

            session.commit();

        }

    }

    /**
     * Updates Category.
     *
     * @param category Category
     */
    public void updateCategory(Category category) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CategoryMapper mapper = session.getMapper(CategoryMapper.class);

            mapper.updateCategory(category);

            session.commit();

        }

    }

    /**
     * Deletes Category.
     *
     * @param categoryId Category ID
     */
    public void deleteCategory(String categoryId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CategoryMapper mapper = session.getMapper(CategoryMapper.class);

            mapper.deleteCategory(categoryId);

            session.commit();

        }

    }

    /**
     * Finds Category by ID.
     *
     * @param categoryId Category ID
     * @return Category
     */
    public Category findCategoryById(String categoryId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CategoryMapper mapper = session.getMapper(CategoryMapper.class);

            return mapper.findCategoryById(categoryId);

        }

    }

    /**
     * Finds Category by Name.
     *
     * @param categoryName Category Name
     * @return Category
     */
    public Category findCategoryByName(String categoryName) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CategoryMapper mapper = session.getMapper(CategoryMapper.class);

            return mapper.findCategoryByName(categoryName);

        }

    }

    /**
     * Returns all Categories.
     *
     * @return Category List
     */
    public List<Category> findAllCategories() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            CategoryMapper mapper = session.getMapper(CategoryMapper.class);

            return mapper.findAllCategories();

        }

    }

}