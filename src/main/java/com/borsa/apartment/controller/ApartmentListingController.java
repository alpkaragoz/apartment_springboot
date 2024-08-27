package com.borsa.apartment.controller;

import com.borsa.apartment.dto.FilteredListingsDto;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.service.ApartmentListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.logging.Filter;

@RestController
@RequestMapping("/api/apartments")
@CrossOrigin(origins = "http://localhost:4200")
public class ApartmentListingController {

    private final ApartmentListingService apartmentListingService;

    @Autowired
    public ApartmentListingController (ApartmentListingService apartmentListingService) {
        this.apartmentListingService = apartmentListingService;
    }

    @GetMapping()
    public List<ApartmentListing> getAllListings() {
        return apartmentListingService.getAllListings();
    }

    @GetMapping("/filter")
    public ResponseEntity<FilteredListingsDto> getFilteredApartmentListings(
            @RequestParam(required = false, defaultValue = "") ApartmentListing.RentSaleEnum rentSale,
            @RequestParam(required = false, defaultValue = "0") double minPrice,
            @RequestParam(required = false, defaultValue = "1.7976931348623157E308") double maxPrice,
            @RequestParam(required = false, defaultValue = "") String address,
            @RequestParam(required = false, defaultValue = "") String listingName,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(apartmentListingService.getFilteredApartmentListings(
                rentSale, minPrice, maxPrice, address, listingName, pageable
        ));
    }
}