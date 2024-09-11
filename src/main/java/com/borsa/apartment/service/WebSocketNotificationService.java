package com.borsa.apartment.service;

import com.borsa.apartment.dto.ListingWithLikesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ApartmentListingService apartmentListingService;

    @Autowired
    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate, ApartmentListingService apartmentListingService) {
        this.messagingTemplate = messagingTemplate;
        this.apartmentListingService = apartmentListingService;
    }

    /**
     * Notify all clients that a favorite has been updated
     */

    public void notifyFavoriteChange(Long userId) {
        List<ListingWithLikesDto> listingsWithLikes = apartmentListingService.getListingsWithLikesUnsafe(userId);
        messagingTemplate.convertAndSend("/topic/favorites/" + userId, listingsWithLikes);
    }
}
