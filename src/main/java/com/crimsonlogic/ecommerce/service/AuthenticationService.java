package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.AddressDAO;
import com.crimsonlogic.ecommerce.dao.AdminDAO;
import com.crimsonlogic.ecommerce.dao.CustomerDAO;
import com.crimsonlogic.ecommerce.dao.SellerDAO;
import com.crimsonlogic.ecommerce.exceptionhandling.DuplicateUserException;
import com.crimsonlogic.ecommerce.exceptionhandling.InvalidCredentialsException;
import com.crimsonlogic.ecommerce.exceptionhandling.ValidationException;
import com.crimsonlogic.ecommerce.model.Address;
import com.crimsonlogic.ecommerce.model.Admin;
import com.crimsonlogic.ecommerce.model.Customer;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.util.*;

/**
 * Handles registration, login and logout operations
 * for all users of the application.
 */
public class AuthenticationService {

    /**
     * Address DAO.
     */
    private final AddressDAO addressDAO = new AddressDAO();

    /**
     * Admin DAO.
     */
    private final AdminDAO adminDAO = new AdminDAO();

    /**
     * Customer DAO.
     */
    private final CustomerDAO customerDAO = new CustomerDAO();

    /**
     * Seller DAO.
     */
    private final SellerDAO sellerDAO = new SellerDAO();

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

        String password =
                PasswordUtil.encryptPassword(
                        readValidPassword());

        String shopName = readValidShopName();

        String shopAddress = readValidShopAddress();

        Address address = createSellerAddress();

        addressDAO.insertAddress(address);

        Seller seller = new Seller(

                IdGenerator.generateId("SEL"),

                name,

                email,

                phone,

                password,

                address,

                shopName,

                shopAddress

        );

        sellerDAO.insertSeller(seller);

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

                        IdGenerator.generateId("ADDR"),

                        houseNumber,

                        street,

                        city,

                        state,

                        country,

