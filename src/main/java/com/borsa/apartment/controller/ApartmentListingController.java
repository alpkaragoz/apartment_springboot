package com.borsa.apartment.controller;

import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.service.ApartmentListingService;
import com.borsa.apartment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/apartments")
@CrossOrigin(origins = "http://localhost:4200")
public class ApartmentListingController {

    @Autowired
    private ApartmentListingService apartmentListingService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<ApartmentListing> getAllListings() {
        return apartmentListingService.getAllListings();
    }
}