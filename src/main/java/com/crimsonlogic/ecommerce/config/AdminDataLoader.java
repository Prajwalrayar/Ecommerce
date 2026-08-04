package com.crimsonlogic.ecommerce.config;

import com.crimsonlogic.ecommerce.dao.AdminDAO;
import com.crimsonlogic.ecommerce.model.Admin;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.PasswordUtil;

public class AdminDataLoader {

    public static void loadAdmins() {

        AdminDAO adminDAO = new AdminDAO();

        // Prevent duplicate insertion
        if (!adminDAO.findAllAdmins().isEmpty()) {
            return;
        }

        Admin admin1 = new Admin(
                IdGenerator.generateId("ADM"),
                "ADMINISTRATOR",
                "admin@ecommerce.com",
                "9876543210",
                PasswordUtil.encryptPassword("Admin@123"),
                null
        );

        Admin admin2 = new Admin(
                IdGenerator.generateId("ADM"),
                "Naveen",
                "naveen@ecommerce.com",
                "9876543211",
                PasswordUtil.encryptPassword("Naveen@123"),
                null
        );

        Admin admin3 = new Admin(
                IdGenerator.generateId("ADM"),
                "Rahul",
                "rahul@ecommerce.com",
                "9876543212",
                PasswordUtil.encryptPassword("Rahul@123"),
                null
        );

        adminDAO.insertAdmin(admin1);
        adminDAO.insertAdmin(admin2);
        adminDAO.insertAdmin(admin3);

        System.out.println("Default Admins Created Successfully.");

    }

}