                        zipCode

                );

                ValidationUtil.validateAddress(address);

                return address;

            } catch (ValidationException exception) {

                System.out.println(exception.getMessage());

            }

        }

    }

    /**
     * Reads Valid User Name.
     *
     * @return User Name
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
     * Reads Valid Email.
     *
     * @return Email
     */
    private String readValidEmail() {

        while (true) {

            try {

                String email =
                        InputUtil.readString("Enter Email: ");

                ValidationUtil.validateEmail(email);

                if (isEmailExists(email)) {

                    throw new DuplicateUserException(
                            "Email already registered.");

                }

                return email;

            } catch (ValidationException | DuplicateUserException exception) {

                System.out.println(exception.getMessage()
                        + " Example : abc@domain.com");

            }

        }

    }

    /**
     * Reads Valid Phone Number.
     *
     * @return Phone Number
     */
    private String readValidPhone() {

        while (true) {

            try {

                String phone =
                        InputUtil.readString("Enter Phone Number: ");

                ValidationUtil.validatePhone(phone);

                if (isPhoneExists(phone)) {

                    throw new DuplicateUserException(
                            "Phone Number already registered.");

                }

                return phone;

            } catch (ValidationException |
                     DuplicateUserException exception) {

                System.out.println(exception.getMessage());

            }

        }

    }

    /**
     * Reads Valid Password.
     *
     * @return Password
     */
    private String readValidPassword() {

        while (true) {

            try {

                String password =
                        InputUtil.readString("Enter Password: ");

                ValidationUtil.validatePassword(password);

                return password;

            } catch (ValidationException exception) {

                System.out.println(exception.getMessage());

            }

        }

    }

    /**
     * Reads Valid Shop Name.
     *
     * @return Shop Name
     */
    private String readValidShopName() {

        while (true) {

            try {

                String shopName =
                        InputUtil.readString(
                                "Enter Shop Name: ");

                ValidationUtil.validateShopName(
                        shopName);

                return shopName;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

            }

        }

    }

    /**
     * Reads Valid Shop Address.
     *
     * @return Shop Address
     */
    private String readValidShopAddress() {

        while (true) {

            try {

                String shopAddress =
                        InputUtil.readString(
                                "Enter Shop Address: ");

                ValidationUtil.validateShopAddress(
                        shopAddress);

                return shopAddress;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

            }

        }

    }
    /**
     * Creates Seller Address.
     * Seller is not asked for House Number.
     *
     * @return Address
     */
    private Address createSellerAddress() {

        while (true) {

            try {

                String street =
                        InputUtil.readString(
                                "Enter Street: ");

                String city =
                        InputUtil.readString(
                                "Enter City: ");

                String state =
                        InputUtil.readString(
                                "Enter State: ");

                String country =
                        InputUtil.readString(
                                "Enter Country: ");

                String zipCode =
                        InputUtil.readString(
                                "Enter Zip Code: ");

                Address address = new Address(

                        IdGenerator.generateId("ADDR"),

                        "",

                        street,

                        city,

                        state,

                        country,

                        zipCode

                );

                ValidationUtil.validateSellerAddress(
                        address);

                return address;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

            }

        }

    }

    /**
     * Checks whether Email already exists.
     *
     * @param email Email
     * @return true if exists
     */
    private boolean isEmailExists(String email) {

        return adminDAO.findAdminByEmail(email) != null
                || sellerDAO.findSellerByEmail(email) != null
                || customerDAO.findCustomerByEmail(email) != null;

    }

    /**
     * Checks whether Phone Number already exists.
     *
     * @param phone Phone Number
     * @return true if exists
     */
    private boolean isPhoneExists(String phone) {

        return adminDAO.findAdminByPhone(phone) != null
                || sellerDAO.findSellerByPhone(phone) != null
                || customerDAO.findCustomerByPhone(phone) != null;

    }

    /**
     * Registers Customer.
     *
     * @return Customer
     */
    public Customer registerCustomer() {

        DisplayUtil.printMessage("Enter Customer Details");

        String name = readValidUserName();

        String email = readValidEmail();

        String phone = readValidPhone();

        String password = PasswordUtil.encryptPassword(
                readValidPassword());

        Address address = createAddress();

        if (address != null) {

            addressDAO.insertAddress(address);

        }

        double walletBalance;

        while (true) {

            try {

                walletBalance =
                        InputUtil.readDouble(
                                "Enter Initial Wallet Balance : ");

                if (walletBalance < 0) {

                    throw new ValidationException(
                            "Wallet Balance Cannot Be Negative.");

                }

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(exception.getMessage());

            }

        }

        Customer customer = new Customer(

                IdGenerator.generateId("CUS"),

                name,

                email,

                phone,

                password,

                address,

                walletBalance

        );

        customerDAO.insertCustomer(customer);

        DisplayUtil.printSuccess("Registered Successfully.");

        System.out.println("Customer ID : "
                + customer.getUserId());

        return customer;

    }
    /**
     * Authenticates Admin.
     *
     * @return Logged-in Admin
     */
    public Admin loginAdmin() {

        try {

            DisplayUtil.printMessage(
                    "Enter Admin Credentials");

            String email =
                    InputUtil.readString(
                            "Enter Email    : ");

            String password =
                    InputUtil.readString(
                            "Enter Password : ");

            Admin admin =
                    adminDAO.findAdminByEmail(email);

            if (admin == null) {

                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");

            }
            if (!PasswordUtil.verifyPassword(
                    password,
                    admin.getUserPassword())) {

                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");

            }

            DisplayUtil.printMessage(
                    "LOGIN SUCCESSFUL");

            return admin;

        } catch (InvalidCredentialsException exception) {

            DisplayUtil.printMessage(
                    exception.getMessage());

            return null;

        }

    }

    /**
     * Authenticates Seller.
     *
     * @return Logged-in Seller
     */
    public Seller loginSeller()     {

        try {

            DisplayUtil.printMessage(
                    "Enter Seller Credentials");

            String email =
                    InputUtil.readString(
                            "Enter Email    : ");

            String password =
                    InputUtil.readString(
                            "Enter Password : ");

            Seller seller =
                    sellerDAO.findSellerByEmail(email);

            if (seller == null) {

                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");

            }
            if (!PasswordUtil.verifyPassword(
                    password,
                    seller.getUserPassword())) {

                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");

            }

            DisplayUtil.printMessage(
                    "LOGIN SUCCESSFUL");

            return seller;

        } catch (InvalidCredentialsException exception) {

            DisplayUtil.printMessage(
                    exception.getMessage());

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

            DisplayUtil.printMessage(
                    "Enter Customer Credentials");

            String email =
                    InputUtil.readString(
                            "Enter Email    : ");

            String password =
                    InputUtil.readString(
                            "Enter Password : ");

            Customer customer =
                    customerDAO.findCustomerByEmail(email);

            if (customer == null) {

                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");

            }

            if (!PasswordUtil.verifyPassword(
                    password,
                    customer.getUserPassword())) {

                throw new InvalidCredentialsException(
                        "Invalid Email or Password.");

            }

            DisplayUtil.printMessage(
                    "LOGIN SUCCESSFUL");

            return customer;

        } catch (InvalidCredentialsException exception) {

            DisplayUtil.printMessage(
                    exception.getMessage());

            return null;

        }

    }

    /**
     * Logs out current user.
     */
    public void logout() {

        DisplayUtil.printMessage(
                "Logged out Successfully.");

    }

}