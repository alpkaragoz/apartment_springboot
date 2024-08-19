package com.borsa.apartment.dbmockers;

import com.borsa.apartment.model.ApartmentListing;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataGenerator {

    private final Faker faker = new Faker();

    public List<ApartmentListing> generateListings(int count) {
        List<ApartmentListing> listings = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            ApartmentListing listing = new ApartmentListing();
            listing.setListingName(faker.company().name() + " " + faker.lorem().word());
            listing.setAddress(faker.address().fullAddress());
            listing.setAge(faker.number().numberBetween(0, 100));
            listing.setRoomNumber(faker.number().numberBetween(1, 5));
            listing.setPrice(faker.number().numberBetween(50000, 500000));
            listing.setRentSale(ApartmentListing.RentSaleEnum.valueOf(faker.bool().bool() ? "RENT" : "SALE"));
            listing.setHasFurniture(faker.bool().bool());
            listing.setHasBalcony(faker.bool().bool());
            listing.setBathroomNumber(faker.number().numberBetween(1, 3));
            listing.setHomeSquareMeter(faker.number().numberBetween(30, 200));
            listing.setListerEmail(faker.internet().emailAddress());
            listings.add(listing);
        }

        return listings;
    }
}

