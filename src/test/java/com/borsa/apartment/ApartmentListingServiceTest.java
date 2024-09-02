package com.borsa.apartment;

import com.borsa.apartment.dto.MessageResponseDto;
import com.borsa.apartment.model.ApartmentListing;
import com.borsa.apartment.model.User;
import com.borsa.apartment.repo.ApartmentListingRepository;
import com.borsa.apartment.service.ApartmentListingService;
import com.borsa.apartment.service.EmailService;
import com.borsa.apartment.service.JwtService;
import com.borsa.apartment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApartmentListingServiceTest {

    @Mock
    private ApartmentListingRepository apartmentListingRepository;

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private ApartmentListingService apartmentListingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllListings() {
        List<ApartmentListing> listings = List.of(new ApartmentListing(), new ApartmentListing());
        when(apartmentListingRepository.findAll()).thenReturn(listings);

        List<ApartmentListing> result = apartmentListingService.getAllListings();

        assertEquals(2, result.size());
        verify(apartmentListingRepository, times(1)).findAll();
    }

    @Test
    void testCreateListingSuccess() {
        String userId = "1";
        String header = "Bearer token";
        ApartmentListing listing = new ApartmentListing();
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        listing.setListingName("Listing Name");
        listing.setAddress("Address");

        when(jwtService.extractId(anyString())).thenReturn(userId);
        when(userService.getUser(1L)).thenReturn(user);
        when(apartmentListingRepository.save(any(ApartmentListing.class))).thenReturn(listing);

        MessageResponseDto response = apartmentListingService.createListing(userId, listing, header);

        assertEquals("Successfully added listing.", response.getMessage());
        verify(apartmentListingRepository, times(1)).save(listing);
        verify(emailService, times(1)).sendEmail(any());
    }
}