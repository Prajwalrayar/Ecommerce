package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.exceptionhandling.user.DuplicateUserException;
import com.crimsonlogic.ecommerce.exceptionhandling.user.InvalidCredentialsException;
import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.Address;
import com.crimsonlogic.ecommerce.model.Admin;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.repository.DataStore;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.IdGenerator;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

import java.util.Optional;

/**
 * Handles registration, login and logout operations
 * for all users of the application.
 */
public class AuthenticationService {

    /**
     * Registers a new Seller.
     *
     * @return Registered Seller
     */
    public Seller registerSeller() {

        DisplayUtil.printMessage("Enter Seller Details");

        String name = readValidUserName();

        String email = readValidEmail();

        String phone = readValidPhone();

        String password = readValidPassword();

        String shopName;

        while (true) {

            try {

                shopName = InputUtil.readString("Enter Shop Name: ");

                ValidationUtil.validateShopName(shopName);

                break;

            } catch (ValidationException exception) {

                System.out.println(exception.getMessage());

            }

        }

        String shopAddress =
                InputUtil.readString("Enter Shop Address: ");

//        Address address = createAddress();

        Seller seller = new Seller(IdGenerator.generateId("SEL"),
                name, email, phone, password,null, shopName, shopAddress);

        DataStore.SELLERS.put(
                seller.getUserId(),
                seller);

        DisplayUtil.printSuccess("Registered Successfully.");
        System.out.println("Seller ID : " + seller.getUserId());

        return seller;

    }

    /**
     * Creates Address.
     *
     * @return Address
     */
    private Address createAddress() {

        while (true) {

            try {

                String houseNumber =
                        InputUtil.readOptionalString(
                                "Enter House Number (Press Enter to Skip): ");

                if (houseNumber == null) {
                    return null;
                }

                String street =
                        InputUtil.readString("Enter Street: ");

                String city =
                        InputUtil.readString("Enter City: ");

                String state =
                        InputUtil.readString("Enter State: ");

                String country =
                        InputUtil.readString("Enter Country: ");

                String zipCode =
                        InputUtil.readString("Enter Zip Code: ");

                Address address = new Address(
                        houseNumber,
                        street,
                        city,
                        state,
                        country,
                        zipCode);

                ValidationUtil.validateAddress(address);

                return address;

            } catch (ValidationException exception) {

                System.out.println(exception.getMessage());

            }

        }

    }

    /**
     * Reads a valid User Name.
     *
     * @return Valid User Name
     */
    private String readValidUserName() {

        while (true) {

            try {

                String name =
                        InputUtil.readString("Enter Name: ");

                ValidationUtil.validateUserName(name);

                return name;

            } catch (ValidationException exception) {

                System.out.println(exception.getMessage());

            }

        }

    }

    /**
     * Reads a valid Email Address.
     *
     * @return Valid Email
     */
    private String readValidEmail() {

        while (true) {
            try {
                String email = InputUtil.readString("Enter Email: ");
                ValidationUtil.validateEmail(email);

                if (isEmailExists(email)) {
                    throw new DuplicateUserException("Email already registered.");
                }
                return email;
            } catch (ValidationException | DuplicateUserException exception) {
                System.out.println(exception.getMessage()+ "Example : abc@domain.com");
            }

        }

    }

