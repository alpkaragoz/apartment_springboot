package com.borsa.apartment.dto;

import com.borsa.apartment.model.ApartmentListing;

import java.util.List;

public class UserListingsResponseDto {
    private List<ApartmentListing> listings;
    private String message;

    public UserListingsResponseDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ApartmentListing> getListings() {
        return listings;
    }

    public void setListings(List<ApartmentListing> listings) {
        this.listings = listings;
    }
}
