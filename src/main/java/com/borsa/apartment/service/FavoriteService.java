package com.borsa.apartment.service;

import com.borsa.apartment.dto.ListingWithLikesDto;
import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.exception.FavoriteAlreadyExistsException;
import com.borsa.apartment.exception.ListingNotFoundException;
import com.borsa.apartment.exception.ResourceNotFoundException;
import com.borsa.apartment.exception.UnauthorizedAccessException;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.model.Favorite;
import com.borsa.apartment.model.User;
import com.borsa.apartment.repo.ApartmentListingRepository;
import com.borsa.apartment.repo.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final ApartmentListingRepository apartmentListingRepository;
    private final WebSocketNotificationService webSocketNotificationService;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, UserService userService, JwtService jwtService, ApartmentListingRepository apartmentListingRepository, WebSocketNotificationService webSocketNotificationService) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
        this.jwtService = jwtService;
        this.apartmentListingRepository = apartmentListingRepository;
        this.webSocketNotificationService = webSocketNotificationService;
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
        ApartmentListing listing = apartmentListingRepository.findById(listingId).orElseThrow(() -> new ListingNotFoundException("Listing not found."));

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setListing(listing);
        favoriteRepository.save(favorite);

        webSocketNotificationService.notifyFavoriteChange(userId);

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
        webSocketNotificationService.notifyFavoriteChange(userId);

        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Successfully deleted listing");
        return responseDto;
    }
}
