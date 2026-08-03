package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.mapper.AdminMapper;
import com.crimsonlogic.ecommerce.model.Admin;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AdminDAO {

    /**
     * Inserts Admin.
     *
     * @param admin Admin
     */
    public void insertAdmin(Admin admin) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AdminMapper mapper = session.getMapper(AdminMapper.class);

            mapper.insertAdmin(admin);

            session.commit();

        }

    }

    /**
     * Updates Admin.
     *
     * @param admin Admin
     */
    public void updateAdmin(Admin admin) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AdminMapper mapper = session.getMapper(AdminMapper.class);

            mapper.updateAdmin(admin);

            session.commit();

        }

    }

    /**
     * Deletes Admin.
     *
     * @param userId Admin ID
     */
    public void deleteAdmin(String userId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AdminMapper mapper = session.getMapper(AdminMapper.class);

            mapper.deleteAdmin(userId);

            session.commit();

        }

    }

    /**
     * Finds Admin by ID.
     *
     * @param userId Admin ID
     * @return Admin
     */
    public Admin findAdminById(String userId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AdminMapper mapper = session.getMapper(AdminMapper.class);

            return mapper.findAdminById(userId);

        }

    }

    /**
     * Finds Admin by Email.
     *
     * @param email Email
     * @return Admin
     */
    public Admin findAdminByEmail(String email) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AdminMapper mapper = session.getMapper(AdminMapper.class);

            return mapper.findAdminByEmail(email);

        }

    }

    /**
     * Returns all Admins.
     *
     * @return Admin List
     */
    public List<Admin> findAllAdmins() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AdminMapper mapper = session.getMapper(AdminMapper.class);

            return mapper.findAllAdmins();

        }

    }

    /**
     * Finds Admin by Phone.
     *
     * @param phone Phone Number
     * @return Admin
     */
    public Admin findAdminByPhone(String phone) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AdminMapper mapper = session.getMapper(AdminMapper.class);

            return mapper.findAdminByPhone(phone);

        }

    }

}