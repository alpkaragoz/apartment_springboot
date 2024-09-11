package com.borsa.apartment.controller;

import com.borsa.apartment.dto.ListingWithLikesDto;
import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.service.ApartmentListingService;
import com.borsa.apartment.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "http://localhost:4200")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final ApartmentListingService apartmentListingService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, ApartmentListingService apartmentListingService) {
        this.favoriteService = favoriteService;
        this.apartmentListingService = apartmentListingService;
    }

    @PostMapping()
    public ResponseEntity<MessageResponseDto> createFavorite(@RequestParam(required = true) Long userId, @RequestParam(required = true) Long listingId, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(favoriteService.addFavorite(userId, listingId, header));
    }

    @GetMapping("/listings")
    public ResponseEntity<List<ListingWithLikesDto>> getListingsWithFavoriteCounts(@RequestParam(required = true) long userId, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(apartmentListingService.getListingsWithFavoriteCounts(userId, header));
    }

    @DeleteMapping()
    public ResponseEntity<MessageResponseDto> deleteFavorite(@RequestParam(required = true) long userId, @RequestParam(required = true) long listingId, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(favoriteService.removeFavorite(userId, listingId, header));
    }
}
