package com.borsa.apartment.controller;

import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.dto.MyListingsResponseDto;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.service.ApartmentListingService;
import com.borsa.apartment.service.EmailService;
import com.borsa.apartment.service.UserService;
import com.borsa.apartment.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.borsa.apartment.model.Email;

@RestController
@RequestMapping("/api/apartments")
@CrossOrigin(origins = "http://localhost:4200")
public class ApartmentListingController {

    @Autowired
    private ApartmentListingService apartmentListingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/lists")
    public List<ApartmentListing> getAllListings() {
        return apartmentListingService.getAllListings();
    }

    @PostMapping("/create-listing")
    public ResponseEntity<MessageResponseDto> createListing(@RequestBody ApartmentListing listing, @RequestHeader("Authorization") String header) {
        MessageResponseDto responseBody = new MessageResponseDto();
        String token = header.substring(7);
        try {
            User user = userService.findUserFromToken(token);
            apartmentListingService.saveListing(user, listing);
            responseBody.setMessage("Successfully added listing.");
            emailService.sendEmail(Email.listingCreatedEmail(user.getEmail()));
            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e) {
            responseBody.setMessage("Unable to add listing.");
            return ResponseEntity.status(500).body(responseBody);
        }
    }

    @GetMapping("/my-listings")
    public ResponseEntity<MyListingsResponseDto>  getMyListings(@RequestHeader("Authorization") String header) {
        MyListingsResponseDto responseBody = new MyListingsResponseDto();
        String token = header.substring(7);
        try {
            User user = userService.findUserFromToken(token);
            List<ApartmentListing> apartmentListings = userService.getApartmentListings(user);

            // No listing found
            if (apartmentListings.isEmpty()) {
                responseBody.setMessage("There is no listing registered under this user.");
                responseBody.setMessageSeverity("warn");
                responseBody.setListings(null);
                return ResponseEntity.status(204).body(responseBody);
            }

            // Listings found
            responseBody.setMessage("Found listings of " + user.getEmail());
            responseBody.setMessageSeverity("success");
            responseBody.setListings(apartmentListings);
            return ResponseEntity.ok().body(responseBody);
        }  catch (Exception e) {

            // Exception
            responseBody.setMessage("Error getting listings.");
            responseBody.setMessageSeverity("error");
            responseBody.setListings(null);
            return ResponseEntity.status(500).body(responseBody);
        }
    }

    @PostMapping("/my-listings/edit")
    public ResponseEntity<MessageResponseDto> editListing(@RequestBody ApartmentListing listing, @RequestHeader("Authorization") String header) {
        MessageResponseDto responseBody = new MessageResponseDto();
        String token = header.substring(7);
        try {
            User user = userService.findUserFromToken(token);
            listing.setUser(user);
            apartmentListingService.updateListing(listing);
            responseBody.setMessage("Successfully updated listing.");
            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e) {
            responseBody.setMessage("Unable to update listing.");
            return ResponseEntity.status(500).body(responseBody);
        }
    }

    @PostMapping("/my-listings/delete")
    public ResponseEntity<MessageResponseDto> deleteListing(@RequestBody ApartmentListing listing) {
        MessageResponseDto responseBody = new MessageResponseDto();
        try {
            apartmentListingService.deleteListing(listing.getId());
            responseBody.setMessage("Successfully deleted listing.");
            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e) {
            responseBody.setMessage("Unable to delete listing.");
            return ResponseEntity.status(500).body(responseBody);
        }
    }
}