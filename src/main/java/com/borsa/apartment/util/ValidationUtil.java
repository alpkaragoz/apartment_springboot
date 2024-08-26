package com.borsa.apartment.util;

import com.borsa.apartment.exception.BadRequestException;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.model.User;

public class ValidationUtil {

    public static void validateRegister(User requestUser) {
        validateEmail(requestUser.getEmail());
        validatePassword(requestUser.getPassword());
    }

    public static void validateListing(ApartmentListing listing) {
        validateListingName(listing.getListingName());
        validateAddress(listing.getAddress());
        validateAge(listing.getAge());
        validateRoomNumber(listing.getRoomNumber());
        validatePrice(listing.getPrice());
        validateHomeSquareMeter(listing.getHomeSquareMeter());
        validateBathroomNumber(listing.getBathroomNumber());
    }

    // Method to validate email
    private static void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Email cannot be empty.");
        }
        if (!email.contains("@")) {
            throw new BadRequestException("Email must contain '@'.");
        }
    }

    // Method to validate password
    private static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new BadRequestException("Password cannot be empty.");
        }
        if (password.length() < 6) {
            throw new BadRequestException("Password must be at least 6 characters long.");
        }
    }

    private static void validateListingName(String listingName) {
        if (listingName == null || listingName.isEmpty()) {
            throw new BadRequestException("Listing name cannot be empty.");
        }
        if (listingName.length() < 3 || listingName.length() > 100) {
            throw new BadRequestException("Listing name must be between 3 and 100 characters.");
        }
    }

    private static void validateAddress(String address) {
        if (address == null || address.isEmpty()) {
            throw new BadRequestException("Address cannot be empty.");
        }
        if (address.length() > 200) {
            throw new BadRequestException("Address is too long.");
        }
    }

    private static void validateAge(int age) {
        if (age < 0) {
            throw new BadRequestException("Age cannot be negative.");
        }
        if (age > 200) {
            throw new BadRequestException("Age seems unrealistic.");
        }
    }

    private static void validateRoomNumber(int roomNumber) {
        if (roomNumber <= 0) {
            throw new BadRequestException("Room number must be greater than zero.");
        }
        if (roomNumber > 1000) {
            throw new BadRequestException("Room number is too high.");
        }
    }

    private static void validatePrice(double price) {
        if (price <= 0) {
            throw new BadRequestException("Price must be a positive number.");
        }
        if (price > 1_000_000_000) {
            throw new BadRequestException("Price seems unrealistic.");
        }
    }

    private static void validateHomeSquareMeter(double homeSquareMeter) {
        if (homeSquareMeter <= 0) {
            throw new BadRequestException("Home square meter must be a positive number.");
        }
        if (homeSquareMeter > 10_000) {
            throw new BadRequestException("Home square meter is too large.");
        }
    }

    private static void validateBathroomNumber(int bathroomNumber) {
        if (bathroomNumber < 0) {
            throw new BadRequestException("Bathroom number cannot be negative.");
        }
        if (bathroomNumber > 1000) {
            throw new BadRequestException("Bathroom number is too high.");
        }
    }
}
