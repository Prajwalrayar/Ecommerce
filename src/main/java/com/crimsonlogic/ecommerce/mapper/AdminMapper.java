package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.model.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {

    void insertAdmin(Admin admin);

    void updateAdmin(Admin admin);

    void deleteAdmin(
            @Param("adminId")
            String adminId);

    Admin findAdminById(
            @Param("adminId")
            String adminId);

    Admin findAdminByEmail(
            @Param("email")
            String email);

    Admin findAdminByPhone(
            @Param("phone")
            String phone);

    List<Admin> findAllAdmins();

}