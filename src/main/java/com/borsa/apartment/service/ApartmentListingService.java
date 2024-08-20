package com.borsa.apartment.service;

import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.model.User;
import com.borsa.apartment.repo.ApartmentListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentListingService {

    @Autowired
    private ApartmentListingRepository apartmentListingRepository;

    public List<ApartmentListing> getAllListings() {
        return apartmentListingRepository.findAll();
    }

    public void saveListing(User user, ApartmentListing listing) {
        listing.setUser(user);
        listing.setListerEmail(user.getEmail());
        apartmentListingRepository.save(listing);
    }

    public void updateListing(ApartmentListing listing) {
        apartmentListingRepository.save(listing);
    }

    public void deleteListing(Long id) {
        apartmentListingRepository.deleteById(id);
    }
}