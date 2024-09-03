package com.borsa.apartment.controller;

import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "http://localhost:4200")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping()
    public ResponseEntity<MessageResponseDto> createFavorite(@RequestParam(required = true) long userId, @RequestParam(required = true) long listingId, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(favoriteService.addFavorite(userId, listingId, header));
    }

    @DeleteMapping()
    public ResponseEntity<MessageResponseDto> deleteFavorite(@RequestParam(required = true) long userId, @RequestParam(required = true) long listingId, @RequestHeader("Authorization") String header) {
        return ResponseEntity.ok().body(favoriteService.removeFavorite(userId, listingId, header));
    }
}
