package com.crimsonlogic.ecommerce.model;

import java.util.Objects;

public class Address {

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
    public Address(String houseNumber,
                   String street,
                   String city,
                   String state,
                   String country,
                   String zipCode) {

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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Address))
            return false;
        Address other = (Address) obj;
        return Objects.equals(houseNumber, other.houseNumber)
                && Objects.equals(street, other.street)
                && Objects.equals(city, other.city)
                && Objects.equals(state, other.state)
                && Objects.equals(country, other.country)
                && Objects.equals(zipCode, other.zipCode);
    }
    @Override
    public int hashCode() {
        return Objects.hash(
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
