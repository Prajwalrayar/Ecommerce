package com.crimsonlogic.ecommerce.mapper;

import com.crimsonlogic.ecommerce.model.Address;

import java.util.List;

public interface AddressMapper {

    // Inserts Address.
    void insertAddress(Address address);

    // Updates Address.
    void updateAddress(Address address);

    // Deletes Address.
    void deleteAddress(String addressId);
    // Finds Address by ID.
    Address findAddressById(String addressId);

    List<Address> findAllAddresses();

}