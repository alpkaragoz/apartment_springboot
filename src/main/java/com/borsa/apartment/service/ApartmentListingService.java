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

    public ApartmentListing saveListing(User user, ApartmentListing listing) {
        listing.setUser(user);
        return apartmentListingRepository.save(listing);
    }

    public ApartmentListing getListingById(Long id) {
        return apartmentListingRepository.findById(id).orElse(null);
    }

//    public ApartmentListing getListingsByEmail(String email) {
//
//    }

    public ApartmentListing updateListing(Long id, ApartmentListing listing) {
        listing.setId(id);
        return apartmentListingRepository.save(listing);
    }

    public void deleteListing(Long id) {
        apartmentListingRepository.deleteById(id);
    }
}