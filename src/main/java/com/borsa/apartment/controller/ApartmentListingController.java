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

    @PostMapping("/my-listings/edit")
    public ResponseEntity<Map<String, Object>> editListing(@RequestBody ApartmentListing listing, @RequestHeader("Authorization") String header) {
        Map<String, Object> returnBody = new HashMap<>();
        String token = header.substring(7);
        try {
            User user = userService.findUserFromToken(token);
            listing.setUser(user);
            apartmentListingService.updateListing(listing);
            returnBody.put("message", "Successfully updated listing.");
            return ResponseEntity.ok().body(returnBody);
        } catch (Exception e) {
            returnBody.put("message", "Unable to update listing.");
            return ResponseEntity.status(500).body(returnBody);
        }
    }

    @PostMapping("/my-listings/delete")
    public ResponseEntity<Map<String, Object>> deleteListing(@RequestBody ApartmentListing listing) {
        Map<String, Object> returnBody = new HashMap<>();
        try {
            apartmentListingService.deleteListing(listing.getId());
            returnBody.put("message", "Successfully deleted listing.");
            return ResponseEntity.ok().body(returnBody);
        } catch (Exception e) {
            returnBody.put("message", "Unable to delete listing.");
            return ResponseEntity.status(500).body(returnBody);
        }
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
            if (apartmentListings.isEmpty()) {
                throw new NullPointerException();
            }
            returnBody.put("message", "Found listings of " + user.getEmail());
            returnBody.put("messageSeverity", "success");
            returnBody.put("listings", apartmentListings);
            return ResponseEntity.ok().body(returnBody);
        }  catch (NullPointerException e) {
            returnBody.put("message", "There is no listing registered under this user.");
            returnBody.put("messageSeverity", "warn");
            returnBody.put("listings", null);
            return ResponseEntity.status(500).body(returnBody);
        }  catch (Exception e) {
            returnBody.put("message", "Error getting listings.");
            returnBody.put("messageSeverity", "error");
            returnBody.put("listings", null);
            return ResponseEntity.status(500).body(returnBody);
        }
    }
}