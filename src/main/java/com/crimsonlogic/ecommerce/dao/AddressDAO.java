package com.crimsonlogic.ecommerce.dao;

import com.crimsonlogic.ecommerce.config.MyBatisUtil;
import com.crimsonlogic.ecommerce.mapper.AddressMapper;
import com.crimsonlogic.ecommerce.model.Address;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AddressDAO {

    public void insertAddress(Address address) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AddressMapper mapper = session.getMapper(AddressMapper.class);

            mapper.insertAddress(address);

            session.commit();
        }

    }

    public void updateAddress(Address address) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AddressMapper mapper = session.getMapper(AddressMapper.class);

            mapper.updateAddress(address);

            session.commit();
        }

    }

    public void deleteAddress(String addressId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AddressMapper mapper = session.getMapper(AddressMapper.class);

            mapper.deleteAddress(addressId);

            session.commit();
        }

    }

    public Address findAddressById(String addressId) {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AddressMapper mapper = session.getMapper(AddressMapper.class);

            return mapper.findAddressById(addressId);

        }

    }

    public List<Address> findAllAddresses() {

        try (SqlSession session = MyBatisUtil.getFactory().openSession()) {

            AddressMapper mapper = session.getMapper(AddressMapper.class);

            return mapper.findAllAddresses();

        }

    }

}