    /**
     * Reads a valid Phone Number.
     *
     * @return Valid Phone Number
     */
    private String readValidPhone() {

        while (true) {
            try {
                String phone = InputUtil.readString("Enter Phone Number: ");
                ValidationUtil.validatePhone(phone);
                if (isPhoneExists(phone)) {
                    throw new DuplicateUserException("Phone Number already registered.");
                }
                return phone;
            } catch (ValidationException | DuplicateUserException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * Reads a valid Password.
     *
     * @return Valid Password
     */
    private String readValidPassword() {

        while (true) {

            try {

                String password =
                        InputUtil.readString(
                                "Enter Password: ");

                ValidationUtil.validatePassword(password);

                return password;

            } catch (ValidationException exception) {

                System.out.println(exception.getMessage());

            }

        }

    }

    /**
     * Checks whether Email already exists.
     *
     * @param email Email Address
     * @return true if exists
     */
    private boolean isEmailExists(String email) {

        return DataStore.ADMINS.values()
                .stream()
                .anyMatch(admin ->
                        admin.getUserEmail()
                                .equalsIgnoreCase(email))
                || DataStore.SELLERS.values()
                .stream()
                .anyMatch(seller ->
                        seller.getUserEmail()
                                .equalsIgnoreCase(email))
                || DataStore.CUSTOMERS.values()
                .stream()
                .anyMatch(customer ->
                        customer.getUserEmail()
                                .equalsIgnoreCase(email));

    }

    /**
     * Checks whether Phone Number already exists.
     *
     * @param phone Phone Number
     * @return true if exists
     */
    private boolean isPhoneExists(String phone) {

        return DataStore.ADMINS.values()
                .stream()
                .anyMatch(admin ->
                        admin.getUserPhNo()
                                .equals(phone))
                || DataStore.SELLERS.values()
                .stream()
                .anyMatch(seller ->
                        seller.getUserPhNo()
                                .equals(phone))
                || DataStore.CUSTOMERS.values()
                .stream()
                .anyMatch(customer ->
                        customer.getUserPhNo()
                                .equals(phone));

    }

    /**
     * Finds Seller by Email.
     *
     * @param email Seller Email
     * @return Seller
     */
    private Optional<Seller> findSellerByEmail(String email) {

        return DataStore.SELLERS.values()
                .stream()
                .filter(seller ->
                        seller.getUserEmail()
                                .equalsIgnoreCase(email))
                .findFirst();

    }

    /**
     * Registers a new Customer.
     *
     * @return Registered Customer
     */
    public Customer registerCustomer() {

        DisplayUtil.printMessage(
                "Enter Customer Details");

        String name = readValidUserName();

        String email = readValidEmail();

        String phone = readValidPhone();

        String password = readValidPassword();

        Address address = createAddress();

        Customer customer = new Customer(IdGenerator.generateId("CUS"),
                name, email, phone, password, address);

        DataStore.CUSTOMERS.put(
                customer.getUserId(),
                customer);

        DisplayUtil.printSuccess("Registered Successfully.");
        System.out.println("Customer ID : " + customer.getUserId());

        return customer;

    }
    /**
     * Finds Customer by Email.
     *
     * @param email Customer Email
     * @return Customer
     */
    private Optional<Customer> findCustomerByEmail(String email) {

        return DataStore.CUSTOMERS.values()
                .stream()
                .filter(customer ->
                        customer.getUserEmail()
                                .equalsIgnoreCase(email))
                .findFirst();

    }

    /**
     * Authenticates Admin.
     *
     * @return Logged-in Admin
     */
    public Admin loginAdmin() {

        try {

            DisplayUtil.printMessage("Enter Admin Credentials");

            String email =
                    InputUtil.readString("Enter Email    : ");

            String password =
                    InputUtil.readString("Enter Password : ");

            Optional<Admin> admin =
                    findAdminByEmail(email);

            if (admin.isEmpty()) {
                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");
            }

            if (!admin.get()
                    .getUserPassword()
                    .equals(password)) {

                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");
            }

            DisplayUtil.printMessage("LOGIN SUCCESSFUL");

            return admin.get();

        } catch (InvalidCredentialsException exception) {

            DisplayUtil.printMessage(exception.getMessage());

            return null;

        }

    }

    /**
     * Authenticates Seller.
     *
     * @return Logged-in Seller
     */
    public Seller loginSeller() {

        try {

            DisplayUtil.printMessage("Enter Seller Credentials");

            String email =
                    InputUtil.readString("Enter Email    : ");

            String password =
                    InputUtil.readString("Enter Password : ");

            Optional<Seller> seller =
                    findSellerByEmail(email);

            if (seller.isEmpty()) {
                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");
            }

            if (!seller.get()
                    .getUserPassword()
                    .equals(password)) {

                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");
            }

            DisplayUtil.printMessage("LOGIN SUCCESSFUL");

            return seller.get();

        } catch (InvalidCredentialsException exception) {

            DisplayUtil.printMessage(exception.getMessage());

            return null;

        }

    }

    /**
     * Authenticates Customer.
     *
     * @return Logged-in Customer
     */
    public Customer loginCustomer() {

        try {

            DisplayUtil.printMessage("Enter Customer Credentials");

            String email =
                    InputUtil.readString("Enter Email    : ");

            String password =
                    InputUtil.readString("Enter Password : ");

            Optional<Customer> customer =
                    findCustomerByEmail(email);

            if (customer.isEmpty()) {
                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");
            }

            if (!customer.get()
                    .getUserPassword()
                    .equals(password)) {

                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");
            }

            DisplayUtil.printMessage("LOGIN SUCCESSFUL");

            return customer.get();

        } catch (InvalidCredentialsException exception) {

            DisplayUtil.printMessage(exception.getMessage());

            return null;

        }

    }

    /**
     * Logs out the current user.
     */
    public void logout() {

        DisplayUtil.printMessage("Logged out Successfully.");

    }

    /**
     * Finds Admin by Email.
     *
     * @param email Admin Email
     * @return Admin
     */
    private Optional<Admin> findAdminByEmail(String email) {

        return DataStore.ADMINS.values()
                .stream()
                .filter(admin ->
                        admin.getUserEmail()
                                .equalsIgnoreCase(email))
                .findFirst();

    }

}