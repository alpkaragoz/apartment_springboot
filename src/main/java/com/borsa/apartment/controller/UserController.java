package com.borsa.apartment.controller;

import com.borsa.apartment.dto.UserListingsResponseDto;
import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.model.User;
import com.borsa.apartment.service.ApartmentListingService;
import com.borsa.apartment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApartmentListingService apartmentListingService;

    @PostMapping()
    public ResponseEntity<MessageResponseDto> registerUser(@RequestBody User requestUser) {
        return ResponseEntity.ok().body(userService.saveUser(requestUser));
    }

    @PostMapping("/{userId}/apartments")
    public ResponseEntity<MessageResponseDto> createListing(@PathVariable String userId, @RequestBody ApartmentListing listing, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(apartmentListingService.createListing(userId, listing, header));
    }

    @GetMapping("/{userId}/apartments")
    public ResponseEntity<UserListingsResponseDto> returnUserListings(@PathVariable String userId, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(apartmentListingService.getUserApartmentListings(userId, header));
    }

    @DeleteMapping("/{userId}/apartments/{apartmentId}")
    public ResponseEntity<MessageResponseDto> deleteListing(@PathVariable String userId, @PathVariable Long apartmentId, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(apartmentListingService.deleteListing(userId ,apartmentId, header));
    }

    @PutMapping("/{userId}/apartments/{apartmentId}")
    public ResponseEntity<MessageResponseDto> editListing(@PathVariable String userId, @RequestBody ApartmentListing listing, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(apartmentListingService.updateListing(userId, listing, header));
    }
}