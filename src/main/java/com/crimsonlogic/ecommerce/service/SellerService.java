package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.dao.SellerDAO;
import com.crimsonlogic.ecommerce.exceptionhandling.UserNotFoundException;
import com.crimsonlogic.ecommerce.exceptionhandling.ValidationException;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.service.abstraction.UserService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

/**
 * Service class responsible for Seller operations.
 */
public class SellerService extends UserService<Seller> {

    private final SellerDAO sellerDAO = new SellerDAO();

    /**
     * Updates Seller Profile.
     *
     * @param seller Logged-in Seller
     */
    @Override
    public void updateProfile(
            Seller seller) {

        // Update common user details
        super.updateProfile(seller);

        while (true) {

            try {

                String shopName =
                        InputUtil.readOptionalString(
                                "Enter Shop Name: ");

                if (shopName != null) {

                    ValidationUtil.validateShopName(
                            shopName);

                    seller.setShopName(
                            shopName);

                }

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printMessage(
                        exception.getMessage());

            }

        }

        String shopAddress =
                InputUtil.readOptionalString(
                        "Enter Shop Address (Press Enter to Skip): ");

        if (shopAddress != null) {

            seller.setShopAddress(
                    shopAddress);

        }

        sellerDAO.updateSeller(
                seller);

        DisplayUtil.printSuccess(
                "Seller Profile Updated Successfully.");

    }

    public String changePassword(Seller seller) {

        String encryptedPassword =
                super.changePassword(seller);

        // Password change was cancelled/failed
        if (encryptedPassword == null) {
            return null;
        }

        sellerDAO.updatePassword(
                seller.getUserId(),
                encryptedPassword);

        DisplayUtil.printSuccess(
                "Password Changed Successfully.");

        return encryptedPassword;
    }


    public boolean deleteAccount(
            Seller seller) {

        try {

            if (sellerDAO.findSellerById(
                    seller.getUserId()) == null) {

                throw new UserNotFoundException(
                        "Seller Account Not Found.");

            }

            sellerDAO.deleteSeller(
                    seller.getUserId());

            return true;

        } catch (UserNotFoundException exception) {

            DisplayUtil.printMessage(
                    exception.getMessage());

            return false;

        }

    }

}