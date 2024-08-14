package com.borsa.apartment.dto;

import com.borsa.apartment.model.ApartmentListing;

import java.util.List;

public class MyListingsResponseDto {
    private List<ApartmentListing> listings;
    private String message;
    private String messageSeverity;

    public MyListingsResponseDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageSeverity() {
        return messageSeverity;
    }

    public void setMessageSeverity(String messageSeverity) {
        this.messageSeverity = messageSeverity;
    }

    public List<ApartmentListing> getListings() {
        return listings;
    }

    public void setListings(List<ApartmentListing> listings) {
        this.listings = listings;
    }
}
