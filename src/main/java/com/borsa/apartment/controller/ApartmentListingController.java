package com.borsa.apartment.controller;

import com.borsa.apartment.dto.FilteredListingsDto;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.service.ApartmentListingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/apartments")
@CrossOrigin(origins = "http://localhost:4200")
public class ApartmentListingController {

    private final ApartmentListingService apartmentListingService;

    @Autowired
    public ApartmentListingController (ApartmentListingService apartmentListingService) {
        this.apartmentListingService = apartmentListingService;
    }

    @Operation(summary = "Get filtered apartment listings", description = "Retrieve a paginated list of apartment listings filtered by rent/sale type, price range, address, and listing name.")
    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved listings"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access attempt for the resource.", content = @Content),
            @ApiResponse(responseCode = "404", description = "No listing found.", content = @Content),
    })
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