package com.borsa.apartment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "apartment_listings")
public class ApartmentListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String listingName;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private RentSaleEnum rentSale;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int age;

    private int roomNumber;

    private double price;

    private boolean hasFurniture;

    private boolean hasBalcony;

    private int bathroomNumber;

    private double homeSquareMeter;

    public enum RentSaleEnum {
        RENT, SALE
    }

    public ApartmentListing(String address, Long id, String listingName, RentSaleEnum rentSale, User user, int age, int roomNumber, double price, boolean hasBalcony, boolean hasFurniture, int bathroomNumber, double homeSquareMeter) {
        this.address = address;
        this.id = id;
        this.listingName = listingName;
        this.rentSale = rentSale;
        this.user = user;
        this.age = age;
        this.roomNumber = roomNumber;
        this.price = price;
        this.hasBalcony = hasBalcony;
        this.hasFurniture = hasFurniture;
        this.bathroomNumber = bathroomNumber;
        this.homeSquareMeter = homeSquareMeter;
    }

    public ApartmentListing() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListingName() {
        return listingName;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RentSaleEnum getRentSale() {
        return rentSale;
    }

    public void setRentSale(RentSaleEnum rentSale) {
        this.rentSale = rentSale;
    }

    public boolean isHasFurniture() {
        return hasFurniture;
    }

    public void setHasFurniture(boolean hasFurniture) {
        this.hasFurniture = hasFurniture;
    }

    public double getHomeSquareMeter() {
        return homeSquareMeter;
    }

    public void setHomeSquareMeter(double homeSquareMeter) {
        this.homeSquareMeter = homeSquareMeter;
    }

    public boolean isHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public int getBathroomNumber() {
        return bathroomNumber;
    }

    public void setBathroomNumber(int bathroomNumber) {
        this.bathroomNumber = bathroomNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}