package com.borsa.apartment.controller;

import com.borsa.apartment.dto.UserListingsResponseDto;
import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.model.User;
import com.borsa.apartment.service.ApartmentListingService;
import com.borsa.apartment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final ApartmentListingService apartmentListingService;

    @Autowired
    public UserController(ApartmentListingService apartmentListingService, UserService userService) {
        this.apartmentListingService = apartmentListingService;
        this.userService = userService;
    }

    @Operation(summary = "Register a new user", description = "Create a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully."),
            @ApiResponse(responseCode = "409", description = "User already exists.", content = @Content)
    })    @PostMapping()
    public ResponseEntity<MessageResponseDto> registerUser(@RequestBody User requestUser) {
        return ResponseEntity.ok().body(userService.saveUser(requestUser));
    }

    @Operation(summary = "Create a new apartment listing", description = "Add a new listing under the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listing created successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized access attempt for the resource.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error saving the listing.", content = @Content)
    })    @PostMapping("/{userId}/apartments")
    public ResponseEntity<MessageResponseDto> createListing(@PathVariable String userId, @RequestBody ApartmentListing listing, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(apartmentListingService.createListing(userId, listing, header));
    }

    @Operation(summary = "Get user's apartment listings", description = "Retrieve all apartment listings for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved listings."),
            @ApiResponse(responseCode = "401", description = "Unauthorized access attempt for the resource.", content = @Content),
            @ApiResponse(responseCode = "404", description = "No listings found.", content = @Content)
    })    @GetMapping("/{userId}/apartments")
    public ResponseEntity<UserListingsResponseDto> returnUserListings(@PathVariable String userId, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(apartmentListingService.getUserApartmentListings(userId, header));
    }

    @Operation(summary = "Delete a user's apartment listing", description = "Remove a specific listing for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listing deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized access attempt for the resource.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error deleting the listing.", content = @Content)
    })    @DeleteMapping("/{userId}/apartments/{apartmentId}")
    public ResponseEntity<MessageResponseDto> deleteListing(@PathVariable String userId, @PathVariable Long apartmentId, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(apartmentListingService.deleteListing(userId ,apartmentId, header));
    }

    @Operation(summary = "Edit a user's apartment listing", description = "Update an existing apartment listing for the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listing updated successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized access attempt for the resource.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error updating the listing.", content = @Content)
    })    @PutMapping("/{userId}/apartments/{apartmentId}")
    public ResponseEntity<MessageResponseDto> editListing(@PathVariable String userId, @RequestBody ApartmentListing listing, @RequestHeader("Authorization") String header, @PathVariable Long apartmentId) {
        return ResponseEntity.ok().body(apartmentListingService.updateListing(userId, listing, header));
    }
}