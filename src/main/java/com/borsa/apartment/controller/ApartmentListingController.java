package com.borsa.apartment.controller;

import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.service.ApartmentListingService;
import com.borsa.apartment.service.UserService;
import com.borsa.apartment.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/apartments")
@CrossOrigin(origins = "http://localhost:4200")
public class ApartmentListingController {

    @Autowired
    private ApartmentListingService apartmentListingService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<ApartmentListing> getAllListings() {
        return apartmentListingService.getAllListings();
    }

    @PostMapping("/create-listing")
    public ResponseEntity<Map<String, Object>> createListing(@RequestBody ApartmentListing listing, @RequestHeader("Authorization") String header) {
        Map<String, Object> returnBody = new HashMap<>();
        String token = header.substring(7);
        try {
            User user = userService.findUserFromToken(token);
            apartmentListingService.saveListing(user, listing);
            returnBody.put("message", "Successfully added listing.");
            return ResponseEntity.ok().body(returnBody);
        } catch (Exception e) {
            returnBody.put("message", "Unable to add listing.");
            return ResponseEntity.status(500).body(returnBody);
        }
    }

    @GetMapping("/my-listings")
    public ResponseEntity<Map<String, Object>>  getMyListings(@RequestHeader("Authorization") String header) {
        Map<String, Object> returnBody = new HashMap<>();
        String token = header.substring(7);
        try {
            User user = userService.findUserFromToken(token);
            List<ApartmentListing> apartmentListings = userService.getApartmentListings(user);
            returnBody.put("message", "Found listings of " + user.getEmail());
            returnBody.put("listings", apartmentListings);
            return ResponseEntity.ok().body(returnBody);
        } catch (Exception e) {
            returnBody.put("message", "Error getting listings.");
            returnBody.put("listings", null);
            return ResponseEntity.status(500).body(returnBody);
        }
    }
}