package com.crimsonlogic.ecommerce.config;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Utility class for MyBatis Configuration.
 */
public class MyBatisUtil {

    /**
     * SqlSessionFactory.
     */
    private static SqlSessionFactory factory;
    /**
     * Static block initializes SqlSessionFactory.
     */
    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");

            factory = new SqlSessionFactoryBuilder().build(reader);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    // Returns SqlSessionFactory.

    public static SqlSessionFactory getFactory() {
        return factory;
    }

}