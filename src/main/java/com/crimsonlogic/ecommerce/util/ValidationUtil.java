package com.crimsonlogic.ecommerce.util;
import com.crimsonlogic.ecommerce.exceptionhandling.ValidationException;
import com.crimsonlogic.ecommerce.model.Address;

import java.util.regex.Pattern;

/**
 * Utility class used for validating user inputs.
 * This class cannot be instantiated.
 */
public class ValidationUtil {
    private ValidationUtil() {
    }
    // Regular Expression Patterns
    // Name: Only alphabets and spaces.
    private static Pattern NAME_PATTERN =
            Pattern.compile("^(?=.{3,30}$)[A-Za-z]+(?:\\s[A-Za-z]+)*$");

    // Email Validation.
    private static Pattern EMAIL_PATTERN =
            Pattern.compile(
                    "^(?=.{6,254}$)(?!.*\\.\\.)([A-Za-z0-9]+(?:[._%+-][A-Za-z0-9]+)*)@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$");

    // Indian Mobile Number.
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");

    /**
     * Password Validation.
     * Rules:
     * First character must be uppercase.
     * Length : 8-20
     * One lowercase
     * One digit
     * One special character
     */
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "^[A-Z](?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&*!?_+=-])[A-Za-z\\d@#$%^&*!?_+=-]{7,19}$");

    // Shop Name Validation.

    private static final Pattern SHOP_PATTERN = Pattern.compile("^[A-Za-z0-9&'.,()\\- ]{3,60}$");

    // Indian ZIP Code.

    private static final Pattern ZIP_PATTERN = Pattern.compile("^[1-9][0-9]{5}$");

    // User Name Validation
    public static void validateUserName(String userName) throws ValidationException {

        if (userName == null || userName.trim().isEmpty()) {
            throw new ValidationException("User Name cannot be empty.");
        }
        if (!NAME_PATTERN.matcher(userName.trim()).matches()) {
            throw new ValidationException(
                    "User Name should contain only alphabets and spaces.");
        }
    }
    // Email Validation

    public static void validateEmail(String email) throws ValidationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty.");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Invalid Email Address.");
        }
    }
    // Phone Number Validation
    public static void validatePhone(String phone) throws ValidationException {

        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Phone Number cannot be empty.");
        }

        if (!PHONE_PATTERN.matcher(phone.trim()).matches()) {
            throw new ValidationException(
                    "Invalid Phone Number. It must contain 10 digits and start with 6-9.");
        }
    }

    // Password Validation
    public static void validatePassword(String password) throws ValidationException {

        if (password == null || password.isBlank()) {
            throw new ValidationException("Password cannot be empty.");
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new ValidationException(
                    "Password must start with a capital letter, contain 8-20 characters, at least one lowercase letter, one digit and one special character.");
        }
    }

    // Shop Name Validation
    public static void validateShopName(String shopName) throws ValidationException {
        if (shopName == null || shopName.trim().isEmpty()) {
            throw new ValidationException("Shop Name cannot be empty.");
        }

        if (!SHOP_PATTERN.matcher(shopName.trim()).matches()) {
            throw new ValidationException("Invalid Shop Name.");
        }

        if (containsThreeConsecutiveCharacters(shopName)) {
            throw new ValidationException(
                    "Shop Name should not contain three consecutive identical characters.");
        }
    }

    // Address Validation
    public static void validateAddress(Address address) throws ValidationException {
        if (address == null) {
            return;
        }
        validateField(address.getHouseNumber(), "House Number");
        validateField(address.getStreet(), "Street");
        validateField(address.getCity(), "City");
        validateField(address.getState(), "State");
        validateField(address.getCountry(), "Country");
        validateZipCode(address.getZipCode());
    }

    // ZIP Code Validation
    public static void validateZipCode(String zipCode) throws ValidationException {

        if (zipCode == null || zipCode.trim().isEmpty()) {
            throw new ValidationException("ZIP Code cannot be empty.");
        }

        if (!ZIP_PATTERN.matcher(zipCode.trim()).matches()) {
            throw new ValidationException("Invalid ZIP Code.");
        }
    }

    // Generic Field Validation
    private static void validateField(String value, String fieldName) throws ValidationException {

        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be empty.");
        }
    }

    /**
     * Validates Category Name.
     *
     * @param categoryName Category Name
     * @throws ValidationException if Category Name is invalid
     */
    public static void validateCategoryName(String categoryName) throws ValidationException {

        if (categoryName == null || categoryName.isBlank()) {
            throw new ValidationException("Category Name cannot be empty.");
        }

        if (!categoryName.matches("[A-Za-z ]{3,30}")) {
            throw new ValidationException(
                    "Category Name should contain only alphabets and spaces (3-30 characters).");
        }
    }

    public static void validateShopAddress(String shopAddress)
            throws ValidationException {

        if (shopAddress == null || shopAddress.trim().isEmpty()) {

            throw new ValidationException(
                    "Shop Address cannot be empty.");

        }

        if (shopAddress.length() < 5) {

            throw new ValidationException(
                    "Shop Address is too short.");

        }

        if (shopAddress.length() > 255) {

            throw new ValidationException(
                    "Shop Address cannot exceed 255 characters.");

        }

    }

    /**
     * Validates Seller Address.
     *
     * @param address Seller Address
     * @throws ValidationException if address is invalid
     */
    public static void validateSellerAddress(Address address)
            throws ValidationException {

        if (address == null) {

            throw new ValidationException(
                    "Address cannot be empty.");

        }

        validateField(
                address.getStreet(),
                "Street");

        validateField(
                address.getCity(),
                "City");

        validateField(
                address.getState(),
                "State");

        validateField(
                address.getCountry(),
                "Country");

        validateZipCode(
                address.getZipCode());

    }

    /**
     * Validates Category Description.
     *
     * @param description Category Description
     * @throws ValidationException if Description is invalid
     */
    public static void validateCategoryDescription(String description)
            throws ValidationException {

        if (description == null || description.isBlank()) {

            throw new ValidationException(
                    "Category Description cannot be empty.");

        }

        if (description.length() < 5 || description.length() > 100) {

            throw new ValidationException(
                    "Category Description should contain 5 to 100 characters.");

        }

    }

    /**
     * Validates Product Name.
     *
     * @param productName Product Name
     * @throws ValidationException if Product Name is invalid
     */
    public static void validateProductName(String productName)
            throws ValidationException {

        if (productName == null || productName.isBlank()) {

            throw new ValidationException(
                    "Product Name cannot be empty.");

        }

        if (productName.length() < 3
                || productName.length() > 50) {

            throw new ValidationException(
                    "Product Name should contain 3 to 50 characters.");

        }

    }

    /**
     * Validates Product Description.
     *
     * @param description Product Description
     * @throws ValidationException if Description is invalid
     */
    public static void validateProductDescription(
            String description)
            throws ValidationException {

        if (description == null
                || description.isBlank()) {

            throw new ValidationException(
                    "Product Description cannot be empty.");

        }

        if (description.length() < 10
                || description.length() > 200) {

            throw new ValidationException(
                    "Product Description should contain 10 to 200 characters.");

        }

    }

    /**
     * Validates Product Price.
     *
     * @param price Product Price
     * @throws ValidationException if Price is invalid
     */
    public static void validateProductPrice(double price)
            throws ValidationException {

        if (price <= 0) {

            throw new ValidationException(
                    "Product Price should be greater than 0.");

        }

    }
    /**
     * Validates Quantity.
     *
     * @param quantity Quantity
     * @throws ValidationException if invalid
     */
    public static void validateQuantity(int quantity)
            throws ValidationException {

        if (quantity < 0) {

            throw new ValidationException(
                    "Quantity cannot be negative.");

        }

    }
    // Helper Method
    /**
     * Checks whether a string contains
     * three consecutive identical characters.
     */
    private static boolean containsThreeConsecutiveCharacters(String value) {

        String text = value.replaceAll("\\s+", "").toLowerCase();
        for (int i = 0; i < text.length() - 2; i++) {
            char current = text.charAt(i);
            if (Character.isLetterOrDigit(current)
                    && current == text.charAt(i + 1)
                    && current == text.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }
}
