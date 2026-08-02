package com.crimsonlogic.ecommerce.service;

import com.crimsonlogic.ecommerce.exceptionhandling.user.UserNotFoundException;
import com.crimsonlogic.ecommerce.exceptionhandling.user.ValidationException;
import com.crimsonlogic.ecommerce.model.Seller;
import com.crimsonlogic.ecommerce.repository.DataStore;
import com.crimsonlogic.ecommerce.service.abstraction.UserService;
import com.crimsonlogic.ecommerce.util.DisplayUtil;
import com.crimsonlogic.ecommerce.util.InputUtil;
import com.crimsonlogic.ecommerce.util.ValidationUtil;

/**
 * Service class responsible for Seller operations.
 */
public class SellerService extends UserService<Seller> {

    /**
     * Updates Seller Profile.
     *
     * @param seller Logged-in Seller
     */
    @Override
    public void updateProfile(Seller seller) {

        // Update common user details
        super.updateProfile(seller);

        System.out.println("\n========== UPDATE SHOP DETAILS ==========");

        while (true) {

            try {

                String shopName = InputUtil.readOptionalString(
                        "Enter Shop Name (Press Enter to Skip): ");

                if (shopName != null) {

                    ValidationUtil.validateShopName(shopName);
                    seller.setShopName(shopName);

                }

                break;

            } catch (ValidationException exception) {

                DisplayUtil.printError(exception.getMessage());

            }

        }

        String shopAddress = InputUtil.readOptionalString(
                "Enter Shop Address (Press Enter to Skip): ");

        if (shopAddress != null) {

            seller.setShopAddress(shopAddress);

        }

        DisplayUtil.printSuccess(
                "Seller Profile Updated Successfully.");

    }

    /**
     * Deletes Seller Account.
     *
     * @param seller Logged-in Seller
     * @return true if deleted successfully, otherwise false
     */
    public boolean deleteAccount(Seller seller) {

        try {

            Seller deletedSeller =
                    DataStore.SELLERS.remove(seller.getUserId());

            if (deletedSeller == null) {

                throw new UserNotFoundException(
                        "Seller Account Not Found.");

            }

            return true;

        } catch (UserNotFoundException exception) {

            DisplayUtil.printMessage(exception.getMessage());

            return false;

        }

    }

}