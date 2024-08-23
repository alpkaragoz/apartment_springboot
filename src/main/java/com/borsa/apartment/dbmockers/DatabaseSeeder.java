package com.borsa.apartment.dbmockers;

import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.repo.ApartmentListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ApartmentListingRepository apartmentListingRepository;
    private final DataGenerator dataGenerator;

    @Autowired
    public DatabaseSeeder(ApartmentListingRepository apartmentListingRepository, DataGenerator dataGenerator) {
        this.apartmentListingRepository = apartmentListingRepository;
        this.dataGenerator = dataGenerator;
    }

    @Override
    public void run(String... args) {
        List<ApartmentListing> listings = dataGenerator.generateListings(0); // Change number to populate db
        apartmentListingRepository.saveAll(listings);
    }
}
