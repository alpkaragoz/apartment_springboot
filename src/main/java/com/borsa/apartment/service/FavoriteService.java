package com.borsa.apartment.service;

import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.exception.FavoriteAlreadyExistsException;
import com.borsa.apartment.exception.ResourceNotFoundException;
import com.borsa.apartment.exception.UnauthorizedAccessException;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.model.Favorite;
import com.borsa.apartment.model.User;
import com.borsa.apartment.repo.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final ApartmentListingService apartmentListingService;
    private final JwtService jwtService;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, UserService userService, ApartmentListingService apartmentListingService, JwtService jwtService) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.apartmentListingService = apartmentListingService;
        this.jwtService = jwtService;
    }

    public List<Long> getLikedListingsByUser(Long userId, String header) {
        String token = header.substring(7);
        String extractedUserId = jwtService.extractId(token);

        // Insecure Direct Object Reference (IDOR) attack prevention.
        if(!extractedUserId.equals(userId.toString())) {
            throw new UnauthorizedAccessException("Unauthorized access attempt for the resource.");
        }
        return favoriteRepository.findAllByUserId(userId).stream()
                .map(favorite -> favorite.getListing().getId())
                .collect(Collectors.toList());
    }

    public long countLikesForListing(Long listingId) {
        return favoriteRepository.countByListingId(listingId);
    }

    public boolean hasUserLikedListing(Long userId, Long listingId) {
        return favoriteRepository.existsByUserIdAndListingId(userId, listingId);
    }

    public MessageResponseDto addFavorite(Long userId, Long listingId, String header) {
        String token = header.substring(7);
        String extractedUserId = jwtService.extractId(token);

        // Insecure Direct Object Reference (IDOR) attack prevention.
        if(!extractedUserId.equals(userId.toString())) {
            throw new UnauthorizedAccessException("Unauthorized access attempt for the resource.");
        }
        if (favoriteRepository.existsByUserIdAndListingId(userId, listingId)) {
            throw new FavoriteAlreadyExistsException("User already added this listing to favorites.");
        }
        User user = userService.getUser(userId);
        ApartmentListing listing = apartmentListingService.getListing(listingId);

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setListing(listing);
        favoriteRepository.save(favorite);

        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Successfully favorited listing");
        return responseDto;
    }

    public MessageResponseDto removeFavorite(Long userId, Long listingId, String header) {
        String token = header.substring(7);
        String extractedUserId = jwtService.extractId(token);

        // Insecure Direct Object Reference (IDOR) attack prevention.
        if(!extractedUserId.equals(userId.toString())) {
            throw new UnauthorizedAccessException("Unauthorized access attempt for the resource.");
        }
        Favorite favorite = favoriteRepository.findByUserIdAndListingId(userId, listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found"));
        favoriteRepository.delete(favorite);

        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Successfully deleted listing");
        return responseDto;
    }
}
