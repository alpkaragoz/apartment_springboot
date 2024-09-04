package com.borsa.apartment.dto;

import com.borsa.apartment.model.ApartmentListing;

public class ListingWithLikesDto {
    private ApartmentListing listing;
    private long likes;

    public ListingWithLikesDto(ApartmentListing listing, long likes) {
        this.listing = listing;
        this.likes = likes;
    }

    public ApartmentListing getListing() {
        return listing;
    }

    public void setListing(ApartmentListing listing) {
        this.listing = listing;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }
}
