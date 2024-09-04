package com.borsa.apartment.service;

import com.borsa.apartment.dto.FilteredListingsDto;
import com.borsa.apartment.dto.ListingWithLikesDto;
import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.dto.UserListingsResponseDto;
import com.borsa.apartment.exception.DatabaseException;
import com.borsa.apartment.exception.ListingNotFoundException;
import com.borsa.apartment.exception.RestExceptionHandler;
import com.borsa.apartment.exception.UnauthorizedAccessException;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.model.Email;
import com.borsa.apartment.model.User;
import com.borsa.apartment.repo.ApartmentListingRepository;
import com.borsa.apartment.util.ApartmentListingSpecUtil;
import com.borsa.apartment.util.ValidationUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApartmentListingService {

    private final ApartmentListingRepository apartmentListingRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final FavoriteService favoriteService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Autowired
    public ApartmentListingService(ApartmentListingRepository apartmentListingRepository, UserService userService, EmailService emailService, JwtService jwtService, FavoriteService favoriteService) {
        this.apartmentListingRepository = apartmentListingRepository;
        this.userService = userService;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.favoriteService = favoriteService;
    }

    public ApartmentListing getListing(long id) {
        return apartmentListingRepository.findById(id).orElseThrow(() -> new ListingNotFoundException("Listing not found."));
    }

    public List<ApartmentListing> getAllListings() {
        return apartmentListingRepository.findAll();
    }

    public MessageResponseDto createListing(String userId, ApartmentListing listing, String header) {
        String token = header.substring(7);
        String extractedUserId = jwtService.extractId(token);

        // Insecure Direct Object Reference (IDOR) attack prevention.
        if(!extractedUserId.equals(userId)) {
            throw new UnauthorizedAccessException("Unauthorized access attempt for the resource.");
        }
        ValidationUtil.validateListing(listing);
        MessageResponseDto responseBody = new MessageResponseDto();
        User user = userService.getUser(Long.valueOf(userId));
        listing.setUser(user);
        listing.setListerEmail(user.getEmail());
        try {
            apartmentListingRepository.save(listing);
        } catch (Exception e) {
            LOGGER.error("Error saving listing with id {} by user {}", listing.getId(), userId);
            throw new DatabaseException("Database related error occurred while saving listing.");
        }
        responseBody.setMessage("Successfully added listing.");
        emailService.sendEmail(Email.listingCreatedEmail(user.getEmail()));
        return responseBody;
    }

    public UserListingsResponseDto getUserApartmentListings(String userId, String header) {
        String token = header.substring(7);
        String extractedUserId = jwtService.extractId(token);

        // Insecure Direct Object Reference (IDOR) attack prevention.
        if(!extractedUserId.equals(userId)) {
            throw new UnauthorizedAccessException("Unauthorized access attempt for the resource.");
        }
        UserListingsResponseDto responseDto = new UserListingsResponseDto();
        List<ApartmentListing> listings = apartmentListingRepository.findByUserId(Long.valueOf(userId));
        if (listings.isEmpty()) throw new ListingNotFoundException("No listing found.");
        responseDto.setListings(listings);
        responseDto.setMessage("Successfully found listings.");
        return responseDto;
    }

    public MessageResponseDto updateListing(String userId, ApartmentListing listing, String header) {
        String token = header.substring(7);
        String extractedUserId = jwtService.extractId(token);

        // Insecure Direct Object Reference (IDOR) attack prevention.
        if(!extractedUserId.equals(userId)) {
            throw new UnauthorizedAccessException("Unauthorized access attempt for the resource.");
        }
        MessageResponseDto responseBody = new MessageResponseDto();
        User user = userService.getUser(Long.valueOf(userId));
        listing.setUser(user);
        try {
            apartmentListingRepository.save(listing);
        } catch (Exception e) {
            LOGGER.error("Error updating listing with id {} by user {}", listing.getId(), userId);
            throw new DatabaseException("Database related error occurred while updating listing.");
        }
        responseBody.setMessage("Successfully updated listing.");
        return responseBody;
    }

    @Transactional
    public MessageResponseDto deleteListing(String  userId, Long apartmentId, String header) {
        String token = header.substring(7);
        String extractedUserId = jwtService.extractId(token);

        // Insecure Direct Object Reference (IDOR) attack prevention.
        if(!extractedUserId.equals(userId)) {
            throw new UnauthorizedAccessException("Unauthorized access attempt for the resource.");
        }
        try {
            apartmentListingRepository.deleteById(apartmentId);
        } catch (Exception e) {
            LOGGER.error("Error deleting listing with id {} by user {}", apartmentId, userId);
            throw new DatabaseException("Database related error occurred while deleting listing.");
        }
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessage("Successfully deleted listing.");
        return responseDto;
    }


    public FilteredListingsDto getFilteredApartmentListings(
            ApartmentListing.RentSaleEnum rentSale,
            double minPrice,
            double maxPrice,
            String address,
            String listingName,
            Pageable pageable
    ) {
        Page<ApartmentListing> page = apartmentListingRepository.findAll(
                ApartmentListingSpecUtil.getApartmentListingSpecification(rentSale, minPrice, maxPrice, address, listingName),
                pageable
        );
        return new FilteredListingsDto(page.getContent(), page.getTotalPages());
    }

    public List<ListingWithLikesDto> getListingsWithLikes(Long userId, String header) {
        String token = header.substring(7);
        String extractedUserId = jwtService.extractId(token);

        // Insecure Direct Object Reference (IDOR) attack prevention.
        if(!extractedUserId.equals(userId.toString())) {
            throw new UnauthorizedAccessException("Unauthorized access attempt for the resource.");
        }
        List<ApartmentListing> listings = apartmentListingRepository.findByUserId(userId);

        return listings.stream()
                .map(listing -> {
                    long likeCount = favoriteService.countLikesForListing(listing.getId());
                    return new ListingWithLikesDto(listing, likeCount);
                })
                .collect(Collectors.toList());
    }
}