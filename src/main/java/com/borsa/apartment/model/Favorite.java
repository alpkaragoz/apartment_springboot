package com.borsa.apartment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-favorites")
    private User user;

    @ManyToOne
    @JoinColumn(name = "apartment_listing_id", nullable = false)
    @JsonBackReference("listing-favorites")
    private ApartmentListing listing;

    private LocalDateTime favoriteDate;

    public Favorite() {
        this.favoriteDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ApartmentListing getListing() {
        return listing;
    }

    public void setListing(ApartmentListing listing) {
        this.listing = listing;
    }

    public LocalDateTime getLikedAt() {
        return favoriteDate;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.favoriteDate = likedAt;
    }
}
