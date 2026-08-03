package com.crimsonlogic.ecommerce.model;

import java.util.Objects;

public class Address {

    /**
     * Address ID.
     */
    private String addressId;

    /* House Number (Example: 45, B-102, Flat-203) */
    private String houseNumber;

    /* Street Name */
    private String street;

    /* City Name */
    private String city;

    /* State Name */
    private String state;

    /* Country Name */
    private String country;

    /* Postal / ZIP Code */
    private String zipCode;

    /**
     * Default Constructor
     * Required by MyBatis.
     */
    public Address() {

    }

    /**
     * Parameterized Constructor
     */
    public Address(
            String addressId,
            String houseNumber,
            String street,
            String city,
            String state,
            String country,
            String zipCode) {

        this.addressId = addressId;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    // ===========================
    // Getters
    // ===========================

    public String getAddressId() {

        return addressId;

    }
    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    // ===========================
    // Setters
    // ===========================

    public void setAddressId(String addressId) {

        this.addressId = addressId;

    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    // ===========================
    // Object Methods
    // ===========================

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof Address address)) {
            return false;
        }

        return Objects.equals(
                addressId,
                address.addressId);

    }
    @Override
    public int hashCode() {
        return Objects.hash(
                addressId,
                houseNumber,
                street,
                city,
                state,
                country,
                zipCode
        );
    }
    @Override
    public String toString() {

        return houseNumber + ", "
                + street + ", "
                + city + ", "
                + state + ", "
                + country + " - "
                + zipCode;
    }
}
