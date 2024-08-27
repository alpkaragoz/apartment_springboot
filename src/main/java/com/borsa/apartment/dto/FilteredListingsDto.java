package com.borsa.apartment.dto;

import com.borsa.apartment.model.ApartmentListing;
import java.util.List;

public class FilteredListingsDto {
    private List<ApartmentListing> listings;
    private int totalPages;

    public FilteredListingsDto(List<ApartmentListing> listings, int totalPages) {
        this.listings = listings;
        this.totalPages = totalPages;
    }

    public List<ApartmentListing> getListings() {
        return listings;
    }

    public void setListings(List<ApartmentListing> listings) {
        this.listings = listings;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
