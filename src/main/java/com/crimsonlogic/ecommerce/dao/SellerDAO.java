package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.mapper.SellerMapper;
import com.crimsonlogic.ecommerce.model.Seller;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SellerDAO {

    /**
     * Inserts Seller.
     *
     * @param seller Seller
     */
    public void insertSeller(Seller seller) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            SellerMapper mapper = session.getMapper(SellerMapper.class);

            mapper.insertSeller(seller);

            session.commit();

        }

    }

    /**
     * Updates Seller.
     *
     * @param seller Seller
     */
    public void updateSeller(Seller seller) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            SellerMapper mapper = session.getMapper(SellerMapper.class);

            mapper.updateSeller(seller);

            session.commit();

        }

    }

    /**
     * Deletes Seller.
     *
     * @param userId Seller ID
     */
    public void deleteSeller(String userId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            SellerMapper mapper = session.getMapper(SellerMapper.class);

            mapper.deleteSeller(userId);

            session.commit();

        }

    }

    /**
     * Finds Seller by ID.
     *
     * @param userId Seller ID
     * @return Seller
     */
    public Seller findSellerById(String userId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            SellerMapper mapper = session.getMapper(SellerMapper.class);

            return mapper.findSellerById(userId);

        }

    }

    /**
     * Finds Seller by Email.
     *
     * @param email Seller Email
     * @return Seller
     */
    public Seller findSellerByEmail(String email) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            SellerMapper mapper = session.getMapper(SellerMapper.class);

            return mapper.findSellerByEmail(email);

        }

    }

    /**
     * Finds Seller by Phone.
     *
     * @param phone Seller Phone Number
     * @return Seller
     */
    public Seller findSellerByPhone(String phone) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            SellerMapper mapper = session.getMapper(SellerMapper.class);

            return mapper.findSellerByPhone(phone);

        }

    }

    /**
     * Returns all Sellers.
     *
     * @return Seller List
     */
    public List<Seller> findAllSellers() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            SellerMapper mapper = session.getMapper(SellerMapper.class);

            return mapper.findAllSellers();

        }

    }

}